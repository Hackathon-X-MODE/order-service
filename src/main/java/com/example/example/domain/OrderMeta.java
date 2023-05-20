package com.example.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderMeta {

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;


    @Column(name = "depth")
    private Integer depth;

    @Column(name = "weight")
    private Integer weight;

}
