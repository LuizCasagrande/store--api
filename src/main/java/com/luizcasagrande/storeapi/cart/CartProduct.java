package com.luizcasagrande.storeapi.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luizcasagrande.storeapi.framework.AbstractEntity;
import com.luizcasagrande.storeapi.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartProduct extends AbstractEntity {

    @Column(nullable = false)
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cart cart;
}
