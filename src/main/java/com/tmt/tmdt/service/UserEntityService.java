package com.tmt.tmdt.service;

import com.tmt.tmdt.dto.ImageRequestDto;
import com.tmt.tmdt.entities.Image;
import com.tmt.tmdt.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface UserEntityService {
    Page<UserEntity> getUserEntitysByRoleAndUserNameLike(Long roleId, String searchNameTerm, Pageable pageable);

    Page<UserEntity> getUserEntitysByRole(Long roleId, Pageable pageable);


    Page<UserEntity> getUserEntitys(Pageable pageable);

    UserEntity getUserEntity(Long id);

    List<UserEntity> getUserEntitys();


    void add(UserEntity userEntity, ImageRequestDto imageRequestDto) throws IOException;

    void update(UserEntity userEntity, ImageRequestDto imageRequestDto, String del) throws IOException;


    Page getUserEntityByUserName(String searchNameTerm, Pageable pageable);


    boolean existByUserName(String username);

    boolean existById(Long id);

    UserEntity getUserEntityWithImage(Long id);



}
