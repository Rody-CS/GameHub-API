package me.dio.gamehub.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import me.dio.gamehub.domain.model.Game;

import java.util.List;
import java.util.stream.Collectors;

public record GameDto(
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String genre,

        @NotBlank
        String developer,

        @Min(1970)
        int releaseYear,

        List<Long> platformIds // Apenas IDs para evitar carregar objetos pesados
) {
    public GameDto(Game game) {
        this(
                game.getId(),
                game.getName(),
                game.getGenre(),
                game.getDeveloper(),
                game.getReleaseYear(),
                game.getPlatforms().stream().map(platform -> platform.getId()).collect(Collectors.toList())
        );
    }

    public Game toModel() {
        Game game = new Game();
        game.setId(this.id);
        game.setName(this.name);
        game.setGenre(this.genre);
        game.setDeveloper(this.developer);
        game.setReleaseYear(this.releaseYear);
        return game;
    }
}
