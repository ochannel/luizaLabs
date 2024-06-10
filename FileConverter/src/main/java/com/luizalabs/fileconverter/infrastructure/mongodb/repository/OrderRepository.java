package com.luizalabs.fileconverter.infrastructure.mongodb.repository;

import com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderDocument, Long> {
    @Query("{ 'orderDate' : { $gte: ?0, $lte: ?1 } }")
    List<OrderDocument> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
