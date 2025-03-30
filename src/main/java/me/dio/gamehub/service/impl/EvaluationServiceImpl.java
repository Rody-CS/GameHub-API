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
        return this.evaluationRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Evaluation findById(Long id) {
        return this.evaluationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Avaliação com ID %d não encontrada.".formatted(id)));
    }

    @Transactional
    @Override
    public Evaluation create(Evaluation evaluationToCreate) {
        validateEvaluation(evaluationToCreate);

        // Valida dependências
        User user = validateUser(evaluationToCreate.getUser().getId());
        Game game = validateGame(evaluationToCreate.getGame().getId());

        // Associa a avaliação ao usuário e ao jogo
        evaluationToCreate.setUser(user);
        evaluationToCreate.setGame(game);

        return this.evaluationRepository.save(evaluationToCreate);
    }

    @Transactional
    @Override
    public Evaluation update(Long id, Evaluation evaluationToUpdate) {
        Evaluation existingEvaluation = findById(id);
        validateEvaluation(evaluationToUpdate);

        // Atualiza atributos modificáveis
        existingEvaluation.setNota(evaluationToUpdate.getNota());
        return this.evaluationRepository.save(existingEvaluation);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Evaluation existingEvaluation = findById(id);
        this.evaluationRepository.delete(existingEvaluation);
    }

    @Transactional(readOnly = true)
    public Page<Evaluation> findByGameId(Long gameId, Pageable pageable) {
        Game game = validateGame(gameId);
        return this.evaluationRepository.findByGameId(game.getId(), pageable);
    }

    @Transactional(readOnly = true)
    public Page<Evaluation> findByUserId(Long userId, Pageable pageable) {
        User user = validateUser(userId);
        return this.evaluationRepository.findByUserId(user.getId(), pageable);
    }

    /**
     * Valida os dados da avaliação.
     */
    private void validateEvaluation(Evaluation evaluation) {
        if (evaluation == null) {
            throw new BusinessException("A avaliação não pode ser nula.");
        }
        if (evaluation.getNota() < 0 || evaluation.getNota() > 10) {
            throw new BusinessException("A nota da avaliação deve estar entre 0 e 10.");
        }
        if (evaluation.getUser() == null || evaluation.getUser().getId() == null) {
            throw new BusinessException("A avaliação deve estar associada a um usuário válido.");
        }
        if (evaluation.getGame() == null || evaluation.getGame().getId() == null) {
            throw new BusinessException("A avaliação deve estar associada a um jogo válido.");
        }
    }

    /**
     * Valida a existência de um usuário.
     */
    private User validateUser(Long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário com ID %d não encontrado.".formatted(userId)));
    }

    /**
     * Valida a existência de um jogo.
     */
    private Game validateGame(Long gameId) {
        return this.gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Jogo com ID %d não encontrado.".formatted(gameId)));
    }

    @Override
    public List<Evaluation> findAll() {
        return this.evaluationRepository.findAll();
    }
}
