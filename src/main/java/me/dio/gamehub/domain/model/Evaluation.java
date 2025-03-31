package me.dio.gamehub.domain.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_evaluations") // Nome ajustado para plural
public class Evaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Evita carregamento automático desnecessário
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) // Evita carregamento automático desnecessário
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Min(0)
    @Max(10)
    @Column(nullable = false)
    private int nota; // Nota de 0 a 10

    @Column(nullable = false, updatable = false)
    private LocalDate evaluationDate = LocalDate.now(); // Imutável após criação
}
