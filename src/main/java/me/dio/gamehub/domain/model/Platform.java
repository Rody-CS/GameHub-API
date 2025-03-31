package me.dio.gamehub.domain.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "tb_platforms") // Nome ajustado para plural
public class Platform {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String manufacturer;

    @Min(1970)
    @Column(nullable = false)
    private int releaseYear;

    @ManyToMany(mappedBy = "platforms", fetch = FetchType.LAZY)
    private List<Game> games;

    public boolean hasAssociatedGames() {
        return games != null && !games.isEmpty();
    }
}
