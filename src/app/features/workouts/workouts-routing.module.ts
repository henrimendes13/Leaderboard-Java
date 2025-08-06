import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WorkoutsListComponent } from './components/workouts-list/workouts-list.component';
import { WorkoutsCreateComponent } from './components/workouts-create/workouts-create.component';

const routes: Routes = [
  { path: '', component: WorkoutsListComponent },
  { path: 'create', component: WorkoutsCreateComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkoutsRoutingModule { } 