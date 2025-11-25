package com.huertohogar.backend.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    @Value("${jwt.secret:miClaveSecretaSuperSeguraParaJWT123456789}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private long expiration;

    private Key getStringKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generarToken(String email, Long userId, String rol) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);
        claims.put("rol", rol);

        Date ahora = new Date();
        Date fechaExpiracion = new Date(ahora.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(ahora)
                .setExpiration(fechaExpiracion)
                .signWith(getStringKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getStringKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Key getSigningKey() {
        return getStringKey();
    }

    public String obtenerEmailDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getStringKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long obtenerUserIdDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object userIdObj = claims.get("userId");
        if (userIdObj instanceof Number) {
            return ((Number) userIdObj).longValue();
        }
        try {
            return Long.valueOf(String.valueOf(userIdObj));
        } catch (Exception e) {
            return null;
        }
    }

    public String obtenerRolDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("rol", String.class);
    }
}
