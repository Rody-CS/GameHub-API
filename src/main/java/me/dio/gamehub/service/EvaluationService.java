package me.dio.gamehub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import me.dio.gamehub.domain.model.Evaluation;

public interface EvaluationService extends CrudService<Long, Evaluation>{
    
    Page<Evaluation> findAll(Pageable pageable);
}
