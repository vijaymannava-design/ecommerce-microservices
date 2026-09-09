package com.sf.notification_service.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NotificationService.class);
    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void consumeOrderEvent(String message) {
        log.info("[NOTIFICATION SERVICE] Asynchronous Event Received successfully!");
        log.info("[NOTIFICATION SERVICE] Alert dispatch log trigger -> Order reference sequence: {}", message);
        // This is exactly where you would place code to execute mail sender logic!
    }
}
