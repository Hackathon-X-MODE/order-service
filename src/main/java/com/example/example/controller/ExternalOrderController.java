package com.example.example.controller;

import com.example.example.configuration.WebConstants;
import com.example.example.model.OrderDto;
import com.example.example.model.OrderWithMetaDto;
import com.example.example.service.OrderExtendService;
import com.example.example.service.OrderExternalService;
import com.example.example.service.OrderInitService;
import com.example.example.service.OrderStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class ExternalOrderController {

    private final OrderInitService orderService;


    private final OrderStatusService orderStatusService;

    private final OrderExtendService orderExtendService;

    private final OrderExternalService orderExternalService;

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrInit(
            @RequestHeader(WebConstants.Header.EXTERNAL_SYSTEM) String externalId,
            @RequestBody OrderDto orderDto
    ) {
        this.orderService.createOrUpdate(externalId, orderDto);
    }

    @Operation(summary = "Завершение заказа", description = "После завершения заказа будет создана ссылка для оставления отзыва",
            responses = {
                    @ApiResponse(
                            description = "Успех",
                            responseCode = "200",
                            content = @Content(examples = {@ExampleObject(value = "https://hack.bigtows.org/feedback/415c2c1d9")})
                    )
            })
    @PutMapping("{orderId}/status/finished")
    public String finishOrder(
            @RequestHeader(WebConstants.Header.EXTERNAL_SYSTEM) String externalId,
            @PathVariable("orderId") String orderExternalId
    ) {
        return this.orderStatusService.finishWithComment(externalId, orderExternalId);
    }


    @Operation(summary = "Получение данных о заказе", description = "С помощью внешнего идентификатора заказа получить информацию по заказу",
            responses = {
                    @ApiResponse(
                            description = "Успех",
                            responseCode = "200",
                            content = @Content(examples = {@ExampleObject(value = "https://hack.bigtows.org/feedback/415c2c1d9")})
                    )
            })
    @GetMapping("{orderId}")
    public OrderWithMetaDto getOrder(
            @RequestHeader(WebConstants.Header.EXTERNAL_SYSTEM) String externalId,
            @PathVariable("orderId") String orderExternalId
    ) {
        return this.orderExternalService.getExternalOrder(externalId, orderExternalId);
    }

    @Operation(
            summary = "Продлить время ожидание для заказа",
            description = "Для заказа будет записана новая дата хранения."
    )
    @PutMapping("{orderId}/extend")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void extend(
            @RequestHeader(WebConstants.Header.EXTERNAL_SYSTEM) String vendorCode,
            @PathVariable("orderId") String orderExternalId
    ){
        this.orderExtendService.extend(vendorCode, orderExternalId);
    }
}
