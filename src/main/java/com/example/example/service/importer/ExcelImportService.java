package com.example.example.service.importer;

import com.coreoz.windmill.Windmill;
import com.coreoz.windmill.files.FileSource;
import com.coreoz.windmill.imports.Row;
import com.example.example.client.CommentClient;
import com.example.example.client.VendorClient;
import com.example.example.service.OrderService;
import com.example.example.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelImportService {

    private final VendorClient vendorClient;


    private final OrderService orderService;

    private final OrderStatusService orderStatusService;


    private final CommentClient commentClient;

    public void importFile(String vendorCode, MultipartFile file) throws IOException {

        final var vendorId = Objects.requireNonNull(this.vendorClient.getVendorId(vendorCode));

        try (final var rowStream = Windmill.parse(FileSource.of(file.getInputStream()))) {
            this.processStreamRow(rowStream, vendorId);
        }
    }

    private void processStreamRow(Stream<Row> rowStream, UUID vendorId) {
        final var currentNano = LocalDateTime.now().getNano();
        rowStream
                .skip(1)
                .forEach(row -> {
                    final var comment = row.cell(0).asString();
                    if (comment == null || comment.isBlank()) {
                        return;
                    }

                    final var order = this.orderService.create(vendorId, Optional.ofNullable(row.cell(4).asString())
                            .orElse("excel-import-" + currentNano + ":" + UUID.randomUUID())
                    );

                    try {

                        final var client = this.orderStatusService.finishWithComment(vendorId, order.getExternalId());

                        log.info("Created order {}", order.getExternalId());
                        this.commentClient.createComment(client,
                                comment,
                                row.cell(2).asDouble().safeValue()
                        );
                        log.info("Comment sent");
                    } catch (Throwable throwable) {
                        log.error("Order can't be processed! {} '{}'", order.getExternalId(), comment);
                    }
                });
    }
}
