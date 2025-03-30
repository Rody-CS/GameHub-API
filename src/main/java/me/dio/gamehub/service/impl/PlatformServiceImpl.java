package me.dio.gamehub.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.dio.gamehub.domain.model.Platform;
import me.dio.gamehub.domain.repository.PlatformRepository;
import me.dio.gamehub.service.PlatformService;
import me.dio.gamehub.service.exception.BusinessException;
import me.dio.gamehub.service.exception.NotFoundException;

@Service
public class PlatformServiceImpl implements PlatformService {

    private final PlatformRepository platformRepository;

    public PlatformServiceImpl(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Platform> findAll() {
        return platformRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Platform> findAll(Pageable pageable) {
        return platformRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Platform findById(Long id) {
        return platformRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plataforma não encontrada"));
    }

    @Transactional
    @Override
    public Platform create(Platform platformToCreate) {
        validatePlatform(platformToCreate);

        if (platformRepository.existsByName(platformToCreate.getName())) {
            throw new BusinessException("Já existe uma plataforma com este nome.");
        }

        return platformRepository.save(platformToCreate);
    }

    @Transactional
    @Override
    public Platform update(Long id, Platform platformToUpdate) {
        Platform existingPlatform = findById(id);
        validatePlatform(platformToUpdate);

        // Atualiza os campos necessários
        existingPlatform.setName(platformToUpdate.getName());
        existingPlatform.setManufacturer(platformToUpdate.getManufacturer());
        existingPlatform.setReleaseYear(platformToUpdate.getReleaseYear());

        return platformRepository.save(existingPlatform);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Platform existingPlatform = findById(id);

        // Verifica se há jogos associados à plataforma
        if (existingPlatform.hasAssociatedGames()) {
            throw new BusinessException("Plataformas com jogos ativos não podem ser excluídas.");
        }

        platformRepository.delete(existingPlatform);
    }

    private void validatePlatform(Platform platform) {
        if (platform == null || platform.getName() == null || platform.getName().isEmpty()) {
            throw new BusinessException("O nome da plataforma não pode ser vazio.");
        }
    }

}
