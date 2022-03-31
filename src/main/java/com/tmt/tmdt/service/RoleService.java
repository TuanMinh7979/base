package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Role save(Role role);

    boolean existByName(String name);

    Page getRolesByNameLike(String searchNameTerm, Pageable pageable);

    Page getRoles(Pageable pageable);


    Integer deleteById(Integer id);

    Integer[] deleteRoles(Integer[] ids);

    Role getRole(Integer id);

    Role getRoleByName(String name);




}
