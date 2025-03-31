package me.dio.gamehub.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.dio.gamehub.domain.model.Evaluation;
import me.dio.gamehub.domain.model.Game;
import me.dio.gamehub.domain.model.User;

import java.time.LocalDate;

public record EvaluationDto(
        Long id,
        @NotNull(message = "O usuário é obrigatório.") Long userId,
        @NotNull(message = "O jogo é obrigatório.") Long gameId,
        @Min(value = 0, message = "A nota mínima permitida é 0.")
        @Max(value = 10, message = "A nota máxima permitida é 10.")
        @NotNull(message = "A nota da avaliação é obrigatória.") int nota,
        LocalDate evaluationDate
) {

    /**
     * Construtor baseado na entidade Evaluation.
     */
    public EvaluationDto(Evaluation evaluation) {
        this(
                evaluation.getId(),
                evaluation.getUser().getId(),
                evaluation.getGame().getId(),
                evaluation.getNota(),
                evaluation.getEvaluationDate()
        );
    }

    /**
     * Conversão do DTO para a entidade Evaluation.
     */
    public Evaluation toModel() {
        Evaluation evaluation = new Evaluation();
        evaluation.setId(this.id);

        // Criando apenas os objetos com ID para evitar carregamento completo das entidades
        User user = new User();
        user.setId(this.userId);
        evaluation.setUser(user);

        Game game = new Game();
        game.setId(this.gameId);
        evaluation.setGame(game);

        evaluation.setNota(this.nota);
        evaluation.setEvaluationDate(this.evaluationDate != null ? this.evaluationDate : LocalDate.now());

        return evaluation;
    }
}
