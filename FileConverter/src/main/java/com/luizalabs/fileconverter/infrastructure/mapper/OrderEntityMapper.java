package com.luizalabs.fileconverter.infrastructure.mapper;

import com.luizalabs.fileconverter.core.entity.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderEntityMapper {

    private final ModelMapper mapper;

    public Order create(com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument orderDocument) {
        return mapper.map(orderDocument, Order.class);
    }

    public Page<Order> create(Page<com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument> page) {

        List<Order> listOrder = page.stream()
                .map(user -> mapper.map(user, Order.class))
                .collect(Collectors.toList());
        return new PageImpl<>(listOrder, page.getPageable(), page.getTotalElements());
    }

    public List<Order> create(List<com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument> listDocument) {

        List<Order> listOrder = listDocument.stream()
                .map(order -> mapper.map(order, Order.class))
                .collect(Collectors.toList());
        return listOrder;
    }
}
