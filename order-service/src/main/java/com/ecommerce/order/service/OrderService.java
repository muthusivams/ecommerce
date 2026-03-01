package com.ecommerce.order.service;

import com.ecommerce.order.client.InventoryClient;
import com.ecommerce.order.client.PaymentClient;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.entity.*;
import com.ecommerce.order.event.OrderEvent;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo;
    private final InventoryClient inventory;
    private final PaymentClient payment;
    private final KafkaTemplate<String, Object> kafka;

    public Order create(OrderRequest req) {
        return repo.findByRequestId(req.getRequestId()).orElseGet(() -> {
            inventory.reserve(req.getProductId(), req.getQuantity());
            Order o = repo.save(Order.builder().userId(req.getUserId()).productId(req.getProductId()).quantity(req.getQuantity()).amount(req.getAmount()).status(OrderStatus.CREATED).requestId(req.getRequestId()).build());
            payment.process(o.getId(), req.getAmount().toString());
            kafka.send("order-events", new OrderEvent(o.getId(), o.getStatus().name()));
            return o;
        });
    }

    public Order get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @KafkaListener(topics = "payment-events", groupId = "order-service")
    public void onPaymentEvent(String event) {
        String[] parts = event.split(":");
        Long id = Long.valueOf(parts[0]);
        String status = parts[1];
        Order o = get(id);
        o.setStatus("SUCCESS".equals(status) ? OrderStatus.PAID : OrderStatus.CANCELLED);
        repo.save(o);
        kafka.send("order-events", new OrderEvent(o.getId(), o.getStatus().name()));
    }
}
