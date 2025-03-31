package me.dio.gamehub.domain.repository;

import me.dio.gamehub.domain.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

    boolean existsByName(String name);
}
