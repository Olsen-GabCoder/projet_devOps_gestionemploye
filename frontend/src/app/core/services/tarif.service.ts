import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Tarif } from '../../models/tarif.model';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TarifService {

  private baseUrl = 'http://16.16.166.194:8081/api/tarifs'; // URL de l'API Spring Boot

  constructor(private http: HttpClient) { }

  getTarifByTypeJour(typeJour: string): Observable<Tarif> {
    let url = '';
    if (typeJour === "jour ordinaire" || typeJour === "weekend") {
      url = `${this.baseUrl}/type/${typeJour}`;
    } else {
      // Sécurité. Envoie un message d'erreur si l'API n'est pas correcte
      console.error(`Type de jour inconnu: ${typeJour}`);
      // Retourne un tarif par défaut avec un type de jour valide
      return of({ id: 0, typeJour: 'jour ordinaire', tarif: 0 });
    }

    return this.http.get<Tarif[]>(url).pipe(
      map(tarifs => {
        if (tarifs && tarifs.length > 0) {
          return tarifs[0]; // Retourne le premier tarif trouvé
        } else {
          console.warn(`Tarif non trouvé pour le type de jour : ${typeJour}`);
          // Retourne un tarif par défaut avec le typeJour correct
          const defaultTarif: Tarif = {
            id: 0,
            typeJour: (typeJour === 'weekend' || typeJour === 'jour ordinaire') ? typeJour : 'jour ordinaire',
            tarif: 0
          };
          return defaultTarif;
        }
      }),
      catchError(error => {
        console.error('Erreur lors de la récupération du tarif', error);
        // Retourne un tarif par défaut avec le typeJour correct
        const defaultTarif: Tarif = {
          id: 0,
          typeJour: (typeJour === 'weekend' || typeJour === 'jour ordinaire') ? typeJour : 'jour ordinaire',
          tarif: 0
        };
        return of(defaultTarif);
      })
    );
  }
}