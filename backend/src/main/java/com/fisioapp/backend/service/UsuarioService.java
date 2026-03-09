package com.fisioapp.backend.service;

import com.fisioapp.backend.model.Usuario;
import com.fisioapp.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired //conecta el repositorio directamennnte
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //inyectamos el cifrado

    //Metodo para registrar nuevo usuario
    public Usuario registrarUsuario (Usuario usuario){

        //Cogemos la contraseña y la ciframos
        String passwordCifrada = passwordEncoder.encode(usuario.getPassword());

        // Se la ponemos del nuevo al user ya cifrada
        usuario.setPassword(passwordCifrada);

        usuario.setRol("PACIENTE");

        //Guardamos en la bbdd
        return usuarioRepository.save(usuario);
    }

    //Metodo para listar los usuarios, para pruebas
    public List<Usuario> obtenerTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario login ( String email, String passwordPlana){
        // buscamos en la bbdd si existe alguien con ese mail
        //usamos Optional porque puede que el usuario no exista
        Usuario usuarioBaseDatos = usuarioRepository.findByEmail(email).orElse(null);

        if (usuarioBaseDatos != null){

            boolean coinciden = passwordEncoder.matches(passwordPlana,usuarioBaseDatos.getPassword());

            if (coinciden){
                return usuarioBaseDatos; //si coincide
            }
        }
        return null; // si no coincide el user o la contraseña
    }



}
