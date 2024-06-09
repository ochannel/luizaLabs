package com.luizalabs.fileconverter.infrastructure.mapper;

import com.luizalabs.fileconverter.core.entity.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDocumentMapper {
    private final ModelMapper mapper;

    public com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument create(Order order) {
        return mapper.map(order, com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument.class);
    }
}
