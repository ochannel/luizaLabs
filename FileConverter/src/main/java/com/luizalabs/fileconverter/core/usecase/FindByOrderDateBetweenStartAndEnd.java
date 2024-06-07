package com.luizalabs.fileconverter.core.usecase;


import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.gateway.OrderGateWay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByOrderDateBetweenStartAndEnd {
    private final OrderGateWay orderGateWay;

    public List<Order> execute(LocalDate startDate, LocalDate endDate) {
        //  return orderGateWay.findById(id).orElseThrow(()->new NotFoundException("Order with id " + id + " not found"));
        return orderGateWay.findByOrderDateBetween(startDate, endDate);
    }

}
