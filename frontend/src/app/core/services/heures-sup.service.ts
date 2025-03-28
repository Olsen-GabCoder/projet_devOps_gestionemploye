import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HeuresSup } from '../../models/heures-sup.model';

@Injectable({
  providedIn: 'root'
})
export class HeuresSupService {

  private baseUrl = 'http://localhost:8081/api/heures-sup'; // URL de l'API Spring Boot

  constructor(private http: HttpClient) { }

  getHeuresSupByEmployeeId(employeeId: number): Observable<HeuresSup[]> {
    return this.http.get<HeuresSup[]>(`${this.baseUrl}?employeId=${employeeId}`);
  }

  addHeuresSup(heuresSup: HeuresSup): Observable<HeuresSup> {
    return this.http.post<HeuresSup>(this.baseUrl, heuresSup);
  }

  deleteHeuresSup(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  // Nouvelle méthode pour récupérer les heures supplémentaires par employé et par période
  getHeuresSupByEmployeeIdAndPeriode(employeeId: number, debutPeriode: Date, finPeriode: Date): Observable<HeuresSup[]> {
    let params = new HttpParams()
      .set('employeId', employeeId.toString())
      .set('debutPeriode', debutPeriode.toISOString())
      .set('finPeriode', finPeriode.toISOString());

    return this.http.get<HeuresSup[]>(this.baseUrl, { params: params });
  }
}