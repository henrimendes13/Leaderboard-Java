import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkoutsCreateComponent } from './workouts-create.component';

describe('WorkoutsCreateComponent', () => {
  let component: WorkoutsCreateComponent;
  let fixture: ComponentFixture<WorkoutsCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkoutsCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WorkoutsCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
