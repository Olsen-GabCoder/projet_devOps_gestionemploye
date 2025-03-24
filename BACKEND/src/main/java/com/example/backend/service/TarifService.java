package com.example.backend.service;

import com.example.backend.model.Tarif;
import com.example.backend.repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifService {

    @Autowired
    private TarifRepository tarifRepository;

    public List<Tarif> getAllTarifs() {
        System.out.println("Récupération de tous les tarifs.");
        return tarifRepository.findAll();
    }

    public Tarif getTarifById(Long id) {
        System.out.println("Récupération du tarif par ID : " + id);
        return tarifRepository.findById(id).orElse(null);
    }

    public Tarif createTarif(Tarif tarif) {
        System.out.println("Création du tarif : " + tarif);
        return tarifRepository.save(tarif);
    }

    public Tarif updateTarif(Long id, Tarif tarif) {
        System.out.println("Mise à jour du tarif : " + tarif + "avec l'id: " + id);
        Tarif existingTarif = tarifRepository.findById(id).orElse(null);
        if (existingTarif != null) {
            tarif.setId(id);
            return tarifRepository.save(tarif);
        }
        return null;
    }

    public boolean deleteTarif(Long id) {
        System.out.println("Suppression du tarif " + id);
        if (tarifRepository.existsById(id)) {
            tarifRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Tarif> getTarifByTypeJour(String typeJour) {
        System.out.println("Récupération de tous les tarifs " + typeJour + ".");
        return tarifRepository.findByTypeJourIgnoreCase(typeJour); // Utilisation de la méthode avec IgnoreCase
    }
}