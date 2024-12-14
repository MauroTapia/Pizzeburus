package com.hiberus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Pizza {
    private Long id;
    private String name;
}