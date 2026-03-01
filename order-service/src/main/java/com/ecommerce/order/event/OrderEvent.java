package com.ecommerce.order.event; import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor public class OrderEvent { private Long orderId; private String status; }
