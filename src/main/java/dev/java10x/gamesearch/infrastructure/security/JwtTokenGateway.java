package dev.java10x.gamesearch.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.java10x.gamesearch.application.gateway.TokenGateway;
import dev.java10x.gamesearch.application.gateway.TokenVerifierGateway;
import dev.java10x.gamesearch.config.JWTUserData;
import dev.java10x.gamesearch.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class JwtTokenGateway implements TokenGateway, TokenVerifierGateway {

    @Value("${gamesearch.security.secret}")
    private String secret;

    @Override
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        Instant now = Instant.now();

        return JWT.create()
                .withSubject(user.email())
                .withClaim("userId", user.id())
                .withClaim("name", user.name())
                .withExpiresAt(now.plusSeconds(86400))
                .withIssuedAt(now)
                .withIssuer("API Game Search")
                .sign(algorithm);
    }

    @Override
    public Optional<JWTUserData> verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData
                    .builder()
                    .id(jwt.getClaim("userId").asLong())
                    .name(jwt.getClaim("name").asString())
                    .email(jwt.getSubject())
                    .build());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
