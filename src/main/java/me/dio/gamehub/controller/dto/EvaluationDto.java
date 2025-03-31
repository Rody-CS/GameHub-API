package me.dio.gamehub.controller.dto;

import java.time.LocalDate;

import me.dio.gamehub.domain.model.Evaluation;

public record EvaluationDto(
        Long id,
        UserDto user,
        int nota,
        GameDto game,
        LocalDate evaluationDate) {

    /**
     * Construtor baseado no modelo Evaluation.
     */
    public EvaluationDto(Evaluation model) {
        this(
            model.getId(),
            new UserDto(model.getUser()),
            model.getNota(),
            new GameDto(model.getGame()),
            model.getEvaluationDate()
        );
    }

    /**
     * Conversão do DTO para a entidade Evaluation.
     */
    public Evaluation toModel() {
        Evaluation model = new Evaluation();
        model.setId(this.id);
        model.setUser(this.user != null ? this.user.toModel() : null);
        model.setNota(this.nota);
        model.setGame(this.game != null ? this.game.toModel() : null);
        model.setEvaluationDate(this.evaluationDate != null ? this.evaluationDate : LocalDate.now());
        return model;
    }
}
