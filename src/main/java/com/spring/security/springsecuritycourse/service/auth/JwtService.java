package com.spring.security.springsecuritycourse.service.auth;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    
    public String generateToken(UserDetails user,Map<String,Object> extraClaims ) {
        
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES*60*1000)+issuedAt.getTime());
        //! Claims
        // son afirmaciones o declaraciones que se incluyen en el cuerpo del token
        // y continenen informacion sobre el usuario y los permisos asociados

        String jwt = Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())    
                .issuedAt(issuedAt)
                .expiration(expiration)
                .header().type("JWT").and()
                .signWith(generateKey()).compact();

        return jwt;
    };

    public Key generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    public String extractUsername(String jwt) {

        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {

        return Jwts.parser()
                .verifyWith((SecretKey) generateKey()).build()
                .parseSignedClaims(jwt) // parsea un token firmado
                .getPayload();
    }
    
    
}
