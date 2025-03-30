package me.dio.gamehub.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.gamehub.domain.model.Platform;

@Repository
public interface PlatformRepository extends JpaRepository<Platform,Long>{

    boolean existsByName(String name);
    
}
