package me.dio.gamehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.gamehub.controller.dto.GameDto;
import me.dio.gamehub.domain.model.Game;
import me.dio.gamehub.service.GameService;
import me.dio.gamehub.service.exception.GameNotFoundException;
import me.dio.gamehub.service.exception.NotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/games")
@Tag(name = "Games Controller", description = "RESTful API for managing games.")
public record GameController(GameService gameService) {

        @GetMapping
        @Operation(summary = "Get all games", description = "Retrieve a list of all registered games")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Operation successful"),
                        @ApiResponse(responseCode = "204", description = "No games found")
        })
        public ResponseEntity<List<GameDto>> findAll() {
                List<GameDto> games = gameService.findAll() // Retorna List<Game>
                                .stream()
                                .map(GameDto::new) // Converte Game para GameDto
                                .toList(); // Retorna List<GameDto>

                return games.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(games);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get a game by ID", description = "Retrieve a specific game based on its ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Operation successful"),
                        @ApiResponse(responseCode = "404", description = "Game not found")
        })
        public ResponseEntity<GameDto> findById(@PathVariable Long id) {
                try {
                        Game game = gameService.findById(id);
                        return ResponseEntity.ok(new GameDto(game));
                } catch (NotFoundException e) {
                        return ResponseEntity.notFound().build();
                }
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
                var updatedGame = gameService.update(id, gameDto.toModel());
                return ResponseEntity.ok(new GameDto(updatedGame));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete a game", description = "Delete an existing game based on its ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Game not found")
        })
        public ResponseEntity<Void> delete(@PathVariable Long id) {
                try {
                        gameService.delete(id);
                        return ResponseEntity.noContent().build();
                } catch (GameNotFoundException e) {
                        return ResponseEntity.notFound().build();
                }
        }
}
