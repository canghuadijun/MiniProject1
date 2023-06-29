package com.example.MiniProject1.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    //    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
//    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
//    private final String JWT_SECRET = "lodaaaaaa";
//
//    //Thời gian có hiệu lực của chuỗi jwt
//    private final long JWT_EXPIRATION = 604800000L;
//
//
//    // Tạo ra jwt từ thông tin user
////    public String generateToken(CustomUserDetails userDetails) {
////        Date now = new Date();
////        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
////        // Tạo chuỗi json web token từ username của user.
////        return Jwts.builder()
////                .setSubject(userDetails.getUsername())
////                .setIssuedAt(now)
////                .setExpiration(expiryDate)
////                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
////                .compact();
////    }
//    public String generateToken(Authentication authentication) {
//        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject((userPrincipal.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date().getTime() + JWT_EXPIRATION)))
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                .compact();
//    }
//
//
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException ex) {
//            logger.error("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            logger.error("Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            logger.error("Unsupported JWT token");
//        } catch (IllegalArgumentException ex) {
//            logger.error("JWT claims string is empty.");
//        }
//        return false;
//    }
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private String jwtSecret = "lodaaaaaa";

    private int jwtExpirationMs = 60480000;

    public String generateJwtToken(Authentication authentication) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
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
