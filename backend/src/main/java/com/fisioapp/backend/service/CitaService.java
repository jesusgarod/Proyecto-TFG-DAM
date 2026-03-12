package com.fisioapp.backend.service;

import com.fisioapp.backend.model.Cita;
import com.fisioapp.backend.model.Usuario;
import com.fisioapp.backend.repository.CitaRepository;
import com.fisioapp.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    //metodo para ver las citas de un paciente
    public List<Cita> obtenerCitasDePaciente (Long usuarioId){
        return citaRepository.findByPacienteId(usuarioId);
    }

    // metodo para cancelar una cita por ID
    public boolean cancelarCita (long citaId){

        if (citaRepository.existsById(citaId)){

            citaRepository.deleteById(citaId);
            return true;
        }
        return false;

    }

    public List <Cita> obtenerTodas(){

        return citaRepository.findAll();
    }

    public Cita cambiarEstado (Long citaId, String nuevoEstado){

        //busca la cita
        Optional<Cita> citaEncontrada = citaRepository.findById(citaId);

        if (citaEncontrada.isPresent()){
            Cita cita = citaEncontrada.get();

            // se le cambia el estado
            cita.setEstado(nuevoEstado);

            //la devolvemos actualizada
            return citaRepository.save(cita);
        }
        return null;
    }





























}
