package com.twinkles.Norbs_Shopping_Mall.data.repository;

import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
