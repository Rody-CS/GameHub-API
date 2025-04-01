package me.dio.gamehub.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "tb_games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String genre;

    @NotBlank
    @Column(nullable = false)
    private String developer;

    @Column(nullable = false)
    private int releaseYear;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_game_platform", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private List<Platform> platforms= new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Library> libraries; // Agora um jogo pode estar em várias bibliotecas de usuários

}
