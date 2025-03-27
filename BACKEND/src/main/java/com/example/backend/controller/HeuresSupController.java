package com.example.backend.controller;

import com.example.backend.model.HeuresSup;
import com.example.backend.service.HeuresSupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heures-sup")
@CrossOrigin(origins = "http://16.16.166.194:4200")
public class HeuresSupController {

    @Autowired
    private HeuresSupService heuresSupService;

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @GetMapping
    public List<HeuresSup> getAllHeuresSup() {
        return heuresSupService.getAllHeuresSup();
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @GetMapping("/{id}")
    public ResponseEntity<HeuresSup> getHeuresSupById(@PathVariable Long id) {
        HeuresSup heuresSup = heuresSupService.getHeuresSupById(id);
        if (heuresSup != null) {
            return new ResponseEntity<>(heuresSup, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @GetMapping("/byEmployeeId") // Modification de l'URL
    public ResponseEntity<List<HeuresSup>> getHeuresSupByEmployeId(@RequestParam Long employeId) {
        List<HeuresSup> heuresSup = heuresSupService.getHeuresSupByEmployeId(employeId);
        return new ResponseEntity<>(heuresSup, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @PostMapping
    public HeuresSup createHeuresSup(@RequestBody HeuresSup heuresSup) {
        return heuresSupService.createHeuresSup(heuresSup);
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @PutMapping("/{id}")
    public ResponseEntity<HeuresSup> updateHeuresSup(@PathVariable Long id, @RequestBody HeuresSup heuresSup) {
        HeuresSup updatedHeuresSup = heuresSupService.updateHeuresSup(id, heuresSup);
        if (updatedHeuresSup != null) {
            return new ResponseEntity<>(updatedHeuresSup, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://16.16.166.194:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeuresSup(@PathVariable Long id) {
        boolean deleted = heuresSupService.deleteHeuresSup(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}