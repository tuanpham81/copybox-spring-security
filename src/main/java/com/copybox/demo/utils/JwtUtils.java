package com.copybox.demo.utils;

import com.copybox.demo.service.user.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${copybox.jwtSecret}")
    private String jwtSecret;

    @Value("${copybox.jwtExpirationMs}")
    private int jwtExpirationMs;

    private Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    public String generateJwtKey(Authentication authentication){
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setId(user.getId())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String getUsernameFromJwtToken(String authToken){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().getSubject();
    }

    public String getUserIdFromJwtToken(String authToken){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().getId();
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT Signature: {}",e.getMessage());
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT Token: {}",e.getMessage());
        } catch (ExpiredJwtException e){
            logger.error("JWT token is expired: {}",e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("JWT token is unsupported: {}",e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
