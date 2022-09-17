package com.luizcasagrande.storeapi.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("select p.category from Product p")
    Set<String> findAllCategory();

    Page<Product> findByCategory(String category, Pageable pageable);

    Set<Product> findByIdIn(Set<UUID> id);
}
