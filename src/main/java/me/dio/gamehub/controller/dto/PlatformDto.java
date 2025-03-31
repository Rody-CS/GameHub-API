package me.dio.gamehub.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import me.dio.gamehub.domain.model.Platform;

public record PlatformDto(
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String manufacturer,

        @Min(1970)
        int releaseYear
) {
    public PlatformDto(Platform platform) {
        this(platform.getId(), platform.getName(), platform.getManufacturer(), platform.getReleaseYear());
    }

    public Platform toModel() {
        var platform = new Platform();
        platform.setId(id);
        platform.setName(name);
        platform.setManufacturer(manufacturer);
        platform.setReleaseYear(releaseYear);
        return platform;
    }
}
