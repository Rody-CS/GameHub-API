package me.dio.gamehub.controller.dto;

import java.time.LocalDate;

import me.dio.gamehub.domain.model.Comment;

public record CommentDto(
        Long id,
        UserDto user,
        GameDto game,
        String comment,
        LocalDate commentDate,
        EvaluationDto evaluation) {

    /**
     * Construtor baseado na entidade Comment.
     */
    public CommentDto(Comment model) {
        this(
            model.getId(),
            model.getUser() != null ? new UserDto(model.getUser()) : null,
            model.getGame() != null ? new GameDto(model.getGame()) : null,
            model.getComment(),
            model.getCommentDate(),
            model.getEvaluation() != null ? new EvaluationDto(model.getEvaluation()) : null
        );
    }

    /**
     * Conversão do DTO para a entidade Comment.
     */
    public Comment toModel() {
        Comment model = new Comment();
        model.setId(this.id);
        model.setUser(this.user != null ? this.user.toModel() : null);
        model.setGame(this.game != null ? this.game.toModel() : null);
        model.setComment(this.comment);
        model.setCommentDate(this.commentDate != null ? this.commentDate : LocalDate.now());
        model.setEvaluation(this.evaluation != null ? this.evaluation.toModel() : null);
        return model;
    }
}
