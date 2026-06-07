package dev.java10x.gamesearch.infrastructure.persistence.mapper;

import dev.java10x.gamesearch.domain.model.User;
import dev.java10x.gamesearch.infrastructure.persistence.entity.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserPersistenceMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .password(user.password())
                .build();
    }

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword()
        );
    }
}
