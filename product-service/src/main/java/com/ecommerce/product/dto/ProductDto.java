package com.ecommerce.product.dto;
import lombok.Data;import javax.validation.constraints.*;import java.math.BigDecimal;
@Data public class ProductDto { private Long id; @NotBlank private String name; @NotBlank private String category; private String description; @NotNull @DecimalMin("0.01") private BigDecimal price; }
