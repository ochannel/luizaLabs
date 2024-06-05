package com.luizalabs.fileconverter.core.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class Order {
    private Integer idOrder ;
    private LocalDate dataOrder;
    private User user;
    private List<Product> products;

}
