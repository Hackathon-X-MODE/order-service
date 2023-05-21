package com.example.example.controller;

import com.example.example.model.OrderWithMetaDto;
import com.example.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("{orderId}")
    public OrderWithMetaDto get(@PathVariable("orderId") UUID orderId){
        return this.orderService.get(orderId);
    }
}
