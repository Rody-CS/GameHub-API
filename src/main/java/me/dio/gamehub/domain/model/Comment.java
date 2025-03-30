package me.dio.gamehub.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "tb_comment")
public class Comment {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "jogo_id", nullable = false)
    private Game game;

    @Column(nullable = false, length = 500)
    private String comment;

    @Column(nullable = false)
    private LocalDate commentDate = LocalDate.now(); // Data do comentário

    @OneToOne
    @JoinColumn(name = "evaluation_id", nullable = true)
    private Evaluation evaluation; // Relacionamento opcional com avaliação
}
