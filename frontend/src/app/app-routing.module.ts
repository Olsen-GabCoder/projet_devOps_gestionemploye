import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeListComponent } from './components/employee-list/employee-list.component';
import { EmployeeDetailsComponent } from './components/employee-details/employee-details.component';

const routes: Routes = [
  { path: 'employees', component: EmployeeListComponent },
  { path: 'employees/:id', component: EmployeeDetailsComponent }, // Nouveau
  { path: '', redirectTo: '/employees', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }