package com.luizalabs.fileconverter.core.usecase.data.util;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.entity.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OrderTestDataUtil {

    public static List<Order> getAllOrder(){
        User user = User.builder()
                .name("Alfredo Oliveira")
                .userId(1L).build();
       Order order= Order.builder()
                .user(user)
                .products(ProductTestDataUtil.getProducts())
                .orderDate(LocalDate.of(2021,1,1))
                .orderId(1L)
                .build();
        return Arrays.asList(order);
    }



}
