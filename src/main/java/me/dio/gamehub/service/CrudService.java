package me.dio.gamehub.service;

import java.util.List;

public interface CrudService<ID, T> {
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
    T findById(ID id);
    List<T> findAll();
}
