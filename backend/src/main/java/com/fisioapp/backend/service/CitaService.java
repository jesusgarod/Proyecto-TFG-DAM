package com.fisioapp.backend.service;

import com.fisioapp.backend.model.Cita;
import com.fisioapp.backend.model.Usuario;
import com.fisioapp.backend.repository.CitaRepository;
import com.fisioapp.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //metodo para crear una cita
    public Cita crearCita (Long usuarioId, Cita nuevaCita){

        //buscamos paciente por ID
        Usuario paciente = usuarioRepository.findById(usuarioId).orElse(null);

        //si el paciente existe lo asignamos a la cita
        if (paciente != null){
            nuevaCita.setPaciente(paciente);

            //guardamos la cita y la devolvemos
            return citaRepository.save(nuevaCita);
        }
        //si el paciente no existe devolvemos null
        return null;
    }
}
