package com.example.example.service;

import com.example.example.domain.OrderEntity;
import com.example.example.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderEntity create(UUID vendorId, String externalId) {
        return this.orderRepository.save(
                new OrderEntity()
                        .setVendorId(vendorId)
                        .setExternalId(externalId)
        );
    }


    @Transactional
    public OrderEntity get(UUID vendorId, String externalId) {
        return this.orderRepository.findByExternalIdAndVendorId(
                externalId, vendorId
        ).orElseGet(() -> this.create(vendorId, externalId));
    }
}
