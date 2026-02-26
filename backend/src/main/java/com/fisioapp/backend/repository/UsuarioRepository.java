package com.fisioapp.backend.repository;

import com.fisioapp.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Le indica a Spring que esta interfaz maneja el acceso a la base de datos
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Este método lo creamos nosotros para poder buscar usuarios por su email
    Optional<Usuario> findByEmail(String email);
}