package me.dio.gamehub.domain.repository;

import me.dio.gamehub.domain.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
