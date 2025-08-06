import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Leaderboard } from './leaderboard.model';

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  // Usando URL relativa para funcionar com proxy
  private apiUrl = '/api/leaderboard';

  constructor(private http: HttpClient) { }

  // Teste de conexão com backend
  testConnection(): Observable<string> {
    return this.http.get(`${this.apiUrl}/test`, { responseType: 'text' });
  }

  // Verificar saúde da API
  getHealth(): Observable<string> {
    return this.http.get(`${this.apiUrl}/health`, { responseType: 'text' });
  }

  // Service methods will be implemented here
} 