package com.ecommerce.inventory.entity; import lombok.*;import javax.persistence.*;
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventory { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(unique=true) private Long productId; private Integer availableStock; }
