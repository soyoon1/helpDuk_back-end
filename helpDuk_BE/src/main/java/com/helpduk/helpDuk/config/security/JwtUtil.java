package com.helpduk.helpDuk.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {

    @Value("${jwt.secret}")
    private static final String secretKey = "secretKey";

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    public static Integer getCurrentMemberId(String token) {
        // Parse the token
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Integer userId = claims.get("userId", Integer.class); // Replace "userId" with the actual key used in the token
        LOGGER.info("[JwtUtil] userId : {}", userId);
        return userId;
    }
}
