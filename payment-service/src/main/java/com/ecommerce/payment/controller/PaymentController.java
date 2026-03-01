package com.ecommerce.payment.controller; import com.ecommerce.payment.entity.Payment;import com.ecommerce.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;import org.springframework.web.bind.annotation.*;import java.math.BigDecimal;
@RestController @RequestMapping("/api/payments") @RequiredArgsConstructor
public class PaymentController { private final PaymentService service;
 @PostMapping("/process/{orderId}") public Payment process(@PathVariable Long orderId,@RequestParam String amount){ return service.process(orderId,new BigDecimal(amount)); } }
