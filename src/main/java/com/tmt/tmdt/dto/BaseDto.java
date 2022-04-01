package com.tmt.tmdt.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDto {

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
