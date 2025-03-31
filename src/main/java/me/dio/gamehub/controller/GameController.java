package me.dio.gamehub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.gamehub.controller.dto.GameDto;
import me.dio.gamehub.service.GameService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/games")
@Tag(name = "Games Controller", description = "RESTful API for managing games.")
public record GameController(GameService gameService) {

    @GetMapping
    @Operation(summary = "Get all games", description = "Retrieve a list of all registered games")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<GameDto>> findAll() {
        var games = gameService.findAll();
        var gamesDto = games.stream().map(GameDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(gamesDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a game by ID", description = "Retrieve a specific game based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    public ResponseEntity<GameDto> findById(@PathVariable Long id) {
        var game = gameService.findById(id);
        return ResponseEntity.ok(new GameDto(game)); // Converta de Game para GameDto
    }

    @PostMapping
    @Operation(summary = "Create a new game", description = "Create a new game and return the created game's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid game data provided")
    })
    public ResponseEntity<GameDto> create(@RequestBody GameDto gameDto) {
        var game = gameService.create(gameDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(game.getId())
                .toUri();
        return ResponseEntity.created(location).body(new GameDto(game));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a game", description = "Update the data of an existing game based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game updated successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found"),
            @ApiResponse(responseCode = "422", description = "Invalid game data provided")
    })
    public ResponseEntity<GameDto> update(@PathVariable Long id, @RequestBody GameDto gameDto) {
        var game = gameService.update(id, gameDto.toModel());
        return ResponseEntity.ok(new GameDto(game));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a game", description = "Delete an existing game based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
