package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.Image;

import java.io.IOException;

public interface ImageService {
    Image save(Image image);

    Image getImageDetail(Long id);

    boolean existById(Long id);

    void deleteById(Long id) throws IOException;

    void deleteFromCloud(Long id) throws IOException;

}
