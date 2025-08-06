import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EquipesRoutingModule } from './equipes-routing.module';

// Components
import { EquipesListComponent } from './components/equipes-list/equipes-list.component';
import { EquipesCreateComponent } from './components/equipes-create/equipes-create.component';

// Services
import { EquipesService } from './services/equipes.service';

@NgModule({
  declarations: [
    EquipesListComponent,
    EquipesCreateComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    EquipesRoutingModule
  ],
  providers: [
    EquipesService
  ],
  exports: [
    EquipesListComponent,
    EquipesCreateComponent
  ]
})
export class EquipesModule { } 