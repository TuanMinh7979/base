package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Role;
import com.tmt.tmdt.entities.UserEntity;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.UserRepo;
import com.tmt.tmdt.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepo userRepo;

    @Override
    public UserEntity getUserEntity(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public Page<UserEntity> getUserEntitysByRoleAndNameLike(Long roleId, String searchNameTerm, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserEntity> getUserEntitysByRole(Long roleId, Pageable pageable) {
        return userRepo.getUserEntitysByRole(roleId, pageable);
    }

    @Override
    public Page<UserEntity> getProductsByUserName(String searchNameTerm, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserEntity> getUserEntitys(Pageable pageable) {
        return userRepo.findAll(pageable);
    }


    @Override
    public List<UserEntity> getUserEntitys() {
        return userRepo.findAll();
    }


    @Override
    public void save(UserEntity userEntity) {

        for (Role role : userEntity.getRoles()) {
            userEntity.getRoleNameList().add(role.getName());
        }
        userRepo.save(userEntity);
    }


}
