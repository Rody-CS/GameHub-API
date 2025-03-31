package me.dio.gamehub.controller;

import me.dio.gamehub.controller.dto.CommentDto;
import me.dio.gamehub.service.CommentService;
import me.dio.gamehub.service.exception.CommentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/comments")
@Tag(name = "Comments Controller", description = "RESTful API for managing comments.")
public record CommentController(CommentService commentService) {

    @GetMapping
    @Operation(summary = "Get all comments", description = "Retrieve a list of all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<CommentDto>> findAll() {
        var comments = commentService.findAll();
        var commentsDto = comments.stream().map(CommentDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(commentsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a comment by ID", description = "Retrieve a specific comment based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<CommentDto> findById(@PathVariable Long id) {
        var comment = commentService.findById(id);
        return comment != null
                ? ResponseEntity.ok(new CommentDto(comment))
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a new comment", description = "Create a new comment and return the created comment's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid comment data provided")
    })
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto) {
        var comment = commentService.create(commentDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comment.getId())
                .toUri();
        return ResponseEntity.created(location).body(new CommentDto(comment));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a comment", description = "Update the data of an existing comment based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "422", description = "Invalid comment data provided")
    })
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        var comment = commentService.update(id, commentDto.toModel());
        return comment != null
                ? ResponseEntity.ok(new CommentDto(comment))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a comment", description = "Delete an existing comment based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            commentService.delete(id); // Chama o serviço de exclusão
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a exclusão for bem-sucedida
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o comentário não for encontrado
        }
    }
}
