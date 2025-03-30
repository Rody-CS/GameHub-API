package me.dio.gamehub.domain.model;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name = "tb_game")
public class Game {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String genre;

    private String developer;

    private int releaseYear;

    @ManyToAny
    private List<Platform> platforms;

    @OneToMany(mappedBy = "game_id", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations; // Lista de avaliações

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL)
    private List<Comment> comentarios; // Lista de comentários
}
