package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tarif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeJour;
    private Float tarif;

    //Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeJour() {
        return typeJour;
    }

    public void setTypeJour(String typeJour) {
        this.typeJour = typeJour;
    }

    public Float getTarif() {
        return tarif;
    }

    public void setTarif(Float tarif) {
        this.tarif = tarif;
    }
}