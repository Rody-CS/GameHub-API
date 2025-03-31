package me.dio.gamehub.controller.dto;

import me.dio.gamehub.domain.model.Game;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public record GameDto(
        Long id,
        String name,
        String genre,
        String developer,
        int releaseYear,
        List<PlatformDto> platforms,
        List<EvaluationDto> evaluations,
        List<CommentDto> comments,
        UserDto user) {

    /**
     * Construtor baseado no modelo Game.
     */
    public GameDto(Game game) {
        this(
            game.getId(),
            game.getName(),
            game.getGenre(),
            game.getDeveloper(),
            game.getReleaseYear(),
            game.getPlatforms() != null ? game.getPlatforms().stream().map(PlatformDto::new).collect(Collectors.toList()) : List.of(),
            game.getEvaluations() != null ? game.getEvaluations().stream().map(EvaluationDto::new).collect(Collectors.toList()) : List.of(),
            game.getComments() != null ? game.getComments().stream().map(CommentDto::new).collect(Collectors.toList()) : List.of(),
            game.getUser() != null ? new UserDto(game.getUser()) : null
        );
    }    

    /**
     * Conversão do DTO para a entidade Game.
     */
    public Game toModel() {
        Game model = new Game();
        model.setId(this.id);
        model.setName(this.name);
        model.setGenre(this.genre);
        model.setDeveloper(this.developer);
        model.setReleaseYear(this.releaseYear);
        model.setPlatforms(
                this.platforms != null ? this.platforms.stream().map(PlatformDto::toModel).collect(toList())
                        : emptyList());
        model.setEvaluations(
                this.evaluations != null ? this.evaluations.stream().map(EvaluationDto::toModel).collect(toList())
                        : emptyList());
        model.setComments(
                this.comments != null ? this.comments.stream().map(CommentDto::toModel).collect(toList())
                        : emptyList());
        model.setUser(this.user != null ? this.user.toModel() : null);
        return model;
    }
}
