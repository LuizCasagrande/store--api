package com.luizcasagrande.storeapi.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressResponse {

    private String city;

    private String street;

    private String number;

    private String zipcode;
}
