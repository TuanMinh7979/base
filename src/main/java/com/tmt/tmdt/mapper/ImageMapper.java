package com.tmt.tmdt.mapper;

import com.tmt.tmdt.dto.FileRequestDto;
import com.tmt.tmdt.entities.Image;

public interface ImageMapper {
    Image toModel(FileRequestDto imageDto);

}
