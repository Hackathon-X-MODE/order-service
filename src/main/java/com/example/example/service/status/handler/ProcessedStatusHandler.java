package com.example.example.service.status.handler;

import com.example.example.domain.OrderEntity;
import com.example.example.domain.StatusOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class ProcessedStatusHandler implements StatusHandler {
    @Override
    public void handler(OrderEntity order, StatusOrder newStatus) {
        if (newStatus != StatusOrder.PROCESSED){
            return;
        }
        log.info("Order {} completed and waiting delivery!", order.getId());
        order.getDateHistory().setAssembling(LocalDateTime.now());
    }
}
