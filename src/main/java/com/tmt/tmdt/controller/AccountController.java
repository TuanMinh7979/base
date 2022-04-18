package com.tmt.tmdt.controller;

import com.tmt.tmdt.entities.UserEntity;
import com.tmt.tmdt.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor

public class AccountController {

    private final UserEntityService userEntityService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "home/account/login";
    }

    @GetMapping("api/user")
    @ResponseBody
    public UserEntity getUser(@AuthenticationPrincipal UserDetails user) {
        return userEntityService.getUserByUsername(user.getUsername());
    }
}


