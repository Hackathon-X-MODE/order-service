package com.example.example.service;

import com.example.example.client.VendorClient;
import com.example.example.mapper.OrderMapper;
import com.example.example.model.OrderWithMetaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderExternalService {

    private final VendorClient vendorClient;

    private final OrderService orderService;

    private final OrderMapper orderMapper;


    public OrderWithMetaDto getExternalOrder(String vendorCode, String externalOrderId) {
        return this.orderMapper.toDto(this.orderService.get(this.vendorClient.getVendorId(vendorCode), externalOrderId));
    }
}
