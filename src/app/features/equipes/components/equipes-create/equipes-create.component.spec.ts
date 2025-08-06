import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipesCreateComponent } from './equipes-create.component';

describe('EquipesCreateComponent', () => {
  let component: EquipesCreateComponent;
  let fixture: ComponentFixture<EquipesCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EquipesCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EquipesCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
