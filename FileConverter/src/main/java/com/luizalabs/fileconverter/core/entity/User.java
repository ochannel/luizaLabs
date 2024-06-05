package com.luizalabs.fileconverter.core.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer idUser ;
    private String name ;
}
