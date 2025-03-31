package me.dio.gamehub.domain.repository;

import me.dio.gamehub.domain.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

    boolean existsByName(String name);
}
