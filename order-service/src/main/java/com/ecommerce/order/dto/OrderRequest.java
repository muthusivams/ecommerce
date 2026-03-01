package com.ecommerce.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal amount;
    private String requestId;
}
