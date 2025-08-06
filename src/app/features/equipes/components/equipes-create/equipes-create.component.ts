import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { EquipesService, Equipe } from '../../services/equipes.service';

@Component({
  selector: 'app-equipes-create',
  templateUrl: './equipes-create.component.html',
  styleUrls: ['./equipes-create.component.css']
})
export class EquipesCreateComponent {

  constructor(
    private router: Router,
    private equipesService: EquipesService
  ) { }

  equipe: Equipe = {
    nome: '',
    pontos: 0
  };

  salvarEquipe() {
    console.log('Salvando equipe:', this.equipe);
    
    if (!this.equipe.nome) {
      alert('Por favor, preencha o nome da equipe.');
      return;
    }
    
    // Chamar o serviço para salvar no backend
    this.equipesService.criarEquipe(this.equipe).subscribe({
      next: (equipeSalva) => {
        console.log('Equipe salva com sucesso:', equipeSalva);
        alert('Equipe salva com sucesso!');
        this.voltar();
      },
      error: (error) => {
        console.error('Erro ao salvar equipe:', error);
        alert('Erro ao salvar equipe. Tente novamente.');
      }
    });
  }

  cancelar() {
    console.log('Cancelando criação de equipe...');
    if (confirm('Tem certeza que deseja cancelar? Os dados não salvos serão perdidos.')) {
      this.voltar();
    }
  }

  voltar() {
    console.log('Voltando para tela de equipes...');
    this.router.navigate(['/equipes']);
  }
}
