package br.com.jvrss.library.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String secretKey = "mysecretkeymysecretkeymysecretkey12"; // Ensure this is at least 32 bytes

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", secretKey);
    }

    @Test
    public void testGenerateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        assertNotNull(token);
    }

    @Test
    public void testExtractUsername() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testValidateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        assertTrue(jwtUtil.validateToken(token, username));
    }

    @Test
    public void testIsTokenExpired() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        assertTrue(jwtUtil.validateToken(token, username));

        // Simulate token expiration by setting the expiration date to the past
        ReflectionTestUtils.setField(jwtUtil, "secret", secretKey);
        String expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 10)) // 10 hours ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // 1 hour ago
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> jwtUtil.validateToken(expiredToken, username));
    }
}