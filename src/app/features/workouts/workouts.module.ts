import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WorkoutsRoutingModule } from './workouts-routing.module';

// Components
import { WorkoutsListComponent } from './components/workouts-list/workouts-list.component';
import { WorkoutsCreateComponent } from './components/workouts-create/workouts-create.component';

// Services
import { WorkoutsService } from 'src/app/features/workouts/services/workouts.service';

@NgModule({
  declarations: [
    WorkoutsListComponent,
    WorkoutsCreateComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    WorkoutsRoutingModule
  ],
  providers: [
    WorkoutsService
  ],
  exports: [
    WorkoutsListComponent,
    WorkoutsCreateComponent
  ]
})
export class WorkoutsModule { } 