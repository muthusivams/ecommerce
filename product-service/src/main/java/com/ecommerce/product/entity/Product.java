package com.ecommerce.product.entity;
import lombok.*;import javax.persistence.*;import java.math.BigDecimal;
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false) private String category;
 private String description;
 @Column(nullable=false) private BigDecimal price;
}
