package com.luizalabs.fileconverter.core.gateway;

import com.luizalabs.fileconverter.core.entity.Order;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    public Order save(Order entity);

    public Page<Order> getAllOrder(int page, int size);

    public Optional<Order> findById(Long id);

    public List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

}
