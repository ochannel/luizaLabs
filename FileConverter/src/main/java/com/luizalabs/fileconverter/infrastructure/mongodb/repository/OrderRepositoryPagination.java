package com.luizalabs.fileconverter.infrastructure.mongodb.repository;

import com.luizalabs.fileconverter.infrastructure.mongodb.document.OrderDocument;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepositoryPagination extends PagingAndSortingRepository<OrderDocument, Long> {
}
