package com.example.backend.service;

import com.example.backend.model.Employee;
import com.example.backend.repository.EmployeeRepository;
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

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
    }

    @Test
    void getAllEmployees_ReturnsListOfEmployees() {
        // Arrange
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll(); // Vérifie que findAll a été appelé une fois
    }

    @Test
    void getEmployeeById_ExistingId_ReturnsEmployee() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        Employee result = employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_NonExistingId_ReturnsNull() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Employee result = employeeService.getEmployeeById(1L);

        // Assert
        assertNull(result);
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void createEmployee_ValidEmployee_ReturnsSavedEmployee() {
        // Arrange
        Employee employee = new Employee();
        employee.setNom("Test Nom");
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Act
        Employee result = employeeService.createEmployee(employee);

        // Assert
        assertNotNull(result);
        assertEquals("Test Nom", employee.getNom());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void updateEmployee_ExistingId_ReturnsUpdatedEmployee() {
        // Arrange
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        Employee updatedEmployee = new Employee();
        updatedEmployee.setNom("Updated Nom");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        // Act
        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Nom", result.getNom());
        assertEquals(1L, result.getId()); // L'ID doit être conservé
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(updatedEmployee);
    }

    @Test
    void updateEmployee_NonExistingId_ReturnsNull() {
        // Arrange
        Employee updatedEmployee = new Employee();
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        // Assert
        assertNull(result);
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).save(any()); // Vérifie que save n'est jamais appelé
    }

    @Test
    void deleteEmployee_ExistingId_ReturnsTrue() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L); // Ne rien faire quand deleteById est appelé

        // Act
        boolean result = employeeService.deleteEmployee(1L);

        // Assert
        assertTrue(result);
        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEmployee_NonExistingId_ReturnsFalse() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = employeeService.deleteEmployee(1L);

        // Assert
        assertFalse(result);
        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, never()).deleteById(any()); // Vérifie que deleteById n'est jamais appelé
    }
}