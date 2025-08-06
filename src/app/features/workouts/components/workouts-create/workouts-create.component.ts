import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { WorkoutsService, Workout } from '../../services/workouts.service';

@Component({
  selector: 'app-workouts-create',
  templateUrl: './workouts-create.component.html',
  styleUrls: ['./workouts-create.component.css']
})
export class WorkoutsCreateComponent {

  constructor(
    private router: Router,
    private workoutsService: WorkoutsService
  ) { }

  workout: Workout = {
    nome: '',
    descricao: '',
    tipo: '',
    unidade: ''
  };

  loading = false;

  salvarWorkout() {
    console.log('Salvando workout:', this.workout);
    
    if (!this.workout.nome || !this.workout.tipo || !this.workout.unidade) {
      alert('Por favor, preencha todos os campos obrigatórios.');
      return;
    }
    
    this.loading = true;
    
    this.workoutsService.criarWorkout(this.workout).subscribe({
      next: (workoutSalvo) => {
        console.log('Workout salvo com sucesso:', workoutSalvo);
        alert('Workout salvo com sucesso!');
        this.voltar();
      },
      error: (error) => {
        console.error('Erro ao salvar workout:', error);
        let mensagemErro = 'Erro ao salvar workout.';
        
        if (error.error && error.error.error) {
          mensagemErro = error.error.error;
        } else if (error.status === 400) {
          mensagemErro = 'Dados inválidos. Verifique os campos obrigatórios.';
        } else if (error.status === 409) {
          mensagemErro = 'Já existe um workout com este nome.';
        }
        
        alert(mensagemErro);
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  cancelar() {
    console.log('Cancelando criação de workout...');
    if (confirm('Tem certeza que deseja cancelar? Os dados não salvos serão perdidos.')) {
      this.voltar();
    }
  }

  voltar() {
    console.log('Voltando para tela de workouts...');
    this.router.navigate(['/workouts']);
  }
}
