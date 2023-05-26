package com.example.example.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class VendorClient {

    private final WebClient webClient;

    public VendorClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://vendor-service:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @NonNull
    public UUID getVendorId(String vendorCode) {
        return Objects.requireNonNull(this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/codes/{code}").build(vendorCode))
                .retrieve()
                .bodyToMono(VendorDto.class)
                .block()).id;
    }

    @NonNull
    public List<UUID> getPostamatIdsByVendor(UUID vendorId) {
        return Objects.requireNonNull(this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{vendorId}/postamates").build(vendorId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PostamatDto>>() {
                })
                .block()).stream().map(PostamatDto::getId).toList();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class VendorDto {
        private UUID id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class PostamatDto {
        private UUID id;
    }
}
