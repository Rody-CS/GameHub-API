package me.dio.gamehub.domain.repository;

import me.dio.gamehub.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Métodos específicos podem ser adicionados aqui, se necessário
    boolean existsByEmail(String email);
    
}
