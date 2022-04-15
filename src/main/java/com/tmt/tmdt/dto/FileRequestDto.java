package com.tmt.tmdt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRequestDto {

    private MultipartFile file;
    Map<String, String> uploadRs = new HashMap<>();


    public FileRequestDto(MultipartFile file) {
        this.file= file;
    }
}
