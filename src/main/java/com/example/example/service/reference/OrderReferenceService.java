package com.example.example.service.reference;

import com.example.example.domain.OrderEntity;
import com.example.example.domain.ref.OrderReference;
import com.example.example.exception.EntityNotFoundException;
import com.example.example.mapper.OrderMapper;
import com.example.example.model.OrderWithMetaDto;
import com.example.example.repository.OrderReferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderReferenceService {

    private final OrderReferenceRepository orderReferenceRepository;

    private final OrderMapper orderMapper;

    @Transactional
    public String generate(OrderEntity order) {

        final var secretKey = DigestUtils.appendMd5DigestAsHex(UUID.randomUUID().toString().getBytes(), new StringBuilder()).substring(0, 9);

        this.orderReferenceRepository.saveAndFlush(
                new OrderReference()
                        .setOrder(order)
                        .setKey(secretKey)
        );
        return secretKey;
    }

    @Transactional(readOnly = true)
    public OrderWithMetaDto byKey(String key) {
        return this.orderMapper.toDto(
                this.orderReferenceRepository.findById(key).orElseThrow(
                        () -> new EntityNotFoundException("Can't find order by key: " + key)
                ).getOrder()
        );
    }
}
