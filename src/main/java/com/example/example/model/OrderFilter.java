package com.example.example.model;

import com.example.example.domain.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilter {

    private String orderId;

    private UUID postamatId;

    private StatusOrder status;
}
