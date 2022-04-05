package com.tmt.tmdt.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tmt.tmdt.entities.Image;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.ImageDetailRepo;
import com.tmt.tmdt.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageDetailRepo imageDetailRepo;
    private final Cloudinary cloudinary;

    @Override
    public Image save(Image image) {

        Image savedImage = imageDetailRepo.save(image);

        return savedImage;
    }

    @Override
    public Image getImageDetail(Long id) {
        return imageDetailRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image with id " + id + " not found"));
    }

    @Override
    public boolean existById(Long id) {
        return imageDetailRepo.existsById(id);
    }


    @Override
    public void deleteById(Long id) throws IOException {

        Image image = getImageDetail(id);
        String publicId = image.getPublicId();
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        imageDetailRepo.deleteById(id);
    }

    @Override
    public void deleteFromCloud(Long id) throws IOException {
        Image image = getImageDetail(id);
        String publicId = image.getPublicId();
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
