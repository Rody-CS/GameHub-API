package me.dio.gamehub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.gamehub.controller.dto.PlatformDto;
import me.dio.gamehub.service.PlatformService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/platforms")
@Tag(name = "Platforms Controller", description = "RESTful API for managing platforms.")
public record PlatformController(PlatformService platformService) {

    @GetMapping
    @Operation(summary = "Get all platforms", description = "Retrieve a list of all platforms.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<PlatformDto>> findAll() {
        var platforms = platformService.findAll();
        var platformsDto = platforms.stream().map(PlatformDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(platformsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a platform by ID", description = "Retrieve a specific platform based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Platform not found")
    })
    public ResponseEntity<PlatformDto> findById(@PathVariable Long id) {
        var platform = platformService.findById(id);
        return ResponseEntity.ok(new PlatformDto(platform));
    }

    @PostMapping
    @Operation(summary = "Create a new platform", description = "Create a new platform and return the created platform's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Platform created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid platform data provided")
    })
    public ResponseEntity<PlatformDto> create(@RequestBody PlatformDto platformDto) {
        var platform = platformService.create(platformDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(platform.getId())
                .toUri();
        return ResponseEntity.created(location).body(new PlatformDto(platform));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a platform", description = "Update the data of an existing platform based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Platform updated successfully"),
            @ApiResponse(responseCode = "404", description = "Platform not found"),
            @ApiResponse(responseCode = "422", description = "Invalid platform data provided")
    })
    public ResponseEntity<PlatformDto> update(@PathVariable Long id, @RequestBody PlatformDto platformDto) {
        var platform = platformService.update(id, platformDto.toModel());
        return ResponseEntity.ok(new PlatformDto(platform));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a platform", description = "Delete an existing platform based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Platform deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Platform not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        platformService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
