package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.dto.response.ViewApi;
import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.entities.Permission;
import com.tmt.tmdt.entities.Role;
import com.tmt.tmdt.service.PermissionService;
import com.tmt.tmdt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@RequestMapping("/admin/role")
@RequiredArgsConstructor
public class RoleController {
    private final PermissionService permissionService;
    private final RoleService roleService;

    @ModelAttribute
    public void commonAtr(Model model) {
        List<Permission> permissions = permissionService.getPermissionByParent(0);
        model.addAttribute("permissionList", permissions.subList(1, permissions.size()));
    }

    @GetMapping("api/viewApi")
    @ResponseBody
    public ViewApi<List<Category>> getCategories(Model model,
                                                 @RequestParam(name = "page", required = false) String pageParam,
                                                 @RequestParam(name = "limit", required = false) String limitParam,
                                                 @RequestParam(name = "sortBy", required = false) String sortBy,
                                                 @RequestParam(name = "sortDirection", required = false) String sortDirection,
                                                 @RequestParam(name = "searchNameTerm", required = false) String searchNameTerm) {


        String sortField = sortBy == null ? "id" : sortBy;
        Sort sort = (sortDirection == null || sortDirection.equals("asc")) ? Sort.by(Sort.Direction.ASC, sortField)
                : Sort.by(Sort.Direction.DESC, sortField);
        int page = pageParam == null ? 0 : Integer.parseInt(pageParam) - 1;
        int limit = limitParam == null ? 5 : Integer.parseInt(limitParam);

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page rolePage = null;
        if (searchNameTerm != null && !searchNameTerm.isEmpty()) {

            rolePage = roleService.getRolesByNameLike(searchNameTerm, pageable);
        } else {
            rolePage = roleService.getRoles(pageable);
        }
        List data = rolePage.getContent();
        int totalPage = rolePage.getTotalPages();

        return new ViewApi<>(totalPage, data);
    }

    @GetMapping("")
    public String index() {
        return "admin/role/index";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("role", new Role());
        return "admin/role/add";
    }

    @PostMapping("save")
    public String save(@RequestParam(name = "permissions", required = false) List<Integer> permissionIds, @Valid @ModelAttribute("role") Role role, BindingResult result) {
//modelAttribute ko hieu cac asscociation mà không có trong chính bảng của nó
        if (roleService.existByName(role.getName())) {
            result.rejectValue("name", "nameIsExist");
        }

        if (!result.hasErrors()) {
            Set<Permission> permissions = new HashSet<>();
            for (Integer id : permissionIds) {
                permissions.add(permissionService.getPermission(id));
            }
            role.setPermissions(permissions);
            roleService.save(role);
            //co the thay de bang dto (Nhận vào Set permissionId) sau đó mapper sang Role trong controller
            //save(RoleDto dto){  toRole(dto) ; save(role)}
            //controller chỉ cần gọi hàm save(Role)

            return "redirect:/admin/role/";
        }
        return "admin/role/add";


    }

    @PostMapping("update")
    public String update(@RequestParam(name = "permissions", required = false) List<Integer> permissionIds, @Valid @ModelAttribute("role") Role role, BindingResult result) {
//modelAttribute ko hieu cac asscociation mà không có trong chính bảng của nó


        if (!result.hasErrors()) {
            Set<Permission> permissions = new HashSet<>();
            for (Integer id : permissionIds) {
                permissions.add(permissionService.getPermission(id));
            }
            role.setPermissions(permissions);
            roleService.save(role);
            //co the thay de bang dto (Nhận vào Set permissionId) sau đó mapper sang Role trong controller
            //save(RoleDto dto){  toRole(dto) ; save(role)}
            //controller chỉ cần gọi hàm save(Role)

            return "redirect:/admin/role/";
        }
        return "admin/role/edit";


    }

    @PostMapping("api/delete/{id}")
    //call with ajax
    public ResponseEntity<Integer> deleteRole(@PathVariable Integer id) {

        //Xay ra loi sql o tang sql chua the handle tam thoi return null

        Integer deletedId = roleService.deleteById(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
        //handle a error or runtimeException by return a bad request in Global Exception handleling


    }


    @PostMapping("api/delete")
    public ResponseEntity<Integer[]> deleteCategories(@RequestBody Integer[] ids) {
        //Neu xay ra loi thi tra ve 1 response bad request trnog GlobalExceptionHandler
        roleService.deleteRoles(ids);
        return new ResponseEntity<>(ids, HttpStatus.OK);


    }

    @GetMapping("edit/{idx}")
    //rest api : showUpdateForm , showAddForm => getCategory(get)(just for update)
    public String showUpdateForm(Model model, @PathVariable("idx") String idx) {

        Role role = null;
        try {
            //Catch casting exception
            Integer id = Integer.parseInt(idx);
            role = roleService.getRole(id);
        } catch (Exception e) {
            e.printStackTrace();
            role = roleService.getRoleByName(idx);
        }


        model.addAttribute("role", role);

        return "admin/role/edit";

    }

    @GetMapping("/api/active-permission-ids/{id}")
    @ResponseBody
    public Set<Integer> getPermission(@PathVariable("id") Integer id) {
        Role role = roleService.getRole(id);
        Set<Integer> activePermissionIds = role.getPermissions().stream().map(p -> p.getId()).collect(Collectors.toSet());
        return activePermissionIds;

    }

}
