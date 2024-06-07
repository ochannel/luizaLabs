package com.luizalabs.fileconverter.core.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
    @EqualsAndHashCode.Include
    private Long orderId ;
    private LocalDate orderDate;
    private User user;
    private List<Product> products;


    public BigDecimal getTotal() {
       return products.stream().map(Product::getProductPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
