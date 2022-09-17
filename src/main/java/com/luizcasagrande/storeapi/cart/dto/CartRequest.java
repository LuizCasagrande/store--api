package com.luizcasagrande.storeapi.cart.dto;

import com.luizcasagrande.storeapi.cart.CartPaymentMethod;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
public class CartRequest {

    @NotNull
    private CartPaymentMethod paymentMethod;

    @NotEmpty
    private Map<UUID, BigDecimal> quantityPerProduct;
}
