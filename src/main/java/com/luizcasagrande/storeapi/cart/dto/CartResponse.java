package com.luizcasagrande.storeapi.cart.dto;

import com.luizcasagrande.storeapi.cart.CartPaymentMethod;
import com.luizcasagrande.storeapi.user.dto.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CartResponse {

    private UUID id;

    private LocalDateTime date;

    private BigDecimal total;

    private CartPaymentMethod paymentMethod;

    private UserResponse user;

    private List<CartProductResponse> products;
}
