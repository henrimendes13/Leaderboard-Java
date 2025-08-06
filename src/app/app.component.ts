import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Leaderboard Personalizado';

  // Métodos para ações do header
  resetLeaderboard() {
    console.log('Resetando leaderboard...');
    if (confirm('Tem certeza que deseja resetar o leaderboard? Esta ação não pode ser desfeita.')) {
      alert('Leaderboard resetado com sucesso!');
    }
  }

  downloadPDF() {
    console.log('Baixando PDF...');
    alert('Funcionalidade de Download PDF será implementada em breve!');
  }
} 