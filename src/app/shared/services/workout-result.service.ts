import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface WorkoutResult {
  id?: number;
  equipeId: number;
  workoutId: number;
  result: string;
  position?: number;
  points?: number;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

export interface LeaderboardEntry {
  equipeId: number;
  equipeNome: string;
  workoutResults: { [workoutId: number]: WorkoutResult };
  totalPoints: number;
  positions: { [workoutId: number]: number };
}

@Injectable({
  providedIn: 'root'
})
export class WorkoutResultService {
  private apiUrl = 'http://localhost:8080/api/workout-results';

  constructor(private http: HttpClient) { }

  getWorkoutResults(): Observable<WorkoutResult[]> {
    return this.http.get<WorkoutResult[]>(this.apiUrl);
  }

  getWorkoutResult(id: number): Observable<WorkoutResult> {
    return this.http.get<WorkoutResult>(`${this.apiUrl}/${id}`);
  }

  createWorkoutResult(result: WorkoutResult): Observable<WorkoutResult> {
    return this.http.post<WorkoutResult>(this.apiUrl, result);
  }

  updateWorkoutResult(id: number, result: WorkoutResult): Observable<WorkoutResult> {
    return this.http.put<WorkoutResult>(`${this.apiUrl}/${id}`, result);
  }

  deleteWorkoutResult(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getLeaderboardData(): Observable<LeaderboardEntry[]> {
    return this.http.get<LeaderboardEntry[]>(`${this.apiUrl}/leaderboard`);
  }
} 