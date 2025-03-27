package com.example.backend.controller;

import com.example.backend.model.Tarif;
import com.example.backend.service.TarifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifs")
@CrossOrigin(origins = "http://16.16.166.194:4200")
public class TarifController {

    @Autowired
    private TarifService tarifService;

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @GetMapping
    public List<Tarif> getAllTarifs() {
        System.out.println("Récupération de tous les tarifs.");
        List<Tarif> tarifs = tarifService.getAllTarifs();
        System.out.println("Tarifs récupérés : " + tarifs);  // Affiche la liste des tarifs récupérés
        return tarifs;
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @GetMapping("/{id}")
    public ResponseEntity<Tarif> getTarifById(@PathVariable Long id) {
        System.out.println("Récupération du tarif avec l'ID : " + id);
        Tarif tarif = tarifService.getTarifById(id);
        if (tarif != null) {
            System.out.println("Tarif trouvé : " + tarif);
            return new ResponseEntity<>(tarif, HttpStatus.OK);
        } else {
            System.out.println("Tarif non trouvé pour l'ID : " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @GetMapping("/type/{typeJour}") // Utilisation de @PathVariable
    public ResponseEntity<List<Tarif>> getTarifByTypeJour(@PathVariable String typeJour) {
        System.out.println("Récupération du tarif pour le type de jour : " + typeJour); // Ajout du log

        List<Tarif> tarifs = tarifService.getTarifByTypeJour(typeJour);

        if (tarifs != null && !tarifs.isEmpty()) {
            System.out.println("Tarifs trouvés pour le type de jour " + typeJour + ": " + tarifs);
            return new ResponseEntity<>(tarifs, HttpStatus.OK);
        } else {
            System.out.println("Aucun tarif trouvé pour le type de jour : " + typeJour);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @PostMapping
    public Tarif createTarif(@RequestBody Tarif tarif) {
        System.out.println("Création d'un nouveau tarif : " + tarif);
        return tarifService.createTarif(tarif);
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @PutMapping("/{id}")
    public ResponseEntity<Tarif> updateTarif(@PathVariable Long id, @RequestBody Tarif tarif) {
        System.out.println("Mise à jour du tarif avec l'ID : " + id + ", nouvelles données : " + tarif);
        Tarif updatedTarif = tarifService.updateTarif(id, tarif);
        if (updatedTarif != null) {
            System.out.println("Tarif mis à jour : " + updatedTarif);
            return new ResponseEntity<>(updatedTarif, HttpStatus.OK);
        } else {
            System.out.println("Tarif non trouvé pour la mise à jour avec l'ID : " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarif(@PathVariable Long id) {
        System.out.println("Suppression du tarif avec l'ID : " + id);
        boolean deleted = tarifService.deleteTarif(id);
        if (deleted) {
            System.out.println("Tarif supprimé avec succès.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            System.out.println("Tarif non trouvé pour la suppression avec l'ID : " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}