package dev.java10x.gamesearch.mapper;

import dev.java10x.gamesearch.controllers.request.UserRequest;
import dev.java10x.gamesearch.controllers.response.UserResponse;
import dev.java10x.gamesearch.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(UserRequest request) {

        return User
                .builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public static UserResponse toUserResponse(User user) {

        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}
