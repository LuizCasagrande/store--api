package com.luizcasagrande.storeapi.cart.dto;

import com.luizcasagrande.storeapi.product.dto.ProductResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartProductResponse {

    private BigDecimal quantity;

    private ProductResponse product;
}
