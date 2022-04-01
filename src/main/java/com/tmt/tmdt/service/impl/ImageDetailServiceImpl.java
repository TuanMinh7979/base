package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.ImageDetail;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.ImageDetailRepo;
import com.tmt.tmdt.service.ImageDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageDetailServiceImpl implements ImageDetailService {
    private final ImageDetailRepo imageDetailRepo;

    @Override
    public ImageDetail save(ImageDetail imageDetail) {

        ImageDetail savedImageDetail= imageDetailRepo.save(imageDetail);

        return savedImageDetail;
    }

    @Override
    public ImageDetail getImageDetail(Long id) {
        return imageDetailRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image with id " + id + " not found"));
    }
}
