package com.example.backend.repository;

import com.example.backend.model.HeuresSup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeuresSupRepository extends JpaRepository<HeuresSup, Long> {

    List<HeuresSup> findByEmployeId(Long employeId);
}