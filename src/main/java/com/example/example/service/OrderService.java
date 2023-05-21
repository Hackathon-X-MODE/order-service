package com.example.example.service;

import com.example.example.domain.OrderEntity;
import com.example.example.exception.EntityNotFoundException;
import com.example.example.mapper.OrderMapper;
import com.example.example.model.OrderWithMetaDto;
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

    private final OrderMapper orderMapper;

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


    @Transactional(readOnly = true)
    public OrderWithMetaDto get(UUID orderId) {
        return this.orderMapper.toDto(
                this.orderRepository.findById(orderId)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Can't find order with id: " + orderId)
                        )
        );
    }
}
