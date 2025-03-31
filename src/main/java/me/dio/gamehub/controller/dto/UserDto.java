package me.dio.gamehub.controller.dto;

import me.dio.gamehub.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public record UserDto(
                Long id,
                String name,
                String email,
                String password,
                List<GameDto> libraryGames,
                List<EvaluationDto> evaluations,
                List<CommentDto> comments) {

        /**
         * Construtor baseado na entidade User.
         */
        public UserDto(User model) {
                this(
                                model.getId(),
                                model.getName(),
                                model.getEmail(),
                                model.getPassword(),
                                model.getLibraryGames() != null ? model.getLibraryGames().stream().map(GameDto::new)
                                                .collect(Collectors.toList()) : List.of(),
                                model.getEvaluations() != null ? model.getEvaluations().stream().map(EvaluationDto::new)
                                                .collect(Collectors.toList()) : List.of(),
                                model.getComments() != null ? model.getComments().stream().map(CommentDto::new)
                                                .collect(Collectors.toList()) : List.of());
        }

        /**
         * Conversão do DTO para a entidade User.
         */
        public User toModel() {
                User model = new User();
                model.setId(this.id);
                model.setName(this.name);
                model.setEmail(this.email);
                model.setPassword(this.password);
                model.setLibraryGames(
                                this.libraryGames != null
                                                ? this.libraryGames.stream().map(GameDto::toModel).collect(toList())
                                                : emptyList());
                model.setEvaluations(
                                this.evaluations != null
                                                ? this.evaluations.stream().map(EvaluationDto::toModel)
                                                                .collect(toList())
                                                : emptyList());
                model.setComments(
                                this.comments != null
                                                ? this.comments.stream().map(CommentDto::toModel).collect(toList())
                                                : emptyList());
                return model;
        }
}
