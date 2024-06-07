package com.luizalabs.fileconverter.infrastructure.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;
@Document(value = "Order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDocument {
    @MongoId
    private Long orderId;
    private LocalDate orderDate;
    private UserDocument user;
    private List<ProductDocument> products;

}
