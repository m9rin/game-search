package dev.java10x.gamesearch.application.gateway;

public interface PasswordMatcherGateway {

    boolean matches(String rawPassword, String encodedPassword);
}
