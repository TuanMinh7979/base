package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.dto.ViewApi;
import com.tmt.tmdt.entities.UserEntity;
import com.tmt.tmdt.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;

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
                userPage = userEntityService.getUserEntitysByRoleAndNameLike(roleId, searchNameTerm, pageable);
            } else {
                userPage = userEntityService.getUserEntitysByRole(roleId, pageable);
            }


        } else if (searchNameTerm != null && !searchNameTerm.isEmpty()) {

            userPage = userEntityService.getProductsByUserName(searchNameTerm, pageable);
        } else {
            userPage = userEntityService.getUserEntitys(pageable);
        }
        List data = userPage.getContent();
        int totalPage = userPage.getTotalPages();


        return new ViewApi<>(totalPage, data);
    }


}
