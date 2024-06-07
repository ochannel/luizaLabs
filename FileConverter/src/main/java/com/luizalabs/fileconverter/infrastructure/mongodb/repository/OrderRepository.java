package com.luizalabs.fileconverter.infrastructure.mongodb.repository;

import com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderDocument, Long> {
    public List<OrderDocument> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
