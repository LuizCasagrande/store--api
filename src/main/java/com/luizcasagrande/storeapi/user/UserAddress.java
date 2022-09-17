package com.luizcasagrande.storeapi.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luizcasagrande.storeapi.framework.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAddress extends AbstractEntity {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String zipcode;

    @JsonBackReference
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;
}
