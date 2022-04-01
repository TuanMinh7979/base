package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.ImageDetail;

public interface ImageDetailService {
ImageDetail save(ImageDetail imageDetail);

ImageDetail getImageDetail(Long id);
}
