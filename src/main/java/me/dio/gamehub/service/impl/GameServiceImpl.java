package me.dio.gamehub.service.impl;

import java.util.List;

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
public class GameServiceImpl implements GameService{

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
        return this.gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Jogo não encontrado"));
    }

    @Transactional
    @Override
    public Game create(Game gameToCreate) {
        // Validação para verificar se o nome do jogo já existe
        if (gameRepository.existsByName(gameToCreate.getName())) {
            throw new BusinessException("Já existe um jogo com este nome.");
        }

        // Salvar o novo jogo no repositório
        return gameRepository.save(gameToCreate);
    }

    @Transactional
    @Override
    public Game update(Long id, Game gameToUpdate) {
        // Busca o jogo existente para garantir que ele existe
        Game existingGame = findById(id);

        // Atualiza os campos necessários
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
        // Verifica se o jogo existe antes de deletar
        Game existingGame = findById(id);

        gameRepository.delete(existingGame);
    }

    @Override
    public Page<Game> findAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }
    
}
