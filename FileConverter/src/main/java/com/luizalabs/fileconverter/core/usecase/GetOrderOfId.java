package com.luizalabs.fileconverter.core.usecase;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.exception.NotFoundException;
import com.luizalabs.fileconverter.core.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderOfId {
    private final OrderGateway orderGateWay;

    public Order execute(Long id) {
        return orderGateWay.findById(id).orElseThrow(() -> new NotFoundException("Order with id " + id + " not found"));
    }

}
