package com.example.backend.service;

import com.example.backend.model.Tarif;
import com.example.backend.repository.TarifRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarifServiceTest {

    @Mock
    private TarifRepository tarifRepository;

    @InjectMocks
    private TarifService tarifService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTarifs_ReturnsListOfTarifs() {
        List<Tarif> tarifs = new ArrayList<>();
        tarifs.add(new Tarif());
        tarifs.add(new Tarif());
        when(tarifRepository.findAll()).thenReturn(tarifs);

        List<Tarif> result = tarifService.getAllTarifs();

        assertEquals(2, result.size());
        verify(tarifRepository, times(1)).findAll();
    }

    @Test
    void getTarifById_ExistingId_ReturnsTarif() {
        Tarif tarif = new Tarif();
        tarif.setId(1L);
        when(tarifRepository.findById(1L)).thenReturn(Optional.of(tarif));

        Tarif result = tarifService.getTarifById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tarifRepository, times(1)).findById(1L);
    }

    @Test
    void getTarifById_NonExistingId_ReturnsNull() {
        when(tarifRepository.findById(1L)).thenReturn(Optional.empty());

        Tarif result = tarifService.getTarifById(1L);

        assertNull(result);
        verify(tarifRepository, times(1)).findById(1L);
    }

    @Test
    void createTarif_ValidTarif_ReturnsSavedTarif() {
        Tarif tarif = new Tarif();
        tarif.setTypeJour("Lundi");
        when(tarifRepository.save(tarif)).thenReturn(tarif);

        Tarif result = tarifService.createTarif(tarif);

        assertNotNull(result);
        assertEquals("Lundi", tarif.getTypeJour());
        verify(tarifRepository, times(1)).save(tarif);
    }

    @Test
    void updateTarif_ExistingId_ReturnsUpdatedTarif() {
        Tarif existingTarif = new Tarif();
        existingTarif.setId(1L);
        Tarif updatedTarif = new Tarif();
        updatedTarif.setTypeJour("Mardi");
        when(tarifRepository.findById(1L)).thenReturn(Optional.of(existingTarif));
        when(tarifRepository.save(updatedTarif)).thenReturn(updatedTarif);

        Tarif result = tarifService.updateTarif(1L, updatedTarif);

        assertNotNull(result);
        assertEquals("Mardi", result.getTypeJour());
        assertEquals(1L, result.getId());
        verify(tarifRepository, times(1)).findById(1L);
        verify(tarifRepository, times(1)).save(updatedTarif);
    }

    @Test
    void updateTarif_NonExistingId_ReturnsNull() {
        Tarif updatedTarif = new Tarif();
        when(tarifRepository.findById(1L)).thenReturn(Optional.empty());

        Tarif result = tarifService.updateTarif(1L, updatedTarif);

        assertNull(result);
        verify(tarifRepository, times(1)).findById(1L);
        verify(tarifRepository, never()).save(any());
    }

    @Test
    void deleteTarif_ExistingId_ReturnsTrue() {
        when(tarifRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tarifRepository).deleteById(1L);

        boolean result = tarifService.deleteTarif(1L);

        assertTrue(result);
        verify(tarifRepository, times(1)).existsById(1L);
        verify(tarifRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTarif_NonExistingId_ReturnsFalse() {
        when(tarifRepository.existsById(1L)).thenReturn(false);

        boolean result = tarifService.deleteTarif(1L);

        assertFalse(result);
        verify(tarifRepository, times(1)).existsById(1L);
        verify(tarifRepository, never()).deleteById(any());
    }

    @Test
    void getTarifByTypeJour_ExistingTypeJour_ReturnsListOfTarifs() {
        // Arrange
        List<Tarif> tarifs = new ArrayList<>();
        Tarif tarif1 = new Tarif();
        tarif1.setTypeJour("Lundi");
        Tarif tarif2 = new Tarif();
        tarif2.setTypeJour("Lundi");
        tarifs.add(tarif1);
        tarifs.add(tarif2);

        when(tarifRepository.findByTypeJourIgnoreCase("Lundi")).thenReturn(tarifs);

        // Act
        List<Tarif> result = tarifService.getTarifByTypeJour("Lundi");

        // Assert
        assertEquals(2, result.size());
        assertEquals("Lundi", result.get(0).getTypeJour());
        assertEquals("Lundi", result.get(1).getTypeJour());
        verify(tarifRepository, times(1)).findByTypeJourIgnoreCase("Lundi");
    }

    @Test
    void getTarifByTypeJour_NonExistingTypeJour_ReturnsEmptyList() {
        // Arrange
        when(tarifRepository.findByTypeJourIgnoreCase("Lundi")).thenReturn(new ArrayList<>());

        // Act
        List<Tarif> result = tarifService.getTarifByTypeJour("Lundi");

        // Assert
        assertTrue(result.isEmpty());
        verify(tarifRepository, times(1)).findByTypeJourIgnoreCase("Lundi");
    }
}