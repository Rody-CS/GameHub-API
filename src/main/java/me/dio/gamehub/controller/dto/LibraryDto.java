package me.dio.gamehub.controller.dto;

import me.dio.gamehub.domain.model.Library;
import me.dio.gamehub.domain.enums.LibraryStatus;
import java.time.LocalDate;

public record LibraryDto(
        Long id,
        String name,
        UserDto user,  // DTO para o usuário (assumindo que a classe UserDto existe)
        GameDto game,  // DTO para o jogo (assumindo que a classe GameDto existe)
        LocalDate addedDate,
        LibraryStatus status
) {

    /**
     * Construtor baseado na entidade Library.
     */
    public LibraryDto(Library model) {
        this(
            model.getId(),
            model.getName(),
            new UserDto(model.getUser()), // Converte o User para UserDto
            new GameDto(model.getGame()), // Converte o Game para GameDto
            model.getAddedDate(),
            model.getStatus()
        );
    }

    /**
     * Conversão do DTO para a entidade Library.
     */
    public Library toModel() {
        Library model = new Library();
        model.setId(this.id);
        model.setName(this.name);
        model.setUser(this.user != null ? this.user.toModel() : null); // Converte UserDto para User
        model.setGame(this.game != null ? this.game.toModel() : null); // Converte GameDto para Game
        model.setAddedDate(this.addedDate);
        model.setStatus(this.status);
        return model;
    }
}
