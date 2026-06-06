package dev.java10x.gamesearch.infrastructure.persistence.mapper;

import dev.java10x.gamesearch.domain.model.Platform;
import dev.java10x.gamesearch.infrastructure.persistence.entity.PlatformEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlatformPersistenceMapper {

    public static PlatformEntity toEntity(Platform platform) {
        return PlatformEntity
                .builder()
                .id(platform.id())
                .name(platform.name())
                .build();
    }

    public static Platform toDomain(PlatformEntity entity) {
        return new Platform(
                entity.getId(),
                entity.getName()
        );
    }
}
