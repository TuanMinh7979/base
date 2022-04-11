package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserEntityService {
    Page<UserEntity> getUserEntitysByRoleAndUserNameLike(Long roleId, String searchNameTerm, Pageable pageable) ;

     Page<UserEntity> getUserEntitysByRole(Long roleId, Pageable pageable) ;



    Page<UserEntity> getUserEntitys(Pageable pageable) ;

    UserEntity getUserEntity(Long id);

    List<UserEntity> getUserEntitys();

    void save(UserEntity userEntity);


    Page getUserEntityByUserName(String searchNameTerm, Pageable pageable);
}
