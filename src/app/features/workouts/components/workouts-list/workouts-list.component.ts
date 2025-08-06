import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WorkoutsService, Workout } from '../../services/workouts.service';

@Component({
  selector: 'app-workouts-list',
  templateUrl: './workouts-list.component.html',
  styleUrls: ['./workouts-list.component.css']
})
export class WorkoutsListComponent implements OnInit {

  workouts: Workout[] = [];
  loading = false;
  error: string | null = null;

  constructor(
    private router: Router,
    private workoutsService: WorkoutsService
  ) { }

  ngOnInit() {
    this.carregarWorkouts();
  }

  carregarWorkouts() {
    this.loading = true;
    this.error = null;

    this.workoutsService.getWorkouts().subscribe({
      next: (workouts) => {
        console.log('Workouts carregados:', workouts);
        this.workouts = workouts;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar workouts:', error);
        this.error = 'Erro ao carregar workouts. Tente novamente.';
        this.loading = false;
      }
    });
  }

  novoWorkout() {
    console.log('Navegando para criar novo workout...');
    this.router.navigate(['/workouts/create']);
  }

  editarWorkout(workout: Workout) {
    console.log('Editando workout:', workout);
    // TODO: Implementar edição
    alert('Funcionalidade de edição será implementada em breve!');
  }

  deletarWorkout(workout: Workout) {
    if (!workout.id) {
      alert('ID do workout não encontrado.');
      return;
    }

    if (confirm(`Tem certeza que deseja deletar o workout "${workout.nome}"?`)) {
      this.loading = true;
      
      this.workoutsService.deletarWorkout(workout.id).subscribe({
        next: () => {
          console.log('Workout deletado com sucesso');
          this.carregarWorkouts(); // Recarrega a lista
        },
        error: (error) => {
          console.error('Erro ao deletar workout:', error);
          alert('Erro ao deletar workout. Tente novamente.');
          this.loading = false;
        }
      });
    }
  }

  voltarAoLeaderboard() {
    console.log('Voltando ao leaderboard...');
    this.router.navigate(['/leaderboard']);
  }

  getTipoDisplay(tipo: string): string {
    switch (tipo) {
      case 'REPETICOES':
        return 'Repetições';
      case 'TEMPO':
        return 'Tempo';
      case 'PESO':
        return 'Peso';
      default:
        return tipo;
    }
  }
}
