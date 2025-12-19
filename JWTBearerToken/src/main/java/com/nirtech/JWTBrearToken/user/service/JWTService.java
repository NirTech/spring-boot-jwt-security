package com.nirtech.JWTBrearToken.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
// A secret key (at least 256 bits for HS256) encoded in Base64
    private static final String SECRET_KEY = "AtLeast32CharactersLongSStoreItInApplicationPropertiesFileForsecurityReason";

    private SecretKey getSiginingKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
// GEnerate TOken
    public String generateToken(String username) {
        String jwt = Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 *60 * 60 * 24))
                .signWith(getSiginingKey())
                .compact();
        return jwt;
    }
//Extracting Information
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSiginingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);

    }
//  Validation
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenNotExpired(token));
    }

    private boolean isTokenNotExpired(String token) {
        return extractClaim(token, Claims :: getExpiration).before(new Date());
    }
}
