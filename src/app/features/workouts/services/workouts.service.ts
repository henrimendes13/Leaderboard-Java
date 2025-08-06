import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Workout {
  id?: number;
  nome: string;
  descricao?: string;
  tipo: string;
  unidade: string;
  ativo?: boolean;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

@Injectable({
  providedIn: 'root'
})
export class WorkoutsService {
  private apiUrl = 'http://localhost:8080/api/workouts';

  constructor(private http: HttpClient) { }

  getWorkouts(): Observable<Workout[]> {
    return this.http.get<Workout[]>(this.apiUrl);
  }

  getWorkout(id: number): Observable<Workout> {
    return this.http.get<Workout>(`${this.apiUrl}/${id}`);
  }

  criarWorkout(workout: Workout): Observable<Workout> {
    return this.http.post<Workout>(this.apiUrl, workout);
  }

  atualizarWorkout(id: number, workout: Workout): Observable<Workout> {
    return this.http.put<Workout>(`${this.apiUrl}/${id}`, workout);
  }

  deletarWorkout(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
} 