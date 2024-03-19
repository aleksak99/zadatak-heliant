package rs.heliant.zadatak.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rs.heliant.zadatak.service.JwtService;

import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private Key signingKey;
    private JwtParser jwtParser;
    @Value("${zadatak.jwt.secret-key}")
    private String secretKey;

    @PostConstruct
    private void init() {
        signingKey = getSignKey();
        jwtParser = Jwts.parserBuilder().setSigningKey(signingKey).build();
    }

    @Override
    public String generateToken(String korisnickoIme) {
        return Jwts.builder()
                .setSubject(korisnickoIme)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*5))
                .signWith(signingKey, SignatureAlgorithm.HS256).compact();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
