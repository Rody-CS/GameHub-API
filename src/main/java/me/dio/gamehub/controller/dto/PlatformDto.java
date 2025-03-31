package me.dio.gamehub.controller.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.dio.gamehub.domain.model.Platform;

public record PlatformDto(
        Long id,
        String name,
        String manufacturer,
        int releaseYear,
        List<GameDto> games) {

    /**
     * Construtor baseado no modelo Platform.
     */
    public PlatformDto(Platform model) {
        this(
            model.getId(),
            model.getName(),
            model.getManufacturer(),
            model.getReleaseYear(),
            Optional.ofNullable(model.getGames())
                .orElse(List.of())
                .stream()
                .map(GameDto::new) // Converte cada Game em GameDto
                .collect(Collectors.toList())
        );
    }

    /**
     * Conversão do DTO para a entidade Platform.
     */
    public Platform toModel() {
        Platform model = new Platform();
        model.setId(this.id);
        model.setName(this.name);
        model.setManufacturer(this.manufacturer);
        model.setReleaseYear(this.releaseYear);
        model.setGames(
            Optional.ofNullable(this.games)
                .orElse(List.of())
                .stream()
                .map(GameDto::toModel) // Converte cada GameDto em Game
                .collect(Collectors.toList())
        );
        return model;
    }
}
