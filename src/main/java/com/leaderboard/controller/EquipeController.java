package com.leaderboard.controller;

import com.leaderboard.dto.EquipeDTO;
import com.leaderboard.service.EquipeService;
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
@RequestMapping("/api/equipes")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@Tag(name = "Equipes", description = "API para gerenciamento de equipes")
public class EquipeController {
    
    @Autowired
    private EquipeService equipeService;
    
    @Operation(summary = "Criar nova equipe", description = "Cria uma nova equipe no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Equipe criada com sucesso",
                    content = @Content(schema = @Schema(implementation = EquipeDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou nome já existe")
    })
    @PostMapping
    public ResponseEntity<?> criarEquipe(@RequestBody EquipeDTO equipeDTO) {
        try {
            EquipeDTO novaEquipe = equipeService.criarEquipe(equipeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaEquipe);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @Operation(summary = "Buscar equipe por ID", description = "Retorna uma equipe específica pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipe encontrada",
                    content = @Content(schema = @Schema(implementation = EquipeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Equipe não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EquipeDTO> buscarPorId(@Parameter(description = "ID da equipe") @PathVariable Long id) {
        Optional<EquipeDTO> equipe = equipeService.buscarPorId(id);
        return equipe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Buscar equipe por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<EquipeDTO> buscarPorNome(@PathVariable String nome) {
        Optional<EquipeDTO> equipe = equipeService.buscarPorNome(nome);
        return equipe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Listar todas as equipes", description = "Retorna todas as equipes cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipes retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = EquipeDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<EquipeDTO>> listarTodas() {
        List<EquipeDTO> equipes = equipeService.listarTodas();
        return ResponseEntity.ok(equipes);
    }
    
    // Atualizar equipe
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEquipe(@PathVariable Long id, @RequestBody EquipeDTO equipeDTO) {
        try {
            Optional<EquipeDTO> equipeAtualizada = equipeService.atualizarEquipe(id, equipeDTO);
            return equipeAtualizada.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    // Deletar equipe
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEquipe(@PathVariable Long id) {
        boolean deletado = equipeService.deletarEquipe(id);
        if (deletado) {
            return ResponseEntity.ok().body("{\"message\": \"Equipe deletada com sucesso\"}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}