package com.example.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "heures_sup")
public class HeuresSup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employe_id")
    private Long employeId;

    @Column(name = "date")
    private Date date;

    @Column(name = "nb_heures")
    private Float nbHeures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getNbHeures() {
        return nbHeures;
    }

    public void setNbHeures(Float nbHeures) {
        this.nbHeures = nbHeures;
    }
}