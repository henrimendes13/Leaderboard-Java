package com.leaderboard.controller;

import com.leaderboard.Entities.enums.TipoWorkout;
import com.leaderboard.dto.WorkoutDTO;
import com.leaderboard.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@Tag(name = "Workouts", description = "API para gerenciamento de workouts")
public class WorkoutController {
    
    @Autowired
    private WorkoutService workoutService;
    
    @Operation(summary = "Criar novo workout", description = "Cria um novo workout no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Workout criado com sucesso",
                    content = @Content(schema = @Schema(implementation = WorkoutDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou nome já existe")
    })
    @PostMapping
    public ResponseEntity<?> criarWorkout(@RequestBody WorkoutDTO workoutDTO) {
        try {
            WorkoutDTO novoWorkout = workoutService.criarWorkout(workoutDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoWorkout);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }



    
    @Operation(summary = "Buscar workout por ID", description = "Retorna um workout específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Workout encontrado",
                    content = @Content(schema = @Schema(implementation = WorkoutDTO.class))),
        @ApiResponse(responseCode = "404", description = "Workout não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> buscarPorId(@Parameter(description = "ID do workout") @PathVariable Long id) {
        Optional<WorkoutDTO> workout = workoutService.buscarPorId(id);
        return workout.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    
    @Operation(summary = "Buscar workout por nome", description = "Retorna um workout específico pelo nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Workout encontrado",
                    content = @Content(schema = @Schema(implementation = WorkoutDTO.class))),
        @ApiResponse(responseCode = "404", description = "Workout não encontrado")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<WorkoutDTO> buscarPorNome(@Parameter(description = "Nome do workout") @PathVariable String nome) {
        Optional<WorkoutDTO> workout = workoutService.buscarPorNome(nome);
        return workout.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    
    @Operation(summary = "Listar todos os workouts ativos", description = "Retorna todos os workouts ativos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de workouts retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = WorkoutDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> listarTodosAtivos() {
        List<WorkoutDTO> workouts = workoutService.listarTodosAtivos();
        return ResponseEntity.ok(workouts);
    }



    
    @Operation(summary = "Listar todos os workouts", description = "Retorna todos os workouts (incluindo inativos)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de workouts retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = WorkoutDTO.class)))
    })
    @GetMapping("/todos")
    public ResponseEntity<List<WorkoutDTO>> listarTodos() {
        List<WorkoutDTO> workouts = workoutService.listarTodos();
        return ResponseEntity.ok(workouts);
    }



    
    @Operation(summary = "Listar workouts por tipo", description = "Retorna workouts filtrados por tipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de workouts retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = WorkoutDTO.class)))
    })
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<WorkoutDTO>> listarPorTipo(@Parameter(description = "Tipo do workout") @PathVariable TipoWorkout tipo) {
        List<WorkoutDTO> workouts = workoutService.listarPorTipo(tipo);
        return ResponseEntity.ok(workouts);
    }



    
    @Operation(summary = "Buscar workouts por nome", description = "Retorna workouts que contêm o nome especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de workouts retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = WorkoutDTO.class)))
    })
    @GetMapping("/busca")
    public ResponseEntity<List<WorkoutDTO>> buscarPorNomeContendo(@Parameter(description = "Nome para busca") @RequestParam String nome) {
        List<WorkoutDTO> workouts = workoutService.buscarPorNomeContendo(nome);
        return ResponseEntity.ok(workouts);
    }



    
    @Operation(summary = "Atualizar workout", description = "Atualiza um workout existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Workout atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = WorkoutDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou nome já existe"),
        @ApiResponse(responseCode = "404", description = "Workout não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarWorkout(@Parameter(description = "ID do workout") @PathVariable Long id, 
                                            @RequestBody WorkoutDTO workoutDTO) {
        try {
            Optional<WorkoutDTO> workoutAtualizado = workoutService.atualizarWorkout(id, workoutDTO);
            return workoutAtualizado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }



    
    @Operation(summary = "Deletar workout", description = "Desativa um workout (soft delete)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Workout deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Workout não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarWorkout(@Parameter(description = "ID do workout") @PathVariable Long id) {
        boolean deletado = workoutService.deletarWorkout(id);
        if (deletado) {
            return ResponseEntity.ok().body("{\"message\": \"Workout deletado com sucesso\"}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    
    @Operation(summary = "Ativar workout", description = "Reativa um workout previamente desativado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Workout ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Workout não encontrado")
    })
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<?> ativarWorkout(@Parameter(description = "ID do workout") @PathVariable Long id) {
        boolean ativado = workoutService.ativarWorkout(id);
        if (ativado) {
            return ResponseEntity.ok().body("{\"message\": \"Workout ativado com sucesso\"}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    
    @Operation(summary = "Contar workouts ativos", description = "Retorna o número de workouts ativos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    })
    @GetMapping("/contar/ativos")
    public ResponseEntity<Long> contarAtivos() {
        long count = workoutService.contarAtivos();
        return ResponseEntity.ok(count);
    }



    
    @Operation(summary = "Contar workouts por tipo", description = "Retorna o número de workouts de um tipo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    })
    @GetMapping("/contar/tipo/{tipo}")
    public ResponseEntity<Long> contarPorTipo(@Parameter(description = "Tipo do workout") @PathVariable TipoWorkout tipo) {
        long count = workoutService.contarPorTipo(tipo);
        return ResponseEntity.ok(count);
    }
} 