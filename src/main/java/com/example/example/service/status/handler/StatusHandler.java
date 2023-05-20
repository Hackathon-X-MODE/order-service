package com.example.example.service.status.handler;

import com.example.example.domain.OrderEntity;
import com.example.example.domain.StatusOrder;
import org.springframework.lang.NonNull;

public interface StatusHandler {

    void handler(@NonNull OrderEntity order, @NonNull StatusOrder newStatus);
}
