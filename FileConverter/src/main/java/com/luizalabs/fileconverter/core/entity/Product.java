package com.luizalabs.fileconverter.core.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class Product {
    private Integer idProduct;
    private BigDecimal productPrice ;
}
