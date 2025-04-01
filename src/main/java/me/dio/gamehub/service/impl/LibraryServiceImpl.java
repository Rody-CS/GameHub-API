package me.dio.gamehub.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dio.gamehub.domain.model.Library;
import me.dio.gamehub.domain.model.User;
import me.dio.gamehub.domain.repository.LibraryRepository;
import me.dio.gamehub.domain.repository.UserRepository;
import me.dio.gamehub.service.LibraryService;
import me.dio.gamehub.service.exception.BusinessException;
import me.dio.gamehub.service.exception.NotFoundException;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;

    public LibraryServiceImpl(LibraryRepository libraryRepository, UserRepository userRepository) {
        this.libraryRepository = libraryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Library create(Library library) {
        validateLibrary(library);

        Long userId = library.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário com ID %d não encontrado.".formatted(userId)));

        library.setUser(user);
        return libraryRepository.save(library);
    }

    @Transactional
    @Override
    public Library update(Long id, Library libraryToUpdate) {
        Library existingLibrary = findById(id);
        validateLibrary(libraryToUpdate);
        existingLibrary.setName(libraryToUpdate.getName());
        return libraryRepository.save(existingLibrary);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Library existingLibrary = findById(id);
        libraryRepository.delete(existingLibrary);
    }

    @Transactional(readOnly = true)
    @Override
    public Library findById(Long id) {
        return libraryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Biblioteca com ID %d não encontrada.".formatted(id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Library> findAll() {
        return libraryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Library> findAll(Pageable pageable) {
        return libraryRepository.findAll(pageable);
    }

    private void validateLibrary(Library library) {
        if (library == null) {
            throw new BusinessException("A biblioteca não pode ser nula.");
        }
        if (library.getName() == null || library.getName().trim().isEmpty()) {
            throw new BusinessException("O nome da biblioteca é obrigatório.");
        }
        if (library.getUser() == null || library.getUser().getId() == null) {
            throw new BusinessException("A biblioteca deve estar associada a um usuário válido.");
        }
    }
}
