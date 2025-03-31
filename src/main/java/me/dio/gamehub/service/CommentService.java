package me.dio.gamehub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import me.dio.gamehub.domain.model.Comment;

public interface CommentService extends CrudService<Long, Comment>{
    
    Page<Comment> findAll(Pageable pageable);
}
