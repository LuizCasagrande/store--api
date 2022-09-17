package com.luizcasagrande.storeapi.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserAddressRequest {

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @NotBlank
    private String zipcode;
}
