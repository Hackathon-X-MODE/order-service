package com.example.example.service;

import com.example.example.client.VendorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderExtendService {

    private final VendorClient vendorClient;

    private final OrderService orderService;

    public void extend(String vendorCode, String orderExternalId) {
        final var vendorId = this.vendorClient.getVendorId(vendorCode);
        final var order = this.orderService.get(vendorId, orderExternalId);

        order.getDateHistory().getStorage().add(LocalDateTime.now());
    }
}
