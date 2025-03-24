import { Component, OnInit } from '@angular/core';
import { Employee } from '../../models/employee.model';
import { EmployeeService } from '../../core/services/employee.service';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  dataSource: MatTableDataSource<Employee> = new MatTableDataSource<Employee>();
  displayedColumns: string[] = ['id', 'nom', 'prenom', 'poste', 'details'];

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.loadEmployees(); // Call loadEmployees instead of subscribing directly
  }

  loadEmployees(): void {
    this.employeeService.getEmployees().subscribe(employees => {
      this.dataSource = new MatTableDataSource(employees); // Assign to dataSource here
    });
  }
}