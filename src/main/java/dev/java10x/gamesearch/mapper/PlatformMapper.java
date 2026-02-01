package dev.java10x.gamesearch.mapper;

import dev.java10x.gamesearch.controllers.request.PlatformRequest;
import dev.java10x.gamesearch.controllers.response.PlatformResponse;
import dev.java10x.gamesearch.entities.Platform;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlatformMapper {

    public static Platform toPlatform(PlatformRequest request) {
        return Platform.builder()
                .name(request.name())
                .build();
    }

    public static PlatformResponse toPlatformResponse(Platform platform) {
        return PlatformResponse.builder()
                .id(platform.getId())
                .name(platform.getName())
                .build();
    }
}
