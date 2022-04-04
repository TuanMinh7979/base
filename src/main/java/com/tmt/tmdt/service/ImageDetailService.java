package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.ImageDetail;

import java.io.IOException;

public interface ImageDetailService {
    ImageDetail save(ImageDetail imageDetail);

    ImageDetail getImageDetail(Long id);

    boolean existById(Long id);

    void deleteById(Long id) throws IOException;

    void deleteFromCloud(Long id) throws IOException;

}
