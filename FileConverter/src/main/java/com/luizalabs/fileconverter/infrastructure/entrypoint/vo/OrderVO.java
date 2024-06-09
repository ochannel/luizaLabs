package com.luizalabs.fileconverter.infrastructure.entrypoint.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@JsonPropertyOrder({"order_id", "total", "date", "products"})
public class OrderVO {
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("date")
    private String dataOrder;
    private BigDecimal total;
    private List<ProductVO> products;
}
