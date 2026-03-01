package com.ecommerce.notification.service; import lombok.extern.slf4j.Slf4j;import org.springframework.kafka.annotation.KafkaListener;import org.springframework.stereotype.Service;
@Service @Slf4j
public class EventConsumer {
 @KafkaListener(topics = "order-events", groupId = "notification-service")
 public void onOrderEvent(String payload) { log.info("Mock email: order update {}", payload); }
 @KafkaListener(topics = "payment-events", groupId = "notification-service")
 public void onPaymentEvent(String payload) { log.info("Mock email: payment update {}", payload); }
}
