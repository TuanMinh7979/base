package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.ImageDetail;
import com.tmt.tmdt.repository.ImageDetailRepo;
import com.tmt.tmdt.service.ImageDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageDetailServiceImpl implements ImageDetailService {
    private final ImageDetailRepo imageDetailRepo;

    @Override
    public void save(ImageDetail imageDetail) {

        imageDetailRepo.save(imageDetail);

    }
}
