package me.dio.gamehub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import me.dio.gamehub.domain.model.Library;

public interface LibraryService extends CrudService<Long, Library>{
    
    Page<Library> findAll(Pageable pageable);
}
