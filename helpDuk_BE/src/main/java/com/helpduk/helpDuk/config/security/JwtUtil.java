package com.helpduk.helpDuk.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {

    @Value("${jwt.secret}")
    private static final String secretKey = "secretKey";

    public static Integer getCurrentMemberId(String token) {
        // Parse the token
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Integer.class); // Replace "userId" with the actual key used in the token
    }
}
