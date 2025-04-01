package me.dio.gamehub.domain.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import me.dio.gamehub.domain.enums.LibraryStatus;

@Data
@Entity(name = "tb_library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private LocalDate addedDate = LocalDate.now(); // Data em que o jogo foi adicionado à biblioteca

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LibraryStatus status = LibraryStatus.PENDING; // Status do jogo na biblioteca
}
