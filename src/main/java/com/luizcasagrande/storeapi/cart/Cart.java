package com.luizcasagrande.storeapi.cart;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luizcasagrande.storeapi.framework.AbstractEntity;
import com.luizcasagrande.storeapi.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart extends AbstractEntity {

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private BigDecimal total;

    @Enumerated(STRING)
    @Column(nullable = false)
    private CartPaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "cart", cascade = ALL, orphanRemoval = true)
    private List<CartProduct> products = new ArrayList<>();
}
