package com.example.example.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Component
public class CommentClient {

    private final WebClient webClient;

    public CommentClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://comment-service:" + Optional.ofNullable(System.getenv("SERVER_HTTP_PORT")).orElse("8083"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void createComment(String key, String comment, double rate) {
        this.webClient.post()
                .header("X-Ref-Order", key)
                .bodyValue(
                        Map.of(
                                "source", "QR",
                                "comment", comment,
                                "rate", rate
                        )
                ).retrieve()
                .toBodilessEntity()
                .block();

    }
}
