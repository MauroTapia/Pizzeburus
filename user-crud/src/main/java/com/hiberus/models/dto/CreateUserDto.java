package com.hiberus.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateUserDto {
    public CreateUserDto(String name) {
        this.name = name;
    }

    private String name;
}

