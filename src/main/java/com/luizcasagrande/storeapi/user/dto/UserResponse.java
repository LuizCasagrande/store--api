package com.luizcasagrande.storeapi.user.dto;

import com.luizcasagrande.storeapi.user.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResponse {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    private UserType type;

    private UserAddressResponse address;
}
