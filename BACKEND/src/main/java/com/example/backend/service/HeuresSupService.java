package com.example.backend.service;

import com.example.backend.model.HeuresSup;
import com.example.backend.repository.HeuresSupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeuresSupService {

    @Autowired
    private HeuresSupRepository heuresSupRepository;

    public List<HeuresSup> getAllHeuresSup() {
        return heuresSupRepository.findAll();
    }

    public HeuresSup getHeuresSupById(Long id) {
        return heuresSupRepository.findById(id).orElse(null);
    }

    public HeuresSup createHeuresSup(HeuresSup heuresSup) {
        return heuresSupRepository.save(heuresSup);
    }

    public HeuresSup updateHeuresSup(Long id, HeuresSup heuresSup) {
        HeuresSup existingHeuresSup = heuresSupRepository.findById(id).orElse(null);
        if (existingHeuresSup != null) {
            heuresSup.setId(id);
            return heuresSupRepository.save(heuresSup);
        }
        return null;
    }

    public boolean deleteHeuresSup(Long id) {
        if (heuresSupRepository.existsById(id)) {
            heuresSupRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<HeuresSup> getHeuresSupByEmployeId(Long employeId) {
        return heuresSupRepository.findByEmployeId(employeId);
    }
}