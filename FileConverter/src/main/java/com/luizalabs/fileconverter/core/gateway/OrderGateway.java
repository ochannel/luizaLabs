package com.luizalabs.fileconverter.core.gateway;

import com.luizalabs.fileconverter.core.entity.Order;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    Order save(Order entity);

    Page<Order> getAllOrders(int page, int size);

    Optional<Order> findById(Long id);

    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

}
