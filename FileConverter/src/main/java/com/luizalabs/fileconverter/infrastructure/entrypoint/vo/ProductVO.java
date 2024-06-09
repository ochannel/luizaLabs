package com.luizalabs.fileconverter.infrastructure.entrypoint.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonPropertyOrder({"product_id", "value"})
public class ProductVO {
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("value")
    private BigDecimal productPrice;
}
