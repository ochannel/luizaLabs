package com.luizalabs.fileconverter.core.usecase;


import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.exception.BadRequestException;
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
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("startDate must be before or equal to endDate");
        }
        return orderGateWay.findByOrderDateBetween(startDate, endDate);
    }
}
