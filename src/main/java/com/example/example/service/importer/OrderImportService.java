package com.example.example.service.importer;

import com.example.example.client.CommentClient;
import com.example.example.domain.DateHistory;
import com.example.example.domain.OrderMeta;
import com.example.example.domain.Person;
import com.example.example.service.OrderService;
import com.example.example.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderImportService {

    private final OrderService orderService;

    private final OrderStatusService orderStatusService;

    private final CommentClient commentClient;

    @Transactional
    public void importOrder(UUID vendorId, UUID postamatId, String externalOrderId) {
        final var order = this.orderService.create(vendorId, externalOrderId);
        final var random = new Random();
        order
                .setPerson(
                        new Person()
                                .setFullName("Иван Иванович Импортер")
                                .setEmail("example@bigtows.org")
                                .setSex(random.nextBoolean())
                                .setDate(LocalDate.now())
                                .setPhone("8800553535")
                )
                .setSum(BigDecimal.valueOf(random.nextLong(99_999) + 1))
                .setDescription("Волшебный заказ из Excel")
                .setMeta(new OrderMeta()
                        .setDepth(random.nextInt(1000) + 1)
                        .setWidth(random.nextInt(1000) + 1)
                        .setHeight(random.nextInt(1000) + 1)
                        .setWidth(random.nextInt(5000) + 1)
                )
                .setPostamatId(postamatId)
                .setDateHistory(
                        new DateHistory()
                                .setGet(LocalDateTime.now())
                );
        log.info("Created order {}", order.getExternalId());
    }

    public void finishOrder(UUID vendorId, String externalOrderId, String comment, double rate) {
        final var client = this.orderStatusService.finishWithComment(vendorId, externalOrderId);
        this.commentClient.createComment(client, comment, rate);
        log.info("Comment sent");
    }
}
