package com.example.example.repository;

import com.example.example.domain.OrderEntity;
import com.example.example.model.OrderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {


    Optional<OrderEntity> findByExternalIdAndVendorId(String externalId, UUID vendorId);

    default Page<OrderEntity> filter(OrderFilter filter, Pageable pageable) {
        Specification<OrderEntity> specification = Specification.where(null);


        if (Objects.nonNull(filter.getOrderId())) {
            specification = specification.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("externalId")),
                            "%" + filter.getOrderId().toLowerCase() + "%"
                    )
            );
        }


        return this.findAll(specification, pageable);
    }
}
