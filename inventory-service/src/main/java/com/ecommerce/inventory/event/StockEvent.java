package com.ecommerce.inventory.event; import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor
public class StockEvent { private Long productId; private Integer stock; private String type; }
