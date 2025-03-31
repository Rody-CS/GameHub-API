package me.dio.gamehub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import me.dio.gamehub.domain.model.Platform;

public interface PlatformService extends CrudService<Long, Platform>{

    Page<Platform> findAll(Pageable pageable);
}
