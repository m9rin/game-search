package dev.java10x.gamesearch.interfaceadapter.rest.mapper;

import dev.java10x.gamesearch.domain.model.Platform;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.PlatformRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.PlatformResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlatformRestMapper {

    public static Platform toDomain(PlatformRequest request) {
        return new Platform(null, request.name());
    }

    public static PlatformResponse toResponse(Platform platform) {
        return PlatformResponse
                .builder()
                .id(platform.id())
                .name(platform.name())
                .build();
    }
}
