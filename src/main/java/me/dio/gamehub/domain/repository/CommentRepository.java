package me.dio.gamehub.domain.repository;

import me.dio.gamehub.domain.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByGameId(Long gameId, Pageable pageable);

    Page<Comment> findByUserId(Long userId, Pageable pageable);

    Page<Comment> findByCommentContaining(String keyword, Pageable pageable);
}
