package com.luizalabs.fileconverter.infrastructure.entrypoint.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;
@Data
@JsonPropertyOrder({"user_id", "name", "orders"})
public class MainOrderVO {
    @JsonProperty("user_id")
    private Long userId ;
    private String name ;
    private List<OrderVO> orders;
}
