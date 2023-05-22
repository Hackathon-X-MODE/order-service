package com.example.example.model;

import com.example.example.domain.OrderMeta;
import com.example.example.domain.Person;
import com.example.example.domain.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.net.URL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String orderId;

    private PostamatMetaDto postamat;

    private BigDecimal sum;

    private StatusOrder status;

    private Person person;

    private String description;

    private OrderMeta meta;

    private URL ref;
}
