package me.dio.gamehub.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.gamehub.domain.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game,Long>{

    boolean existsByName(String name);
    
}
