package com.example.backend.service;

import com.example.backend.model.HeuresSup;
import com.example.backend.repository.HeuresSupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HeuresSupServiceTest {

    @Mock
    private HeuresSupRepository heuresSupRepository;

    @InjectMocks
    private HeuresSupService heuresSupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllHeuresSup_ReturnsListOfHeuresSup() {
        List<HeuresSup> heuresSupList = new ArrayList<>();
        heuresSupList.add(new HeuresSup());
        heuresSupList.add(new HeuresSup());
        when(heuresSupRepository.findAll()).thenReturn(heuresSupList);

        List<HeuresSup> result = heuresSupService.getAllHeuresSup();

        assertEquals(2, result.size());
        verify(heuresSupRepository, times(1)).findAll();
    }

    @Test
    void getHeuresSupById_ExistingId_ReturnsHeuresSup() {
        HeuresSup heuresSup = new HeuresSup();
        heuresSup.setId(1L);
        when(heuresSupRepository.findById(1L)).thenReturn(Optional.of(heuresSup));

        HeuresSup result = heuresSupService.getHeuresSupById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(heuresSupRepository, times(1)).findById(1L);
    }

    @Test
    void getHeuresSupById_NonExistingId_ReturnsNull() {
        when(heuresSupRepository.findById(1L)).thenReturn(Optional.empty());

        HeuresSup result = heuresSupService.getHeuresSupById(1L);

        assertNull(result);
        verify(heuresSupRepository, times(1)).findById(1L);
    }

    @Test
    void createHeuresSup_ValidHeuresSup_ReturnsSavedHeuresSup() {
        HeuresSup heuresSup = new HeuresSup();
        heuresSup.setNbHeures(8.0f);
        when(heuresSupRepository.save(heuresSup)).thenReturn(heuresSup);

        HeuresSup result = heuresSupService.createHeuresSup(heuresSup);

        assertNotNull(result);
        assertEquals(8.0f, heuresSup.getNbHeures());
        verify(heuresSupRepository, times(1)).save(heuresSup);
    }

    @Test
    void updateHeuresSup_ExistingId_ReturnsUpdatedHeuresSup() {
        HeuresSup existingHeuresSup = new HeuresSup();
        existingHeuresSup.setId(1L);
        HeuresSup updatedHeuresSup = new HeuresSup();
        updatedHeuresSup.setNbHeures(10.0f);
        when(heuresSupRepository.findById(1L)).thenReturn(Optional.of(existingHeuresSup));
        when(heuresSupRepository.save(updatedHeuresSup)).thenReturn(updatedHeuresSup);

        HeuresSup result = heuresSupService.updateHeuresSup(1L, updatedHeuresSup);

        assertNotNull(result);
        assertEquals(10.0f, result.getNbHeures());
        assertEquals(1L, result.getId());
        verify(heuresSupRepository, times(1)).findById(1L);
        verify(heuresSupRepository, times(1)).save(updatedHeuresSup);
    }

    @Test
    void updateHeuresSup_NonExistingId_ReturnsNull() {
        HeuresSup updatedHeuresSup = new HeuresSup();
        when(heuresSupRepository.findById(1L)).thenReturn(Optional.empty());

        HeuresSup result = heuresSupService.updateHeuresSup(1L, updatedHeuresSup);

        assertNull(result);
        verify(heuresSupRepository, times(1)).findById(1L);
        verify(heuresSupRepository, never()).save(any());
    }

    @Test
    void deleteHeuresSup_ExistingId_ReturnsTrue() {
        when(heuresSupRepository.existsById(1L)).thenReturn(true);
        doNothing().when(heuresSupRepository).deleteById(1L);

        boolean result = heuresSupService.deleteHeuresSup(1L);

        assertTrue(result);
        verify(heuresSupRepository, times(1)).existsById(1L);
        verify(heuresSupRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteHeuresSup_NonExistingId_ReturnsFalse() {
        when(heuresSupRepository.existsById(1L)).thenReturn(false);

        boolean result = heuresSupService.deleteHeuresSup(1L);

        assertFalse(result);
        verify(heuresSupRepository, times(1)).existsById(1L);
        verify(heuresSupRepository, never()).deleteById(any());
    }

    @Test
    void getHeuresSupByEmployeId_ExistingEmployeId_ReturnsListOfHeuresSup() {
        // Arrange
        List<HeuresSup> heuresSupList = new ArrayList<>();
        HeuresSup heuresSup1 = new HeuresSup();
        heuresSup1.setEmployeId(1L);
        HeuresSup heuresSup2 = new HeuresSup();
        heuresSup2.setEmployeId(1L);
        heuresSupList.add(heuresSup1);
        heuresSupList.add(heuresSup2);

        when(heuresSupRepository.findByEmployeId(1L)).thenReturn(heuresSupList);

        // Act
        List<HeuresSup> result = heuresSupService.getHeuresSupByEmployeId(1L);

        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getEmployeId());
        assertEquals(1L, result.get(1).getEmployeId());
        verify(heuresSupRepository, times(1)).findByEmployeId(1L);
    }

    @Test
    void getHeuresSupByEmployeId_NonExistingEmployeId_ReturnsEmptyList() {
        // Arrange
        when(heuresSupRepository.findByEmployeId(1L)).thenReturn(new ArrayList<>());

        // Act
        List<HeuresSup> result = heuresSupService.getHeuresSupByEmployeId(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(heuresSupRepository, times(1)).findByEmployeId(1L);
    }

}