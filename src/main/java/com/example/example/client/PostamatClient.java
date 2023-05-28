package com.example.example.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class PostamatClient {

    private final WebClient webClient;

    public PostamatClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://vendor-service:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Nullable
    public UUID getPostamatId(UUID vendorId, String externalId) {
        try {
            return Objects.requireNonNull(this.webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/{vendorId}/postamates/externalIds/{externalId}").build(vendorId, externalId))
                    .retrieve()
                    .bodyToMono(PostamatDto.class)
                    .block()).id;
        } catch (Exception e) {
            log.error("Error while getting postamat at vendor {} with external id {}", vendorId, externalId, e);
            return null;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class PostamatDto {
        private UUID id;
    }
}
