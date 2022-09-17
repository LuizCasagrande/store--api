package com.luizcasagrande.storeapi.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductResponse {

    private UUID id;

    private String title;

    private String description;

    private BigDecimal price;

    private String category;

    private String image;
}
