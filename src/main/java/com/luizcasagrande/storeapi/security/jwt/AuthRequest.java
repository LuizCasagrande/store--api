package com.luizcasagrande.storeapi.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    private String email;

    private String password;
}
