package me.dio.gamehub.domain.repository;

import me.dio.gamehub.domain.model.Evaluation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Page<Evaluation> findByGameId(Long gameId, Pageable pageable);

    Page<Evaluation> findByUserId(Long userId, Pageable pageable);
}
