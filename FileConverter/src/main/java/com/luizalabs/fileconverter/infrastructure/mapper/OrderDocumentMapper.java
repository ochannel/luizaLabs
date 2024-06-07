package com.luizalabs.fileconverter.infrastructure.mapper;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDocumentMapper {
    private final ModelMapper mapper;

    public OrderDocument create(Order order) {
        return mapper.map(order, OrderDocument.class);
    }
}
