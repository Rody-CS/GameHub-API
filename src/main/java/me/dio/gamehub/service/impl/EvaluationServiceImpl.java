package me.dio.gamehub.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dio.gamehub.domain.model.Evaluation;
import me.dio.gamehub.domain.model.Game;
import me.dio.gamehub.domain.model.User;
import me.dio.gamehub.domain.repository.EvaluationRepository;
import me.dio.gamehub.domain.repository.GameRepository;
import me.dio.gamehub.domain.repository.UserRepository;
import me.dio.gamehub.service.EvaluationService;
import me.dio.gamehub.service.exception.BusinessException;
import me.dio.gamehub.service.exception.NotFoundException;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.evaluationRepository = evaluationRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Evaluation> findAll(Pageable pageable) {
        return evaluationRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Evaluation findById(Long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Avaliação com ID %d não encontrada.", id)));
    }

    @Transactional
    @Override
    public Evaluation create(Evaluation evaluationToCreate) {
        validateEvaluation(evaluationToCreate);

        Long userId = evaluationToCreate.getUser().getId();
        Long gameId = evaluationToCreate.getGame().getId();

        // Busca usuário e jogo apenas uma vez
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Usuário com ID %d não encontrado.", userId)));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(String.format("Jogo com ID %d não encontrado.", gameId)));

        // Associa entidades
        evaluationToCreate.setUser(user);
        evaluationToCreate.setGame(game);

        return evaluationRepository.save(evaluationToCreate);
    }

    @Transactional
    @Override
    public Evaluation update(Long id, Evaluation evaluationToUpdate) {
        Evaluation existingEvaluation = findById(id);
        validateEvaluation(evaluationToUpdate);

        existingEvaluation.setNota(evaluationToUpdate.getNota());

        return evaluationRepository.save(existingEvaluation);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Evaluation existingEvaluation = findById(id);
        evaluationRepository.delete(existingEvaluation);
    }

    @Transactional(readOnly = true)
    public Page<Evaluation> findByGameId(Long gameId, Pageable pageable) {
        if (!gameRepository.existsById(gameId)) {
            throw new NotFoundException(String.format("Jogo com ID %d não encontrado.", gameId));
        }
        return evaluationRepository.findByGameId(gameId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Evaluation> findByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Usuário com ID %d não encontrado.", userId));
        }
        return evaluationRepository.findByUserId(userId, pageable);
    }

    /**
     * Valida os dados da avaliação.
     */
    private void validateEvaluation(Evaluation evaluation) {
        if (evaluation == null) {
            throw new BusinessException("A avaliação não pode ser nula.");
        }
        Integer nota = evaluation.getNota();
        if (nota == null || nota < 0 || nota > 10) {
            throw new BusinessException("A nota da avaliação deve estar entre 0 e 10.");
        }
        if (evaluation.getUser() == null || evaluation.getUser().getId() == null) {
            throw new BusinessException("A avaliação deve estar associada a um usuário válido.");
        }
        if (evaluation.getGame() == null || evaluation.getGame().getId() == null) {
            throw new BusinessException("A avaliação deve estar associada a um jogo válido.");
        }
    }
}
