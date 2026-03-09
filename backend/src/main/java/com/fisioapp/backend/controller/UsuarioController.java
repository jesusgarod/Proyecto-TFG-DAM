package com.fisioapp.backend.controller;


import com.fisioapp.backend.config.JwtUtil;
import com.fisioapp.backend.dto.LoginRequestDTO;
import com.fisioapp.backend.dto.LoginResponseDTO;
import com.fisioapp.backend.model.Usuario;
import com.fisioapp.backend.repository.UsuarioRepository;
import com.fisioapp.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //para decir que esta clase es una API y que devuelve datos JSON
@RequestMapping("/api/usuarios") // dir URL local para entrar localhost:8080/api/usuarios

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil; //aqui se crean los tokens

    // metodo que se activa cuando enviamos datos por POST (registro)
    @PostMapping("/registro")
    public Usuario registrar(@RequestBody Usuario usuario) {
        // le pido al servicio que registre al usuario
        return usuarioService.registrarUsuario(usuario);
    }

    // este metodo se activca con GET (para ver todos los usuarios)
    @GetMapping("/todos")
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {

        // Pedimos al servicio que intente hacer el login
        Usuario usuarioValidado = usuarioService.login(loginRequest.getEmail(), loginRequest.getPassword());

        // Si nos devuelve un usuario, todo ha ido bien
        if (usuarioValidado != null) {
            // 1. Fabricamos la pulsera (Token JWT) usando su email
            String tokenGenerado = jwtUtil.generarToken(usuarioValidado.getEmail());

            // 2. La metemos en su cajita DTO
            LoginResponseDTO respuesta = new com.fisioapp.backend.dto.LoginResponseDTO(tokenGenerado);

            // 3. Se la enviamos al usuario con un 200 OK
            return ResponseEntity.ok(respuesta);
        }

        // Si nos devuelve null, devolvemos error 401 (No autorizado)
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/perfil")
    public ResponseEntity<Usuario> obtenerMiPerfil(Authentication authentication) {

//        guardamos el email del paciente
        String emailPaciente = authentication.getName();

        // se usa el repositorio para buscar el paciente por el mail introducido
        Usuario paciente = usuarioRepository.findByEmail(emailPaciente).get();

        // se devuelven los datos del paciente
        return ResponseEntity.ok(paciente);

    }

    // GET para obtener los datos del perfil de usuario
    @GetMapping("/{id}")
    public ResponseEntity<Usuario>  obtenerPerfil (@PathVariable Long id){

        //Buscamos al user en la base de datos
        Usuario usuarioEncontrado = usuarioRepository.findById(id).orElse(null);

        if (usuarioEncontrado != null){
            return ResponseEntity.ok(usuarioEncontrado);
        }
        return ResponseEntity.notFound().build();
    }
}
