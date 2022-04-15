package com.tmt.tmdt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter

@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDto  {
    private Long id;
    private String username;
    private String email;
    private String imageLink;

}
