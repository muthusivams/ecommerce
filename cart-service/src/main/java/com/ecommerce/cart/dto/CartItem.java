package com.ecommerce.cart.dto; import lombok.*; import java.io.Serializable;
@Data @NoArgsConstructor @AllArgsConstructor public class CartItem implements Serializable { private Long productId; private Integer quantity; }
