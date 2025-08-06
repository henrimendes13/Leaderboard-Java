import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LeaderboardComponent } from './shared/components/leaderboard.component';

const routes: Routes = [
  { path: '', redirectTo: '/leaderboard', pathMatch: 'full' },
  { path: 'leaderboard', component: LeaderboardComponent },
  { 
    path: 'equipes', 
    loadChildren: () => import('./features/equipes/equipes.module').then(m => m.EquipesModule)
  },
  { 
    path: 'workouts', 
    loadChildren: () => import('./features/workouts/workouts.module').then(m => m.WorkoutsModule)
  },
  { path: '**', redirectTo: '/leaderboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } 