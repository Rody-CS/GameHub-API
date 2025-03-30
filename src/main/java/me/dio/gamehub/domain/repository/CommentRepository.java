package me.dio.gamehub.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.gamehub.domain.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

    Page<Comment> findByGameId(Long id, Pageable pageable);

    Page<Comment> findByUserId(Long id, Pageable pageable);

    Page<Comment> findByCommentContaining(String keyword, Pageable pageable);
    
}
