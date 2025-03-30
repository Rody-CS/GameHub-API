package me.dio.gamehub.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<ID, T> {
    Page<T> findAll(Pageable pageable);
    List<T> findAll();
    T findById(ID id);
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}
