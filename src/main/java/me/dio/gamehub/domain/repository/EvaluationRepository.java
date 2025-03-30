package me.dio.gamehub.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.gamehub.domain.model.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long>{

    Page<Evaluation> findByGameId(Long id, Pageable pageable);

    Page<Evaluation> findByUserId(Long id, Pageable pageable);
    
}
