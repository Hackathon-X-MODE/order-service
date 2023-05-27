package com.example.example.service;

import com.example.example.client.VendorClient;
import com.example.example.domain.StatusOrder;
import com.example.example.service.reference.OrderReferenceService;
import com.example.example.service.status.StatusUpdaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private static final String FEEDBACK_URL = "https://hack.bigtows.org/mobile/";

    private final VendorClient vendorClient;

    private final OrderService orderService;

    private final OrderReferenceService orderReferenceService;

    private final StatusUpdaterService statusUpdaterService;

    @Transactional
    public String finishWithComment(String vendorCode, String externalOrderId) {
        final var vendor = this.vendorClient.getVendorId(vendorCode);
        return FEEDBACK_URL + this.finishWithComment(vendor, externalOrderId);
    }


    @Transactional
    public String finishWithComment(UUID vendorId, String externalOrderId) {
        final var order = this.orderService.get(vendorId, externalOrderId);

        this.statusUpdaterService.update(order, StatusOrder.FINISHED);
        return this.orderReferenceService.generate(order);
    }
}
