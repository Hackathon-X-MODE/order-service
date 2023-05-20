package com.example.example.model;

import com.example.example.domain.DateHistory;
import com.example.example.domain.OrderMeta;
import com.example.example.domain.Person;
import com.example.example.domain.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderWithMetaDto {

    private UUID id;

    private String externalId;

    private UUID vendorId;
    private UUID postamatId;

    private BigDecimal sum;

    private StatusOrder status;

    private Person person;

    private List<String> categories;

    private OrderMeta meta;

    private URL ref;

    private DateHistory dateHistory;
}
