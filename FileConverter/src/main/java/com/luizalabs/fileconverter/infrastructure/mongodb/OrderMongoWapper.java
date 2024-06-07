package com.luizalabs.fileconverter.infrastructure.mongodb;

import com.luizalabs.fileconverter.core.entity.Order;
import com.luizalabs.fileconverter.core.gateway.OrderGateWay;
import com.luizalabs.fileconverter.infrastructure.mapper.OrderDocumentMapper;
import com.luizalabs.fileconverter.infrastructure.mapper.OrderEntityMapper;
import com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument;
import com.luizalabs.fileconverter.infrastructure.mongodb.repository.OrderRepository;
import com.luizalabs.fileconverter.infrastructure.mongodb.repository.OrderRepositoryPagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMongoWapper implements OrderGateWay {

    private final OrderRepository repository;
    private final OrderRepositoryPagination orderRepositoryPagination;
    private final OrderDocumentMapper orderDocumentMapper;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Order save(Order order) {
        OrderDocument orderDocument = orderDocumentMapper.create(order);
        return orderEntityMapper.create(repository.save(orderDocument));
    }
    @Override
    public Page<Order> getAllOrder(int page, int size) {
        Sort.Direction direction = Sort.Direction.fromString("desc");
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "idOrder"));
        Page<OrderDocument> paginationReturn = orderRepositoryPagination.findAll(pageable);
        return orderEntityMapper.create(paginationReturn);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(o -> orderEntityMapper.create(o));
    }

    @Override
    public List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate) {
        return orderEntityMapper.create(repository.findByOrderDateBetween(startDate, endDate));
    }
}
