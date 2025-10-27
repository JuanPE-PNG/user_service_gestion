package com.example.userservice.service;

import com.example.userservice.entity.UserInfo;
import com.example.userservice.repository.UserInfoRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Value("${JWT_SECRET}")
    private String secret;

    public String generateToken(String email) {
        Map<String,Object> claims = new HashMap<>();
        long currentTime = System.currentTimeMillis();

        Optional<UserInfo> userOpt = userInfoRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        UserInfo user = userOpt.get();

        claims.put("id", user.getId());
        claims.put("email", email);
        claims.put("name", user.getName());
        claims.put("lastName", user.getLastName());
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + 1000 * 60 * 5))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

    public boolean isAdminUser(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);
            System.out.println("Token role: " + role); // Debug log

            return "Administrador".equalsIgnoreCase(role);
        } catch (Exception e) {
            System.out.println("Error in isAdminUser: " + e.getMessage()); // Debug log
            e.printStackTrace();
            return false;
        }
    }


    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}