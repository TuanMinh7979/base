package com.tmt.tmdt.service;

import com.tmt.tmdt.dto.FileRequestDto;
import com.tmt.tmdt.entities.UserEntity;
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


    void add(UserEntity userEntity, FileRequestDto fileRequestDto) throws IOException;

    void update(UserEntity userEntity, FileRequestDto fileRequestDto, String del) throws IOException;


    Page getUserEntityByUserName(String searchNameTerm, Pageable pageable);


    boolean existByUserName(String username);

    boolean existById(Long id);

    UserEntity getUserEntityWithImage(Long id);


    void delete(Long id);

    void deletes(Long[] ids);
}
