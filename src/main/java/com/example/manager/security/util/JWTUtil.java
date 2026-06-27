package com.example.manager.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.manager.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.subject}")
    private String subject;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiration_period_minutes}")
    private Integer expiration_time;

    public String generateToken(User user){
        return JWT.create().withSubject(subject)
                .withClaim("id", user.getId())
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(
                        Date.from(ZonedDateTime.now().plusMinutes(expiration_time).toInstant())
                ).sign(Algorithm.HMAC512(secret));
    }

    public int validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build().verify(token);
        return jwt.getClaim("id").asInt();
    }
}
