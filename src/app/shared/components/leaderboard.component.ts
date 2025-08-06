import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { LeaderboardService } from '../services/leaderboard.service';
import { WorkoutResultService, WorkoutResult, LeaderboardEntry } from '../services/workout-result.service';
import { EquipesService, Equipe } from '../../features/equipes/services/equipes.service';
import { WorkoutsService, Workout } from '../../features/workouts/services/workouts.service';
import { Leaderboard } from '../services/leaderboard.model';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {
  
  leaderboardData: Leaderboard[] = [];
  backendStatus: string = 'Testing connection...';
  healthStatus: string = '';
  
  // Dados do leaderboard
  equipes: Equipe[] = [];
  workouts: Workout[] = [];
  workoutResults: WorkoutResult[] = [];
  leaderboardEntries: LeaderboardEntry[] = [];
  
  // Estados de carregamento
  loading = false;
  error: string | null = null;
  
  // Dados para edição
  editingResult: { equipeId: number; workoutId: number; result: string } | null = null;
  private tempResults: { [key: string]: string } = {};

  constructor(
    private leaderboardService: LeaderboardService,
    private workoutResultService: WorkoutResultService,
    private equipesService: EquipesService,
    private workoutsService: WorkoutsService
  ) { }

  ngOnInit(): void {
    this.testBackendConnection();
    this.checkHealth();
    this.loadLeaderboardData();
  }

  testBackendConnection(): void {
    this.leaderboardService.testConnection().subscribe({
      next: (response: any) => {
        this.backendStatus = response;
        console.log('Backend connection successful:', response);
      },
      error: (error: any) => {
        this.backendStatus = 'Error connecting to backend: ' + error.message;
        console.error('Backend connection failed:', error);
      }
    });
  }

  checkHealth(): void {
    this.leaderboardService.getHealth().subscribe({
      next: (response: any) => {
        this.healthStatus = response;
        console.log('Health check successful:', response);
      },
      error: (error: any) => {
        this.healthStatus = 'Health check failed: ' + error.message;
        console.error('Health check failed:', error);
      }
    });
  }

  loadLeaderboardData(): void {
    this.loading = true;
    this.error = null;

    // Carregar equipes, workouts e resultados usando forkJoin
    forkJoin({
      equipes: this.equipesService.getEquipes(),
      workouts: this.workoutsService.getWorkouts(),
      results: this.workoutResultService.getWorkoutResults()
    }).subscribe({
      next: (data) => {
        this.equipes = data.equipes || [];
        this.workouts = data.workouts || [];
        this.workoutResults = data.results || [];
        
        this.calculateLeaderboard();
        this.loading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar dados:', error);
        this.error = 'Erro ao carregar dados. Tente novamente.';
        this.loading = false;
      }
    });
  }

  calculateLeaderboard(): void {
    this.leaderboardEntries = [];

    // Para cada equipe, calcular resultados
    this.equipes.forEach(equipe => {
      const entry: LeaderboardEntry = {
        equipeId: equipe.id!,
        equipeNome: equipe.nome,
        workoutResults: {},
        totalPoints: 0,
        positions: {}
      };

      // Buscar resultados da equipe
      this.workoutResults
        .filter(result => result.equipeId === equipe.id)
        .forEach(result => {
          entry.workoutResults[result.workoutId] = result;
        });

      this.leaderboardEntries.push(entry);
    });

    // Calcular posições e pontos para cada workout
    this.workouts.forEach(workout => {
      this.calculateWorkoutPositions(workout.id!);
    });

    // Calcular pontos totais
    this.leaderboardEntries.forEach(entry => {
      entry.totalPoints = Object.values(entry.positions).reduce((sum, pos) => sum + pos, 0);
    });

    // Ordenar por pontos totais (menor = melhor)
    this.leaderboardEntries.sort((a, b) => a.totalPoints - b.totalPoints);
  }

  calculateWorkoutPositions(workoutId: number): void {
    const workout = this.workouts.find(w => w.id === workoutId);
    if (!workout) return;

    // Coletar todos os resultados para este workout
    const results = this.leaderboardEntries
      .map(entry => ({
        equipeId: entry.equipeId,
        result: entry.workoutResults[workoutId]?.result || ''
      }))
      .filter(item => item.result !== '');

    // Ordenar resultados baseado no tipo de workout
    if (workout.tipo === 'TEMPO') {
      // Para tempo, menor é melhor
      results.sort((a, b) => this.compareTime(a.result, b.result));
    } else {
      // Para peso e repetições, maior é melhor
      results.sort((a, b) => parseFloat(b.result) - parseFloat(a.result));
    }

    // Atribuir posições
    results.forEach((item, index) => {
      const position = index + 1;
      const entry = this.leaderboardEntries.find(e => e.equipeId === item.equipeId);
      if (entry) {
        entry.positions[workoutId] = position;
      }
    });
  }

  compareTime(time1: string, time2: string): number {
    const parseTime = (time: string) => {
      const parts = time.split(':');
      return parseInt(parts[0]) * 60 + parseInt(parts[1]);
    };
    return parseTime(time1) - parseTime(time2);
  }

  getResultForEquipeAndWorkout(equipeId: number, workoutId: number): string {
    const entry = this.leaderboardEntries.find(e => e.equipeId === equipeId);
    return entry?.workoutResults[workoutId]?.result || '';
  }

  getPositionForEquipeAndWorkout(equipeId: number, workoutId: number): number {
    const entry = this.leaderboardEntries.find(e => e.equipeId === equipeId);
    return entry?.positions[workoutId] || 0;
  }

  startEditing(equipeId: number, workoutId: number): void {
    const currentResult = this.getResultForEquipeAndWorkout(equipeId, workoutId);
    this.editingResult = { equipeId, workoutId, result: currentResult };
  }

  saveResult(): void {
    if (!this.editingResult) return;

    const { equipeId, workoutId, result } = this.editingResult;
    
    // Buscar resultado existente ou criar novo
    const existingResult = this.workoutResults.find(
      r => r.equipeId === equipeId && r.workoutId === workoutId
    );

    const workoutResult: WorkoutResult = {
      equipeId,
      workoutId,
      result
    };

    if (existingResult) {
      // Atualizar resultado existente
      this.workoutResultService.updateWorkoutResult(existingResult.id!, workoutResult)
        .subscribe({
          next: (updatedResult) => {
            const index = this.workoutResults.findIndex(r => r.id === existingResult.id);
            if (index !== -1) {
              this.workoutResults[index] = updatedResult;
            }
            this.calculateLeaderboard();
            this.editingResult = null;
          },
          error: (error) => {
            console.error('Erro ao atualizar resultado:', error);
            this.error = 'Erro ao salvar resultado. Tente novamente.';
          }
        });
    } else {
      // Criar novo resultado
      this.workoutResultService.createWorkoutResult(workoutResult)
        .subscribe({
          next: (newResult) => {
            this.workoutResults.push(newResult);
            this.calculateLeaderboard();
            this.editingResult = null;
          },
          error: (error) => {
            console.error('Erro ao criar resultado:', error);
            this.error = 'Erro ao salvar resultado. Tente novamente.';
          }
        });
    }
  }

  cancelEditing(): void {
    this.editingResult = null;
  }

  isEditing(equipeId: number, workoutId: number): boolean {
    return this.editingResult?.equipeId === equipeId && this.editingResult?.workoutId === workoutId;
  }

  getMedalIcon(position: number): string {
    switch (position) {
      case 1: return '🥇';
      case 2: return '🥈';
      case 3: return '🥉';
      default: return '';
    }
  }

  // Métodos para edição inline
  onResultInput(event: any, equipeId: number, workoutId: number): void {
    const key = `${equipeId}-${workoutId}`;
    this.tempResults[key] = event.target.value;
  }

  saveResultInline(equipeId: number, workoutId: number): void {
    const key = `${equipeId}-${workoutId}`;
    const result = this.tempResults[key];
    
    if (result !== undefined && result.trim() !== '') {
      // Verificar se já existe um resultado
      const existingResult = this.workoutResults.find(r => 
        r.equipeId === equipeId && r.workoutId === workoutId
      );

      if (existingResult) {
        // Atualizar resultado existente
        existingResult.result = result;
        this.workoutResultService.updateWorkoutResult(existingResult.id!, existingResult).subscribe({
          next: () => {
            this.calculateLeaderboard();
            delete this.tempResults[key];
          },
          error: (error) => {
            console.error('Erro ao atualizar resultado:', error);
          }
        });
      } else {
        // Criar novo resultado
        const newResult: WorkoutResult = {
          equipeId: equipeId,
          workoutId: workoutId,
          result: result
        };
        
        this.workoutResultService.createWorkoutResult(newResult).subscribe({
          next: () => {
            this.loadLeaderboardData();
            delete this.tempResults[key];
          },
          error: (error) => {
            console.error('Erro ao criar resultado:', error);
          }
        });
      }
    } else if (result !== undefined && result.trim() === '') {
      // Se o campo foi limpo, remover o resultado
      const existingResult = this.workoutResults.find(r => 
        r.equipeId === equipeId && r.workoutId === workoutId
      );
      
      if (existingResult) {
        this.workoutResultService.deleteWorkoutResult(existingResult.id!).subscribe({
          next: () => {
            this.loadLeaderboardData();
            delete this.tempResults[key];
          },
          error: (error) => {
            console.error('Erro ao deletar resultado:', error);
          }
        });
      } else {
        delete this.tempResults[key];
      }
    }
  }

  cancelResultEdit(equipeId: number, workoutId: number): void {
    const key = `${equipeId}-${workoutId}`;
    delete this.tempResults[key];
  }

  resetLeaderboard(): void {
    if (confirm('Tem certeza que deseja resetar o leaderboard? Todos os resultados serão perdidos.')) {
      // Implementar lógica de reset
      console.log('Resetando leaderboard...');
    }
  }

  downloadPDF(): void {
    // Implementar download de PDF
    console.log('Baixando PDF...');
  }
} 