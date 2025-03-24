package com.example.backend.repository;

import com.example.backend.model.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarifRepository extends JpaRepository<Tarif, Long> {
    List<Tarif> findByTypeJourIgnoreCase(String typeJour); // Utilisation de la méthode avec IgnoreCase
}