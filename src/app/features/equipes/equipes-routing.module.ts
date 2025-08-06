import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EquipesListComponent } from './components/equipes-list/equipes-list.component';
import { EquipesCreateComponent } from './components/equipes-create/equipes-create.component';

const routes: Routes = [
  { path: '', component: EquipesListComponent },
  { path: 'create', component: EquipesCreateComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EquipesRoutingModule { } 