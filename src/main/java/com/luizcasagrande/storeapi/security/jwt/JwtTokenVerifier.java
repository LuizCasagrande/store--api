package com.luizcasagrande.storeapi.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.luizcasagrande.storeapi.util.StoreApiConstants.AUTHORITIES;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.AUTHORITY;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.BEARER;
import static com.luizcasagrande.storeapi.util.StoreApiConstants.INVALID_TOKEN;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(AUTHORIZATION);

        if (isEmpty(header) || !header.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(BEARER, EMPTY);

        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = body.getSubject();

            var authorities = (List<Map<String, String>>) body.get(AUTHORITIES);

            List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(e -> new SimpleGrantedAuthority(e.get(AUTHORITY)))
                    .toList();

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            throw new IllegalStateException(INVALID_TOKEN);
        }

        filterChain.doFilter(request, response);
    }
}
