import { Component, OnInit, ErrorHandler } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Employee } from '../../models/employee.model';
import { EmployeeService } from '../../core/services/employee.service';
import { HeuresSup } from '../../models/heures-sup.model';
import { HeuresSupService } from '../../core/services/heures-sup.service';
import { TarifService } from '../../core/services/tarif.service';
import { Tarif } from '../../models/tarif.model';
import { Observable, forkJoin, map } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { trigger, transition, style, animate } from '@angular/animations';
import { MatTableDataSource } from '@angular/material/table';

// Validator personnalisé pour vérifier que la date de début est antérieure à la date de fin
function dateRangeValidator(control: AbstractControl): { [key: string]: any } | null {
  const debutPeriode = control.get('debutPeriode');
  const finPeriode = control.get('finPeriode');

  if (debutPeriode && finPeriode && debutPeriode.value && finPeriode.value) {
    const debut = new Date(debutPeriode.value);
    const fin = new Date(finPeriode.value);

    if (debut > fin) {
      return { dateRangeInvalid: true };
    }
  }

  return null;
}

interface HeuresSupParJour { date: Date; nbHeures: number; tarif: number; cout: number; }

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms ease-in-out', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms ease-in-out', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class EmployeeDetailsComponent implements OnInit {

  employee: Employee | undefined;
  heuresSup: HeuresSup[] = [];
  totalHeuresSup: number = 0;
  coutTotalHeuresSup: number = 0;
  debutPeriode: Date | null = null;
  finPeriode: Date | null = null;
  periodeForm: FormGroup;
  ajoutHeuresSupForm: FormGroup;
  employeeId: number = 0;
  heuresSupParJour: HeuresSupParJour[] = []; // Utilisation de l'interface HeuresSupParJour
  displayedColumns: string[] = ['date', 'nbHeures', 'tarif', 'cout', 'actions'];
  isLoading = false;
  dataSourceHeuresSup = new MatTableDataSource<HeuresSupParJour>(); // Utilisation de l'interface HeuresSupParJour

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private heuresSupService: HeuresSupService,
    private tarifService: TarifService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private errorHandler: ErrorHandler
  ) {
    this.periodeForm = this.fb.group({
      debutPeriode: ['', Validators.required],
      finPeriode: ['', Validators.required]
    }, { validators: dateRangeValidator });

    this.ajoutHeuresSupForm = this.fb.group({
      date: ['', [Validators.required, this.datePasDansLeFuturValidator]],
      nbHeures: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.employeeId = +params['id'];
      this.loadEmployeeData();
    });
  }

  loadEmployeeData(): void {
    this.isLoading = true;

    forkJoin({
      employee: this.employeeService.getEmployee(this.employeeId),
      heuresSup: this.heuresSupService.getHeuresSupByEmployeeId(this.employeeId)
    }).pipe(
      finalize(() => this.isLoading = false)
    ).subscribe({
      next: ({ employee, heuresSup }) => {
        this.employee = employee;
        this.heuresSup = heuresSup;
        this.calculerHeuresSup();
      },
      error: (error) => {
        this.errorHandler.handleError(error);
        this.snackBar.open('Erreur lors du chargement des données', 'Fermer', {
          duration: 3000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }

  calculerHeuresSup(): void {
    if (this.debutPeriode && this.finPeriode) {
      this.totalHeuresSup = 0;
      this.coutTotalHeuresSup = 0;
      this.heuresSupParJour = [];

      let currentDate = new Date(this.debutPeriode);
      let endDate = new Date(this.finPeriode);

      let observables: Observable<any>[] = []; // Type any pour éviter les problèmes de typage

      while (currentDate <= endDate) {
        const date = new Date(currentDate);
        let nbHeures = 0;
        let typeJour: 'weekend' | 'jour ordinaire' = this.isWeekend(date) ? 'weekend' : 'jour ordinaire';

        this.heuresSup.forEach(heureSup => {
          const heureSupDate = new Date(heureSup.date);
          if (heureSupDate.toDateString() === date.toDateString()) {
            nbHeures = heureSup.nbHeures;
          }
        });

        observables.push(
          this.tarifService.getTarifByTypeJour(typeJour).pipe(
            map(tarif => {
              console.log(`Tarif récupéré pour le`, date, `(` + typeJour + `): `, tarif);

              //Affichage de données
              console.log('Data', date);
              console.log('TypeJour', typeJour);
              console.log('Tarif', tarif);
              //Ajout des logs

              let tarifValue = 0;
              if (tarif && tarif.tarif !== null && tarif.tarif !== undefined) {
                tarifValue = Number(tarif.tarif);
              }
              else {
                console.warn("Tarif non trouvé pour", typeJour);
              }
              if (isNaN(tarifValue)) {
                console.error("Tarif invalide pour", typeJour, "Tarif reçu:", tarif);
                tarifValue = 0; // Ou une autre valeur par défaut appropriée
              }
              const cout = tarifValue * nbHeures;
              console.log('Cout:', cout);

              return {
                date: date,
                nbHeures: nbHeures,
                tarif: tarifValue,
                cout: cout // Ajout du cout dans l'objet
              };
            })
          )
        );
        currentDate.setDate(currentDate.getDate() + 1);
      }

      forkJoin(observables).subscribe(heuresSupParJour => {
        this.heuresSupParJour = heuresSupParJour;
        this.dataSourceHeuresSup.data = heuresSupParJour;

        // Calculer les totaux après avoir récupéré tous les tarifs
        this.totalHeuresSup = this.heuresSupParJour.reduce((total, jour) => total + jour.nbHeures, 0);
        this.coutTotalHeuresSup = this.heuresSupParJour.reduce((total, jour) => total + jour.cout, 0);
        console.log("Total heures supplémentaires:", this.totalHeuresSup);
        console.log("Coût total:", this.coutTotalHeuresSup);
      });
    }
  }

  onPeriodeSubmit(): void {
    if (this.periodeForm.valid) {
      this.debutPeriode = this.periodeForm.value.debutPeriode;
      this.finPeriode = this.periodeForm.value.finPeriode;
      this.calculerHeuresSup();
    } else {
      this.snackBar.open('La date de début doit être antérieure à la date de fin', 'Fermer', {
        duration: 3000
      });
    }
  }

  isWeekend(date: Date): boolean {
    const day = date.getDay();
    return day === 0 || day === 6;
  }

  ajouterHeuresSup(): void {
    if (this.ajoutHeuresSupForm.valid) {
      this.isLoading = true;

      const newHeuresSup: HeuresSup = {
        id: 0,
        employeId: this.employeeId,
        date: this.ajoutHeuresSupForm.value.date,
        nbHeures: this.ajoutHeuresSupForm.value.nbHeures
      };

      this.heuresSupService.addHeuresSup(newHeuresSup).pipe(
        finalize(() => this.isLoading = false)
      ).subscribe({
        next: addedHeuresSup => {
          // Mettre à jour this.heuresSup avec la nouvelle liste
          this.heuresSupService.getHeuresSupByEmployeeId(this.employeeId).subscribe(updatedHeuresSup => {
            this.heuresSup = updatedHeuresSup;
            this.calculerHeuresSup();
          });
          this.ajoutHeuresSupForm.reset();
          this.snackBar.open('Heures supplémentaires ajoutées avec succès', 'Fermer', {
            duration: 3000
          });
        },
        error: (error) => {
          this.errorHandler.handleError(error);
          this.snackBar.open('Erreur lors de l\'ajout des heures supplémentaires', 'Fermer', {
            duration: 3000,
            panelClass: ['error-snackbar']
          });
        }
      });
    }
  }

  datePasDansLeFuturValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const date = new Date(control.value);
    const aujourdhui = new Date();
    aujourdhui.setHours(0, 0, 0, 0);

    if (date > aujourdhui) {
      return { dateDansLeFutur: true };
    }

    return null;
  }

  supprimerHeuresSup(heureSup: { date: Date, nbHeures: number, tarif: number }): void {
    this.isLoading = true;

    const heureSupAsHeuresSup = this.heuresSup.find(hs => new Date(hs.date).toDateString() === heureSup.date.toDateString() && hs.nbHeures === heureSup.nbHeures);

    if (heureSupAsHeuresSup) {
      this.heuresSupService.deleteHeuresSup(heureSupAsHeuresSup.id).pipe(
        finalize(() => this.isLoading = false)
      ).subscribe({
        next: () => {
          // Mettre à jour this.heuresSup avec la nouvelle liste
          this.heuresSupService.getHeuresSupByEmployeeId(this.employeeId).subscribe(updatedHeuresSup => {
            this.heuresSup = updatedHeuresSup;
            this.calculerHeuresSup();
          });
          this.snackBar.open('Heures supplémentaires supprimées avec succès', 'Fermer', {
            duration: 3000,
          });
        },
        error: (error) => {
          this.errorHandler.handleError(error);
          this.snackBar.open('Erreur lors de la suppression des heures supplémentaires', 'Fermer', {
            duration: 3000,
            panelClass: ['error-snackbar'],
          });
        },
      });
    } else {
      this.isLoading = false;
      this.snackBar.open('Erreur : Heure supplémentaire non trouvée', 'Fermer', {
        duration: 3000,
        panelClass: ['error-snackbar'],
      });
    }
  }
}