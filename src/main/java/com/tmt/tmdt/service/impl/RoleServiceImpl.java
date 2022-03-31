package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Role;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.RoleRepo;
import com.tmt.tmdt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public boolean existByName(String name) {
        return roleRepo.existsByName(name);
    }

    @Override
    public Page getRolesByNameLike(String searchNameTerm, Pageable pageable) {
        return roleRepo.getRolesByNameLike(searchNameTerm, pageable);
    }

    @Override
    public Page getRoles(Pageable pageable) {
        return roleRepo.findAll(pageable);
    }

    @Override
    public Integer deleteById(Integer id) {
        roleRepo.deleteById(id);
        return id;
    }


    public Integer[] deleteRoles(Integer[] ids) {
        for (Integer id : ids) {
            roleRepo.deleteById(id);
        }
        return ids;
    }

    @Override
    public Role getRole(Integer id) {
        return roleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Role with id "+id+" not found"));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepo.getRoleByName(name);
    }




}
