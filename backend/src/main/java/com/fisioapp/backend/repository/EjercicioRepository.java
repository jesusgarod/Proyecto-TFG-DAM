package com.fisioapp.backend.repository;

import com.fisioapp.backend.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

    // para buscar toda la tabla de ejercicios de un paciente
    List<Ejercicio> findByPacienteId(Long pacienteId);
}