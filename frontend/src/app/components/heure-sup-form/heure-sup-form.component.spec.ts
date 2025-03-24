import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeureSupFormComponent } from './heure-sup-form.component';

describe('HeureSupFormComponent', () => {
  let component: HeureSupFormComponent;
  let fixture: ComponentFixture<HeureSupFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeureSupFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeureSupFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
