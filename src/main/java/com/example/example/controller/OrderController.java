package com.example.example.controller;

import com.example.example.model.OrderFilter;
import com.example.example.model.OrderWithMetaDto;
import com.example.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получение заказа по его ID")
    @GetMapping("{orderId}")
    public OrderWithMetaDto get(@PathVariable("orderId") UUID orderId) {
        return this.orderService.get(orderId);
    }

    @Operation(summary = "Получение заказов по списку ID")
    @PostMapping("/list")
    public List<OrderWithMetaDto> get(@RequestBody List<UUID> ids) {
        return this.orderService.get(ids);
    }

    @Operation(summary = "Получение заказа по фильтрам")
    @GetMapping
    public Page<OrderWithMetaDto> pagination(
            OrderFilter filter,
            Pageable pageable
    ) {
        return this.orderService.get(filter, pageable);
    }
}
