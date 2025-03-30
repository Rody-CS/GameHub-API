package me.dio.gamehub.domain.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity(name = "tb_platform")
public class Platform {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String manufacturer;

    @Min(1970)
    private int releaseYear;

    @ManyToMany(mappedBy = "platforms")
    private List<Game> games;

    public boolean hasAssociatedGames() {
        return games != null && !games.isEmpty();
    }
}
