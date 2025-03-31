package me.dio.gamehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.gamehub.controller.dto.LibraryDto;
import me.dio.gamehub.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/libraries")
@Tag(name = "Library Controller", description = "RESTful API for managing libraries of games.")
public record LibraryController(LibraryService libraryService) {

    @GetMapping
    @Operation(summary = "Get all libraries", description = "Retrieve a list of all libraries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<LibraryDto>> findAll() {
        var libraries = libraryService.findAll();
        var librariesDto = libraries.stream().map(LibraryDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(librariesDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a library by ID", description = "Retrieve a specific library based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Library not found")
    })
    public ResponseEntity<LibraryDto> findById(@PathVariable Long id) {
        var library = libraryService.findById(id);
        return ResponseEntity.ok(new LibraryDto(library));
    }

    @PostMapping
    @Operation(summary = "Create a new library", description = "Create a new library and return the created library's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Library created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid library data provided")
    })
    public ResponseEntity<LibraryDto> create(@RequestBody LibraryDto libraryDto) {
        var library = libraryService.create(libraryDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(library.getId())
                .toUri();
        return ResponseEntity.created(location).body(new LibraryDto(library));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a library", description = "Update the data of an existing library based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library updated successfully"),
            @ApiResponse(responseCode = "404", description = "Library not found"),
            @ApiResponse(responseCode = "422", description = "Invalid library data provided")
    })
    public ResponseEntity<LibraryDto> update(@PathVariable Long id, @RequestBody LibraryDto libraryDto) {
        var library = libraryService.update(id, libraryDto.toModel());
        return ResponseEntity.ok(new LibraryDto(library));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a library", description = "Delete an existing library based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Library deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Library not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        libraryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
