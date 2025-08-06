import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EquipesService, Equipe } from '../../services/equipes.service';

@Component({
  selector: 'app-equipes-list',
  templateUrl: './equipes-list.component.html',
  styleUrls: ['./equipes-list.component.css']
})
export class EquipesListComponent implements OnInit {

  equipes: Equipe[] = [];
  loading: boolean = false;
  error: string = '';

  constructor(
    private router: Router,
    private equipesService: EquipesService
  ) { }

  ngOnInit() {
    this.carregarEquipes();
  }

  carregarEquipes() {
    this.loading = true;
    this.error = '';

    this.equipesService.getEquipes().subscribe({
      next: (equipes) => {
        console.log('Equipes carregadas:', equipes);
        this.equipes = equipes;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar equipes:', error);
        this.error = 'Erro ao carregar equipes. Tente novamente.';
        this.loading = false;
      }
    });
  }

  novaEquipe() {
    console.log('Navegando para criar nova equipe...');
    this.router.navigate(['/equipes/create']);
  }

  voltarAoLeaderboard() {
    console.log('Voltando ao leaderboard...');
    this.router.navigate(['/leaderboard']);
  }

  editarEquipe(equipe: Equipe) {
    console.log('Editando equipe:', equipe);
    // TODO: Implementar edição de equipe
    alert('Funcionalidade de edição será implementada em breve!');
  }

  deletarEquipe(equipe: Equipe) {
    if (confirm(`Tem certeza que deseja deletar a equipe "${equipe.nome}"?`)) {
      console.log('Deletando equipe:', equipe);
      
      if (equipe.id) {
        this.equipesService.deletarEquipe(equipe.id).subscribe({
          next: () => {
            console.log('Equipe deletada com sucesso');
            this.carregarEquipes(); // Recarrega a lista
          },
          error: (error) => {
            console.error('Erro ao deletar equipe:', error);
            alert('Erro ao deletar equipe. Tente novamente.');
          }
        });
      }
    }
  }
}
