package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Role;
import com.tmt.tmdt.entities.UserEntity;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.UserRepo;
import com.tmt.tmdt.service.RoleService;
import com.tmt.tmdt.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepo userRepo;
    private final RoleService roleService;

    private List<String> setRoleNameListHelper(List<String> currentRoleNameList, Set<Role> roles) {
        //use for 2 case add new or remove all
        if (!currentRoleNameList.isEmpty()) {
            //if currentRoleNameListNotEmpty clear it first
            for (String rolenamei : currentRoleNameList) {
                currentRoleNameList.remove(rolenamei);
            }
        }
        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                currentRoleNameList.add(role.getName());
            }
        }
        return currentRoleNameList;

    }

    @Override
    public UserEntity getUserEntity(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<UserEntity> getUserEntitys() {
        return userRepo.findAll();
    }


    @Override
    public void save(UserEntity userEntity) {
        userEntity.setRoleNameList(setRoleNameListHelper(userEntity.getRoleNameList(), userEntity.getRoles()));
        userRepo.save(userEntity);
    }


    @Override
    public Page<UserEntity> getUserEntitysByRole(Long roleId, Pageable pageable) {
        return userRepo.getUserEntitysByRole(roleId, pageable);
    }


    @Override
    public Page<UserEntity> getUserEntitys(Pageable pageable) {
        return userRepo.findAll(pageable);
    }


    @Override
    public Page<UserEntity> getUserEntitysByRoleAndUserNameLike(Long roleId, String searchNameTerm, Pageable pageable) {
        return userRepo.getUserEntitysByRoleAndUserNameLike(roleId, searchNameTerm, pageable);
    }

    @Override
    public Page getUserEntityByUserName(String searchNameTerm, Pageable pageable) {
        return userRepo.getUserEntitysByUserName(searchNameTerm, pageable);
    }

    @Override
    public boolean existByUserName(String username) {
        return userRepo.existsByUsername(username);
    }


}
