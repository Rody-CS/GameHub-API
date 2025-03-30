package me.dio.gamehub.domain.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_platform")
public class Platform {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String manufacturer;

    private int releaseYear;

    private List<Game> games;

    public boolean hasAssociatedGames() {
        return games != null && !games.isEmpty();
    }
}
