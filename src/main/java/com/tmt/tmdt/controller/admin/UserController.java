package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.constant.UserStatus;
import com.tmt.tmdt.dto.ImageRequestDto;
import com.tmt.tmdt.dto.ViewApi;
import com.tmt.tmdt.entities.UserEntity;
import com.tmt.tmdt.repository.UserRepo;
import com.tmt.tmdt.service.RoleService;
import com.tmt.tmdt.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final RoleService roleService;
    private final UserRepo userRepo;


    @GetMapping("")
    public String index() {
        return "admin/user/index";
    }

    @GetMapping("api/viewApi")
    @ResponseBody
    public ViewApi<List<UserEntity>> getUsers(Model model,
                                              @RequestParam(name = "page", required = false) String pageParam,
                                              @RequestParam(name = "limit", required = false) String limitParam,
                                              @RequestParam(name = "sortBy", required = false) String sortBy,
                                              @RequestParam(name = "sortDirection", required = false) String sortDirection,
                                              @RequestParam(name = "searchNameTerm", required = false) String searchNameTerm,
                                              @RequestParam(name = "role", required = false) String roleIdParam) {


        String sortField = sortBy == null ? "id" : sortBy;
        Sort sort = (sortDirection == null || sortDirection.equals("asc")) ? Sort.by(Sort.Direction.ASC, sortField)
                : Sort.by(Sort.Direction.DESC, sortField);
        int page = pageParam == null ? 0 : Integer.parseInt(pageParam) - 1;
        int limit = limitParam == null ? 5 : Integer.parseInt(limitParam);

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page userPage = null;

        if (roleIdParam != null && !roleIdParam.isEmpty() && Long.parseLong(roleIdParam) != 0) {

            Long roleId = Long.parseLong(roleIdParam);
            if (searchNameTerm != null && !searchNameTerm.isEmpty()) {
                userPage = userEntityService.getUserEntitysByRoleAndUserNameLike(roleId, searchNameTerm, pageable);
            } else {
                userPage = userEntityService.getUserEntitysByRole(roleId, pageable);
            }


        } else if (searchNameTerm != null && !searchNameTerm.isEmpty()) {

            userPage = userEntityService.getUserEntityByUserName(searchNameTerm, pageable);
        } else {
            userPage = userEntityService.getUserEntitys(pageable);
        }
        List data = userPage.getContent();
        int totalPage = userPage.getTotalPages();


        return new ViewApi<>(totalPage, data);
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("rolesForForm", roleService.getRoles());
        model.addAttribute("status", new ArrayList<>(Arrays.asList(UserStatus.values())));
        return "admin/user/add";
    }


    @PostMapping("/save")
    public String save(Model model,
                       ImageRequestDto imageRequestDto,
                       @Valid @ModelAttribute("user") UserEntity userEntity,
                       BindingResult result) throws IOException {

        if (userEntityService.existByUserName(userEntity.getUsername())) {
            result.rejectValue("username", "nameIsExist");
        }
        if (!result.hasErrors()) {
            //only upload if no binđing error occurs
            userEntityService.add(userEntity, imageRequestDto);

            return "redirect:/admin/user";
        }
        model.addAttribute("rolesForForm", roleService.getRoles());
        model.addAttribute("status", new ArrayList<>(Arrays.asList(UserStatus.values())));
        return "admin/user/add";

    }

//    @GetMapping("edit/{id}")
//    public String showUpdateForm(Model model, @PathVariable("id") Long id) {
//        UserEntity userEntity = userEntityService.getUserEntityWithImage(id);
//        model.addAttribute("user", userEntity);
//        model.addAttribute("rolesForForm", roleService.getRoles());
//        model.addAttribute("status", new ArrayList<>(Arrays.asList(UserStatus.values())));
//        return "admin/user/edit";
//
//    }

    @GetMapping("edit/{id}")
    @ResponseBody
    public UserEntity showUpdateForma(Model model, @PathVariable("id") Long id) {
        UserEntity userEntity = userEntityService.getUserEntityWithImage(id);
        model.addAttribute("user", userEntity);

        return userEntity;
    }

    @PostMapping("edit/{id}")
    @ResponseBody
    public UserEntity update(Model model, @RequestBody  UserEntity user) {

        userRepo.save(user);
        return null;
    }


    @Transactional
    @PostMapping("/update")

    public String update(Model model,
                         @RequestParam(value = "delImageId", required = false) String delImageId,
                         ImageRequestDto imageRequestDto,
                         @Valid @ModelAttribute("user") UserEntity userEntity,
                         BindingResult result) throws IOException {

        String oldUserName = userEntityService.getUserEntity(userEntity.getId()).getUsername();
        if ((userEntity.getUsername() == oldUserName)
                && userEntityService.existByUserName(userEntity.getUsername())) {
            result.rejectValue("username", "nameIsExist");
        }
        if (!result.hasErrors()) {
            userEntityService.update(userEntity, imageRequestDto, delImageId);
            return "redirect:/admin/user";
        }
        model.addAttribute("rolesForForm", roleService.getRoles());
        model.addAttribute("status", new ArrayList<>(Arrays.asList(UserStatus.values())));
        return "admin/user/add";

    }


    @GetMapping("/api/{id}/active-role-ids")
    @ResponseBody
    public List<Integer> getPermission(@PathVariable("id") Long id) {
        return roleService.getRoleIdsByUserId(id);
    }


}
