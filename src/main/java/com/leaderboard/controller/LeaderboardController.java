package com.leaderboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@Tag(name = "Leaderboard", description = "API para gerenciamento do leaderboard")
public class LeaderboardController {
    
    @Operation(summary = "Teste da API", description = "Endpoint de teste para verificar se a API está funcionando")
    @GetMapping("/test")
    public String test() {
        return "Backend is working! CORS configured successfully.";
    }
    
    @Operation(summary = "Status da API", description = "Endpoint de health check da API")
    @GetMapping("/health")
    public String health() {
        return "{\"status\": \"UP\", \"message\": \"Leaderboard API is running\", \"cors\": \"enabled\"}";
    }
    
    @Operation(summary = "Página inicial", description = "Endpoint da página inicial da API")
    @GetMapping("/")
    public String home() {
        return "Leaderboard API - CORS enabled for Angular frontend";
    }
} 