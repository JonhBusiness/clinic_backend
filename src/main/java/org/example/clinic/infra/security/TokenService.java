package org.example.clinic.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.example.clinic.domain.usuario.Usuario;
import org.example.clinic.infra.exceptions.TokenCreationException;
import org.example.clinic.infra.exceptions.TokenExpiradoException;
import org.example.clinic.infra.exceptions.TokenMalFormadoException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {
    private static final String ISSUER = "voll med";
    private static final String ROLE_CLAIM = "role";

    @Value("${jwt.secret}")
    private String apiSecret;

    @Value("${jwt.expiration.hours}")
    private long expirationHours;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(apiSecret);
    }

    private Instant generarFechaExpiracion() {
        return Instant.now().plus(expirationHours, ChronoUnit.HOURS);
    }

    public String generarToken(Usuario usuario) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getUsername())
                    .withClaim(ROLE_CLAIM, usuario.getRole().name())
                    .withExpiresAt(Date.from(generarFechaExpiracion()))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new TokenCreationException("Error al generar el token", exception);
        }
    }

    public String getSubject(String token) {

        try {
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
            String subject = verifier.getSubject();
            if (subject == null) {
                throw new TokenMalFormadoException("Token sin subject");
            }
            return subject;
        } catch (TokenExpiredException exception) {
            throw new TokenExpiradoException("Token expirado", exception);
        } catch (JWTVerificationException exception) {
            throw new TokenMalFormadoException("Token mal formado", exception);
        }
    }
}