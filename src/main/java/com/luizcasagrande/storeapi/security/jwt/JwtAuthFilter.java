package com.luizcasagrande.storeapi.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.luizcasagrande.storeapi.util.StoreApiConstants.AUTHORITIES;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.BEARER;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.time.LocalDate.now;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final String secret;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            AuthRequest authRequest = objectMapper.readValue(request.getInputStream(), AuthRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                    authRequest.getPassword());

            return authenticationManager.authenticate(authentication);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim(AUTHORITIES, authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(now().plusDays(1)))
                .signWith(hmacShaKeyFor(secret.getBytes()))
                .compact();

        response.setHeader(AUTHORIZATION, BEARER + token);
    }
}
