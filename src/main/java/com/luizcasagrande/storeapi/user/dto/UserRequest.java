package com.luizcasagrande.storeapi.user.dto;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
public class UserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @Valid
    private UserAddressRequest address;
}
