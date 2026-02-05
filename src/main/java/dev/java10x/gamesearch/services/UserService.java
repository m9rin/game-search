package dev.java10x.gamesearch.services;

import dev.java10x.gamesearch.entities.User;
import dev.java10x.gamesearch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }
}
