package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Image;
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

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepo userRepo;


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
    public void save(UserEntity userEntity, Image image) {
        userEntity.setRoleNameList(setRoleNameListHelper(userEntity.getRoleNameList(), userEntity.getRoles()));
        userEntity.setImage(image);
        userEntity.setImageLink(image.getLink());
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

    @Override
    public boolean existById(Long id) {
        return userRepo.existsById(id);
    }


}
