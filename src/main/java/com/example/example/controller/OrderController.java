package com.example.example.controller;

import com.example.example.config.WebConstants;
import com.example.example.model.OrderDto;
import com.example.example.service.OrderInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderInitService orderService;

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrInit(
            @RequestHeader(WebConstants.Header.EXTERNAL_SYSTEM) String externalId,
            @RequestBody OrderDto orderDto
    ) {
        this.orderService.createOrUpdate(externalId, orderDto);
    }
}
