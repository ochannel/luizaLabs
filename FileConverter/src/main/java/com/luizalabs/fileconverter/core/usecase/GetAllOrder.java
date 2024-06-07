package com.luizalabs.fileconverter.core.usecase;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.gateway.OrderGateWay;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllOrder {
    private final OrderGateWay orderGateWay;

    public Page<Order> execute(int page, int size){
        return orderGateWay.getAllOrder(page,size);
    }
}
