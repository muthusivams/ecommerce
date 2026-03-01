package com.ecommerce.payment.service; import com.ecommerce.payment.entity.Payment;import com.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;import org.springframework.kafka.core.KafkaTemplate;import org.springframework.stereotype.Service;import java.math.BigDecimal;
@Service @RequiredArgsConstructor
public class PaymentService { private final PaymentRepository repo; private final KafkaTemplate<String,String> kafka;
 public Payment process(Long orderId, BigDecimal amount){ String status=amount.doubleValue()<=10000?"SUCCESS":"FAILED"; Payment p=repo.save(Payment.builder().orderId(orderId).amount(amount).status(status).build()); kafka.send("payment-events",orderId+":"+status); return p; }
}
