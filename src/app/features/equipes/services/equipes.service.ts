import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Equipe {
  id?: number;
  nome: string;
  pontos?: number;
  posicao?: number;
}

@Injectable({
  providedIn: 'root'
})
export class EquipesService {
  private apiUrl = 'http://localhost:8080/api/equipes';

  constructor(private http: HttpClient) { }

  getEquipes(): Observable<Equipe[]> {
    return this.http.get<Equipe[]>(this.apiUrl);
  }

  getEquipe(id: number): Observable<Equipe> {
    return this.http.get<Equipe>(`${this.apiUrl}/${id}`);
  }

  criarEquipe(equipe: Equipe): Observable<Equipe> {
    return this.http.post<Equipe>(this.apiUrl, equipe);
  }

  atualizarEquipe(id: number, equipe: Equipe): Observable<Equipe> {
    return this.http.put<Equipe>(`${this.apiUrl}/${id}`, equipe);
  }

  deletarEquipe(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
} 