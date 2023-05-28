package com.example.example.service.status.handler;

import com.example.example.domain.OrderEntity;
import com.example.example.domain.StatusOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DeliveredStatusHandler implements StatusHandler {
    @Override
    public void handler(@NonNull OrderEntity order, @NonNull StatusOrder newStatus) {
        if (newStatus != StatusOrder.DELIVERED) {
            return;
        }
        log.info("Order {} waiting person!", order.getId());
        order.getDateHistory().setReceive(LocalDateTime.now());
        order.getDateHistory().getStorage().add(LocalDateTime.now());
    }
}
