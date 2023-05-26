package com.example.example.service.importer;

import com.coreoz.windmill.Windmill;
import com.coreoz.windmill.files.FileSource;
import com.coreoz.windmill.imports.Row;
import com.example.example.client.VendorClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelImportService {

    private final VendorClient vendorClient;

    private final OrderImportService orderImportService;

    @Async
    public void importFile(String vendorCode, byte[] bytes) {

        final var vendorId = Objects.requireNonNull(this.vendorClient.getVendorId(vendorCode));
        final var postamats = this.vendorClient.getPostamatIdsByVendor(vendorId);

        try (final var rowStream = Windmill.parse(FileSource.of(bytes))) {
            log.info("Import started");
            this.processStreamRow(rowStream, vendorId, postamats);
        }
        log.info("Import end");
    }

    private void processStreamRow(Stream<Row> rowStream, UUID vendorId, List<UUID> postamats) {
        final var currentNano = LocalDateTime.now().getNano();
        final var atomic = new AtomicLong(1);
        final var rand = new Random();
        rowStream
                .skip(1)
                .parallel()
                .forEach(row -> {
                    final var comment = row.cell(0).asString();
                    if (comment == null || comment.isBlank()) {
                        return;
                    }
                    final var externalOrderId = "import-" + currentNano + ":" + atomic.getAndIncrement();
                    try {
                        this.orderImportService.importOrder(
                                vendorId,
                                postamats.get(rand.nextInt(postamats.size())),
                                externalOrderId
                        );
                        this.orderImportService.finishOrder(
                                vendorId,
                                externalOrderId,
                                comment,
                                row.cell(2).asDouble().safeValue()
                        );
                    } catch (Throwable t) {
                        log.error("Order can't be processed! {} '{}'", externalOrderId, comment, t);
                    }
                });
    }
}
