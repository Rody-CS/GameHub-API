package me.dio.gamehub.service.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dio.gamehub.domain.model.User;
import me.dio.gamehub.domain.repository.UserRepository;
import me.dio.gamehub.service.UserService;
import me.dio.gamehub.service.exception.BusinessException;
import me.dio.gamehub.service.exception.NotFoundException;

@Service
public class UserServiceImpl implements UserService {

    /**
     * ID de usuário utilizado no Game Hub 2025.
     * Por isso, vamos criar algumas regras para mantê-lo íntegro.
     */
    private static final Long UNCHANGEABLE_USER_ID = 1L;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    @Transactional
    @Override
    public User create(User userToCreate) {
        validateUser(userToCreate);

        if (userRepository.existsByEmail(userToCreate.getEmail())) {
            throw new BusinessException("Este e-mail já está em uso.");
        }

        return this.userRepository.save(userToCreate);
    }

    @Transactional
    @Override
    public User update(Long id, User userToUpdate) {
        validateUnchangeableId(id);
        validateUser(userToUpdate);

        User existingUser = findById(id);
        existingUser.setName(userToUpdate.getName());
        existingUser.setEmail(userToUpdate.getEmail());
        existingUser.setPassword(userToUpdate.getPassword());

        return this.userRepository.save(existingUser);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        validateUnchangeableId(id);

        User existingUser = findById(id);
        // Exemplo de validação adicional (dependências):
        if (existingUser.hasActiveGames()) {
            throw new BusinessException("Usuário com jogos ativos não pode ser excluído.");
        }

        this.userRepository.delete(existingUser);
    }

    /**
     * Método para validar dados do usuário.
     */
    private void validateUser(User user) {
        if (user == null) {
            throw new BusinessException("Usuário não pode ser nulo.");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new BusinessException("O nome do usuário não pode ser vazio.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new BusinessException("O e-mail do usuário não pode ser vazio.");
        }
        if (!Pattern.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,4}$", user.getEmail())) {
            throw new BusinessException("Formato de e-mail inválido.");
        }
    }

    /**
     * Método para validar IDs imutáveis.
     */
    private void validateUnchangeableId(Long id) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("Este usuário não pode ser modificado ou excluído.");
        }
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }
}
