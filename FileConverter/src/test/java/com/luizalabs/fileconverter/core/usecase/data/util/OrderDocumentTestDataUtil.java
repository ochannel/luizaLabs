package com.luizalabs.fileconverter.core.usecase.data.util;

import com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument;
import com.luizalabs.fileconverter.infrastructure.mongodb.document.UserDocument;

import java.time.LocalDate;

public class OrderDocumentTestDataUtil {

    public static OrderDocument getOrder() {
        UserDocument user = UserDocument.builder()
                .name("Alfredo Oliveira")
                .userId(1L).build();
        OrderDocument order = OrderDocument.builder()
                .user(user)
                .products(ProductDocumentTestDataUtil.getProducts())
                .orderDate(LocalDate.of(2021, 1, 1))
                .orderId(1L)
                .build();
        return order;
    }
}
