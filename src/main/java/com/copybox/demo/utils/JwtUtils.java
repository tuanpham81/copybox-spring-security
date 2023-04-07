package com.copybox.demo.utils;

import com.copybox.demo.service.user.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${copybox.jwtSecret}")
    private String jwtSecret;

    @Value("${copybox.jwtExpirationMs}")
    private int jwtExpirationMs;

    private final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Key signingKey = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(signingKey)
                .compact();
    }

    private Jws<Claims> parseToken(String authToken) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(authToken);
    }

    public String getUsernameFromJwtToken(String authToken) {
        return parseToken(authToken).getBody().getSubject();
    }

    public String getUserIdFromJwtToken(String authToken) {
        return parseToken(authToken).getBody().get("id", String.class);
    }

    public boolean validateJwtToken(String authToken) {
        Key signingKey = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        try {
            Jwts.parser().setSigningKey(signingKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT Signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
