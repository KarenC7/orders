package com.ecommerce.orders.util;


import com.ecommerce.orders.exception.JwtSignatureException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    // Generamos una clave segura para HS512
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long jwtExpirationMs = 86400000; // 24 horas

    public String generateJwtToken(String username) {
        log.info("Generating JWT token for user: {}", username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("JWT signature does not match locally computed signature: {}", e.getMessage());
            throw new JwtSignatureException("JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.", e);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtSignatureException("Invalid JWT token", e);
        }
    }
}