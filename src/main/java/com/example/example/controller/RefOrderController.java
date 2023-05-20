package com.example.example.controller;

import com.example.example.model.OrderWithMetaDto;
import com.example.example.service.reference.OrderReferenceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("refs")
@RequiredArgsConstructor
public class RefOrderController {


    private final OrderReferenceService orderReferenceService;

    @Operation(summary = "Получение информации о заказе по ссылке")
    @GetMapping("{key}")
    public OrderWithMetaDto getOrderByRef(
            @PathVariable("key") String key
    ) {
        return this.orderReferenceService.byKey(key);
    }
}
