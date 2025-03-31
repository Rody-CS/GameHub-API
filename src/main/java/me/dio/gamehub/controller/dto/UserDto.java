package me.dio.gamehub.controller.dto;

import me.dio.gamehub.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

public record UserDto(
        Long id,
        String name,
        String email,
        List<LibraryDto> library,  // Relacionamento com jogos na biblioteca do usuário
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
            ofNullable(model.getLibrary()).orElse(emptyList()).stream().map(LibraryDto::new).collect(Collectors.toList()),
            ofNullable(model.getEvaluations()).orElse(emptyList()).stream().map(EvaluationDto::new).collect(Collectors.toList()),
            ofNullable(model.getComments()).orElse(emptyList()).stream().map(CommentDto::new).collect(Collectors.toList())
        );
    }

    /**
     * Conversão do DTO para a entidade User.
     */
    public User toModel() {
        User model = new User();
        model.setId(this.id);
        model.setName(this.name);
        model.setEmail(this.email);
        model.setLibrary(
            ofNullable(this.library).orElse(emptyList()).stream().map(LibraryDto::toModel).collect(Collectors.toList())
        );
        model.setEvaluations(
            ofNullable(this.evaluations).orElse(emptyList()).stream().map(EvaluationDto::toModel).collect(Collectors.toList())
        );
        model.setComments(
            ofNullable(this.comments).orElse(emptyList()).stream().map(CommentDto::toModel).collect(Collectors.toList())
        );
        return model;
    }
}
