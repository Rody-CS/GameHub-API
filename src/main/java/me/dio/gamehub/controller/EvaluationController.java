package me.dio.gamehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.dio.gamehub.controller.dto.EvaluationDto;
import me.dio.gamehub.service.EvaluationService;
import me.dio.gamehub.domain.model.Evaluation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evaluations")
public record EvaluationController(EvaluationService evaluationService) {

    @GetMapping
    @Operation(summary = "Get all evaluations", description = "Retrieve all evaluations from the database")
    public ResponseEntity<List<EvaluationDto>> findAll() {
        List<EvaluationDto> evaluations = evaluationService.findAll()
                .stream()
                .map(EvaluationDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an evaluation by ID", description = "Retrieve a specific evaluation using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluation found"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found")
    })
    public ResponseEntity<EvaluationDto> findById(@PathVariable Long id) {
        Evaluation evaluation = evaluationService.findById(id);
        return ResponseEntity.ok(new EvaluationDto(evaluation));
    }

    @PostMapping
    @Operation(summary = "Create a new evaluation", description = "Add a new evaluation to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evaluation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<EvaluationDto> create(@RequestBody EvaluationDto evaluationDto) {
        Evaluation createdEvaluation = evaluationService.create(evaluationDto.toModel());
        return ResponseEntity.status(201).body(new EvaluationDto(createdEvaluation));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an evaluation", description = "Modify an existing evaluation using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluation updated successfully"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found")
    })
    public ResponseEntity<EvaluationDto> update(@PathVariable Long id, @RequestBody EvaluationDto evaluationDto) {
        Evaluation updatedEvaluation = evaluationService.update(id, evaluationDto.toModel());
        return ResponseEntity.ok(new EvaluationDto(updatedEvaluation));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an evaluation", description = "Delete an existing evaluation based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evaluation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        evaluationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
