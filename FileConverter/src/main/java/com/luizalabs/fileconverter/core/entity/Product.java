package com.luizalabs.fileconverter.core.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "productPrice")
public class Product {
    private Long productId;
    private BigDecimal productPrice;
}
