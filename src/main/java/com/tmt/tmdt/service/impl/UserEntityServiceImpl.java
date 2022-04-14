package com.tmt.tmdt.service.impl;

import com.cloudinary.Cloudinary;
import com.tmt.tmdt.dto.ImageRequestDto;
import com.tmt.tmdt.entities.Image;
import com.tmt.tmdt.entities.Role;
import com.tmt.tmdt.entities.UserEntity;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.mapper.ImageMapper;
import com.tmt.tmdt.repository.UserRepo;
import com.tmt.tmdt.service.ImageService;
import com.tmt.tmdt.service.UploadService;
import com.tmt.tmdt.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepo userRepo;
    private final UploadService uploadService;
    private final ImageMapper imageMapper;
    private final Cloudinary cloudinary;
    private final ImageService imageService;


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
    @Transactional
    public void update(UserEntity userEntity, ImageRequestDto imageRequestDto, String delImageId) throws IOException {

        //delete old image if have
        if (delImageId != null && !delImageId.isEmpty()) {
            delImageId = delImageId.trim();
            Long imageIdToDel = Long.parseLong(delImageId);
            uploadService.deleteFromCloud(imageIdToDel);

            userEntity.setImage(null);
            imageService.deleteById(imageIdToDel);
        }

        //add new image if have
        //if dont change image get file still empty and userEntity image == null


        if (!imageRequestDto.getFile().isEmpty()) {
            //reset
            imageRequestDto.setUploadRs(uploadService.simpleUpload(imageRequestDto.getFile()));
            Image image = imageMapper.toModel(imageRequestDto);


            Image savedImage = imageService.save(image);
            savedImage.setUserEntity(userEntity);
            //persistence
//            savedUser.setImage(savedImage);
//            savedUser.setImageLink(savedImage.getLink());

            userEntity.setImageLink(savedImage.getLink());


        } else {
            userEntity.setImage(getUserEntity(userEntity.getId()).getImage());
        }
        userEntity.setRoleNameList(setRoleNameListHelper(userEntity.getRoleNameList(), userEntity.getRoles()));
        userRepo.save(userEntity);


    }


    @Transactional
    @Override
    public void add(UserEntity userEntity, ImageRequestDto imageRequestDto) throws IOException {


        //add image if have , imageRequestDto away !=null but for scable -> execute fully checking
        if (imageRequestDto != null && !imageRequestDto.getFile().isEmpty()) {
            imageRequestDto.setUploadRs(uploadService.simpleUpload(imageRequestDto.getFile()));
            Image image = imageMapper.toModel(imageRequestDto);

            Image imageSaved = imageService.save(image);
            imageSaved.setUserEntity(userEntity);
            //persistence

            userEntity.setImageLink(imageSaved.getLink());


        }
        userEntity.setRoleNameList(setRoleNameListHelper(userEntity.getRoleNameList(), userEntity.getRoles()));
//        userEntity.setImage(null);
        UserEntity usersaved = userRepo.save(userEntity);

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

    @Override
    public UserEntity getUserEntityWithImage(Long id) {
        return userRepo.getUserEntityWithImage(id).
                orElseThrow(() -> new ResourceNotFoundException("user with id " + id + " not found"));
    }


}
