package com.example.example.service;

import com.example.example.client.PostamatClient;
import com.example.example.client.VendorClient;
import com.example.example.mapper.OrderMapper;
import com.example.example.model.OrderDto;
import com.example.example.model.PostamatMetaDto;
import com.example.example.service.status.StatusUpdaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderInitService {

    private final VendorClient vendorClient;

    private final PostamatClient postamatClient;

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    private final StatusUpdaterService statusUpdaterService;

    @Transactional
    public void createOrUpdate(String vendorCode, OrderDto orderDto) {
        final var vendorId = this.vendorClient.getVendorId(vendorCode);
        final var postamatId = this.getPostamatId(orderDto.getPostamat());

        final var order = this.orderService.get(vendorId, orderDto.getOrderId());
        this.statusUpdaterService.update(order, orderDto.getStatus());
        this.orderMapper.update(order, orderDto);
        if (postamatId != null) {
            order.setPostamatId(postamatId);
        }

    }

    @Nullable
    private UUID getPostamatId(PostamatMetaDto postamatMetaDto) {
        if (postamatMetaDto == null) {
            return null;
        }
        log.info("Try get postamatId at {} with {}", postamatMetaDto.getVendorCode(), postamatMetaDto.getVendorCode());
        if (postamatMetaDto.getVendorCode() == null || postamatMetaDto.getPostamatExternalId() == null) {
            return null;
        }

        try {
            return this.postamatClient.getPostamatId(
                    this.vendorClient.getVendorId(postamatMetaDto.getVendorCode()),
                    postamatMetaDto.getPostamatExternalId()
            );
        } catch (Exception e) {
            log.warn("Can't get postamat...");
            return null;
        }
    }
}
