package com.tmt.tmdt.service.security;

import com.tmt.tmdt.entities.Permission;
import com.tmt.tmdt.entities.Role;
import com.tmt.tmdt.entities.UserEntity;
import com.tmt.tmdt.service.UserEntityService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor


public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userEntityService.getUserEntityWithRoles(username);

        Set<SimpleGrantedAuthority> setAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            setAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            for (Permission permission : role.getPermissions()) {
                setAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return new User(user.getUsername(), user.getPassword(), setAuthorities);


    }


}
