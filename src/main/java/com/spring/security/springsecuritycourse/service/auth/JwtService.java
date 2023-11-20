package com.spring.security.springsecuritycourse.service.auth;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
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
                //.setClaims(extraClaims)
                .subject(user.getUsername())    
                //.setSubject(user.getUsername())
                .issuedAt(issuedAt)
                //.setIssuedAt(issuedAt)
                .expiration(expiration)
                //.setExpiration(expiration)
                .header().type("JWT").and()
                //.setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .signWith(generateKey()).compact();
                //.signWith(generateKey(),Jwts.SIG.HS256).compact();
                //.signWith(generateKey(), SignatureAlgorithm.HS256).compact();

        return jwt;
    };

    public Key generateKey() {
        byte[] key = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(key);
    }
}
