package dev.java10x.gamesearch.infrastructure.security;

import dev.java10x.gamesearch.application.gateway.PasswordEncoderGateway;
import dev.java10x.gamesearch.application.gateway.PasswordMatcherGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordGateway implements PasswordEncoderGateway, PasswordMatcherGateway {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
