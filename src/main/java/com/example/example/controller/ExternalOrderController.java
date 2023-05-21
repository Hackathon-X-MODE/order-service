package com.example.example.controller;

import com.example.example.configuration.WebConstants;
import com.example.example.model.OrderDto;
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
}
