package com.example.example.repository;

import com.example.example.domain.ref.OrderReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReferenceRepository extends JpaRepository<OrderReference, String> {
}
