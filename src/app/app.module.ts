import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { LeaderboardComponent } from './shared/components/leaderboard.component';

// Feature Modules
import { EquipesModule } from './features/equipes/equipes.module';
import { WorkoutsModule } from './features/workouts/workouts.module';

@NgModule({
  declarations: [
    AppComponent,
    LeaderboardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    EquipesModule,
    WorkoutsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { } 