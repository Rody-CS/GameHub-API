package me.dio.gamehub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import me.dio.gamehub.domain.model.User;

public interface UserService extends CrudService<Long, User>{
    
    Page<User> findAll(Pageable pageable);
}
