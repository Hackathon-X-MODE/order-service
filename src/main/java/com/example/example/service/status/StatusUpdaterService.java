package com.example.example.service.status;

import com.example.example.domain.OrderEntity;
import com.example.example.domain.StatusOrder;
import com.example.example.service.status.handler.StatusHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusUpdaterService {
    private static final Map<StatusOrder, Set<StatusOrder>> STATUS_FLOW = Map.of(
            StatusOrder.CREATED, Set.of(StatusOrder.CANCELED, StatusOrder.AT_DELIVERY, StatusOrder.DELIVERED, StatusOrder.PROCESSED, StatusOrder.FINISHED),
            StatusOrder.PROCESSED, Set.of(StatusOrder.AT_DELIVERY, StatusOrder.DELIVERED, StatusOrder.FINISHED),
            StatusOrder.AT_DELIVERY, Set.of(StatusOrder.CANCELED, StatusOrder.DELIVERED, StatusOrder.FINISHED),
            StatusOrder.DELIVERED, Set.of(StatusOrder.CANCELED, StatusOrder.FINISHED),
            StatusOrder.CANCELED, Set.of(),
            StatusOrder.FINISHED, Set.of()
    );

    private final List<StatusHandler> statusHandlers;

    public void update(OrderEntity order, @Nullable StatusOrder newStatus) {
        if (newStatus == null) {
            return;
        }
        log.info("Update status order");

        if (order.getStatusOrder() != null && !this.validateFlow(order.getStatusOrder(), newStatus)) {
            throw new IllegalArgumentException("Illegal flow status, from " + order.getStatusOrder() + " to " + newStatus);
        }

        this.statusHandlers.forEach(statusHandler -> statusHandler.handler(order, newStatus));

        order.setStatusOrder(newStatus);
    }

    private boolean validateFlow(StatusOrder oldStatus, StatusOrder newStatus) {
        final var flow = STATUS_FLOW.get(oldStatus);
        if (flow == null) {
            throw new RuntimeException("Status " + oldStatus + " not present at flow!");
        }

        return flow.contains(newStatus);
    }
}
