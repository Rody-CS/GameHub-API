package me.dio.gamehub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import me.dio.gamehub.domain.model.Game;

public interface GameService extends CrudService<Long, Game>{
    
    Page<Game> findAll(Pageable pageable);
}
