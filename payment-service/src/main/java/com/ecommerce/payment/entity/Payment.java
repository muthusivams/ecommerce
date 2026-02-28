package com.ecommerce.payment.entity; import lombok.*;import javax.persistence.*;import java.math.BigDecimal;
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private Long orderId; private BigDecimal amount; private String status; }
