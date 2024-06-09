package com.luizalabs.fileconverter.core.usecase.data.util;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.entity.User;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderTestDataUtil {

    public static List<Order> getAllOrders() {
        User user = User.builder()
                .name("Alfredo Oliveira")
                .userId(1L).build();
        Order order = Order.builder()
                .user(user)
                .products(ProductTestDataUtil.getProducts())
                .orderDate(LocalDate.of(2021, 1, 1))
                .orderId(1L)
                .build();
        return Arrays.asList(order);
    }

    public static Page<Order> getPageOrder(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").ascending());
        Page<Order> expected = new PageImpl<>(OrderTestDataUtil.getAllOrders(), pageable, OrderTestDataUtil.getAllOrders().size());
        return expected;
    }

    public static Page<Order> getPageOrderEmpty(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").ascending());
        Page<Order> expected = new PageImpl<>(Arrays.asList(), pageable, Arrays.asList().size());
        return expected;
    }


    public static List<Order> getOrderForLine() {
        User user1 = User.builder()
                .name("Alfredo Oliveira")
                .userId(1L).build();
        Order order1 = Order.builder()
                .user(user1)
                .products(new ArrayList<>(Collections.singletonList(ProductTestDataUtil.getProducts().get(0))))
                .orderDate(LocalDate.of(2021, 1, 1))
                .orderId(1L)
                .build();
        User user2 = User.builder()
                .name("Pedro")
                .userId(2L).build();
        Order order2 = Order.builder()
                .user(user2)
                .products(new ArrayList<>(Collections.singletonList(ProductTestDataUtil.getProducts().get(1))))
                .orderDate(LocalDate.of(2022, 1, 1))
                .orderId(2L)
                .build();
        List<Order> list = new ArrayList();
        list.add(order1);
        list.add(order2);
        return list;
    }

}
