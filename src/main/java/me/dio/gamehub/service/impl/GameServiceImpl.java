package me.dio.gamehub.service.impl;

import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dio.gamehub.domain.model.Game;
import me.dio.gamehub.domain.repository.GameRepository;
import me.dio.gamehub.service.GameService;
import me.dio.gamehub.service.exception.BusinessException;
import me.dio.gamehub.service.exception.NotFoundException;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Game> findAll() {
        return this.gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Jogo não encontrado com ID: " + id));
    }

    @Transactional
    @Override
    public Game create(Game gameToCreate) {
        validateGame(gameToCreate);

        if (gameRepository.existsByName(gameToCreate.getName())) {
            throw new BusinessException("Já existe um jogo cadastrado com este nome.");
        }

        return gameRepository.save(gameToCreate);
    }

    @Transactional
    @Override
    public Game update(Long id, Game gameToUpdate) {
        validateGame(gameToUpdate);

        Game existingGame = findById(id);

        // Impedir a atualização do nome para um que já exista em outro jogo
        if (!existingGame.getName().equals(gameToUpdate.getName())
                && gameRepository.existsByName(gameToUpdate.getName())) {
            throw new BusinessException("Já existe um jogo cadastrado com este nome.");
        }

        existingGame.setName(gameToUpdate.getName());
        existingGame.setPlatforms(gameToUpdate.getPlatforms());
        existingGame.setGenre(gameToUpdate.getGenre());
        existingGame.setDeveloper(gameToUpdate.getDeveloper());
        existingGame.setReleaseYear(gameToUpdate.getReleaseYear());

        return gameRepository.save(existingGame);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Game existingGame = findById(id);

        gameRepository.delete(existingGame);
    }

    @Override
    public Page<Game> findAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    /**
     * Método para validar os dados do jogo antes de criar ou atualizar.
     */
    private void validateGame(Game game) {
        if (Objects.isNull(game)) {
            throw new BusinessException("O jogo não pode ser nulo.");
        }
        if (game.getName() == null || game.getName().trim().isEmpty()) {
            throw new BusinessException("O nome do jogo não pode estar vazio.");
        }
        if (game.getGenre() == null || game.getGenre().trim().isEmpty()) {
            throw new BusinessException("O gênero do jogo não pode estar vazio.");
        }
        if (game.getDeveloper() == null || game.getDeveloper().trim().isEmpty()) {
            throw new BusinessException("O desenvolvedor do jogo não pode estar vazio.");
        }
        if (Objects.isNull(game.getReleaseYear()) || game.getReleaseYear() <= 0) {
            throw new BusinessException("O ano de lançamento do jogo deve ser válido.");
        }
    }

}
