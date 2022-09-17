package com.luizcasagrande.storeapi.cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    Page<Cart> findByUserId(UUID userId, Pageable pageable);

    Optional<Cart> findByIdAndUserId(UUID id, UUID userId);
}
