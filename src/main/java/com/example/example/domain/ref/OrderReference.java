package com.example.example.domain.ref;

import com.example.example.domain.OrderEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "order_reference")
@Entity
@ToString
@Accessors(chain = true)
public class OrderReference {

    @Id
    @Column(name = "key")
    private String key;


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;


    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
