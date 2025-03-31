package me.dio.gamehub.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dio.gamehub.domain.model.Comment;
import me.dio.gamehub.domain.model.Game;
import me.dio.gamehub.domain.model.User;
import me.dio.gamehub.domain.repository.CommentRepository;
import me.dio.gamehub.domain.repository.GameRepository;
import me.dio.gamehub.domain.repository.UserRepository;
import me.dio.gamehub.service.CommentService;
import me.dio.gamehub.service.exception.BusinessException;
import me.dio.gamehub.service.exception.NotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário com ID " + id + " não encontrado."));
    }

    @Transactional
    @Override
    public Comment create(Comment commentToCreate) {
        validateComment(commentToCreate);

        // Validação de dependências
        User user = validateUser(commentToCreate.getUser().getId());
        Game game = validateGame(commentToCreate.getGame().getId());

        // Associação segura
        commentToCreate.setUser(user);
        commentToCreate.setGame(game);

        return commentRepository.save(commentToCreate);
    }

    @Transactional
    @Override
    public Comment update(Long id, Comment commentToUpdate) {
        Comment existingComment = findById(id);
        validateComment(commentToUpdate);

        // Atualiza apenas os atributos permitidos
        existingComment.setComment(commentToUpdate.getComment());
        existingComment.setCommentDate(commentToUpdate.getCommentDate());

        return commentRepository.save(existingComment);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Comment existingComment = findById(id);
        commentRepository.delete(existingComment);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findByGameId(Long gameId, Pageable pageable) {
        validateGame(gameId);
        return commentRepository.findByGameId(gameId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findByUserId(Long userId, Pageable pageable) {
        validateUser(userId);
        return commentRepository.findByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findByKeyword(String keyword, Pageable pageable) {
        return commentRepository.findByCommentContaining(keyword, pageable);
    }

    /**
     * Valida o conteúdo do comentário.
     */
    private void validateComment(Comment comment) {
        if (Objects.isNull(comment)) {
            throw new BusinessException("O comentário não pode ser nulo.");
        }
        if (Objects.isNull(comment.getComment()) || comment.getComment().trim().isEmpty()) {
            throw new BusinessException("O conteúdo do comentário não pode estar vazio.");
        }
        if (Objects.isNull(comment.getUser()) || Objects.isNull(comment.getUser().getId())) {
            throw new BusinessException("O comentário deve estar associado a um usuário válido.");
        }
        if (Objects.isNull(comment.getGame()) || Objects.isNull(comment.getGame().getId())) {
            throw new BusinessException("O comentário deve estar associado a um jogo válido.");
        }
    }

    /**
     * Valida a existência de um usuário.
     */
    private User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + userId + " não encontrado."));
    }

    /**
     * Valida a existência de um jogo.
     */
    private Game validateGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Jogo com ID " + gameId + " não encontrado."));
    }
}
