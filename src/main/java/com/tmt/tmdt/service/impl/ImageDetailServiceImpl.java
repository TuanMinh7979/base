package com.tmt.tmdt.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tmt.tmdt.entities.ImageDetail;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.ImageDetailRepo;
import com.tmt.tmdt.service.ImageDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageDetailServiceImpl implements ImageDetailService {
    private final ImageDetailRepo imageDetailRepo;
    private final Cloudinary cloudinary;

    @Override
    public ImageDetail save(ImageDetail imageDetail) {

        ImageDetail savedImageDetail = imageDetailRepo.save(imageDetail);

        return savedImageDetail;
    }

    @Override
    public ImageDetail getImageDetail(Long id) {
        return imageDetailRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image with id " + id + " not found"));
    }

    @Override
    public boolean existById(Long id) {
        return imageDetailRepo.existsById(id);
    }


    @Override
    public void deleteById(Long id) throws IOException {

        ImageDetail imageDetail = getImageDetail(id);
        String publicId = imageDetail.getPublicId();
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        imageDetailRepo.deleteById(id);
    }

    @Override
    public void deleteFromCloud(Long id) throws IOException {
        ImageDetail imageDetail = getImageDetail(id);
        String publicId = imageDetail.getPublicId();
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
