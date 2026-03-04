package com.fisioapp.backend.service;

import com.fisioapp.backend.model.Ejercicio;
import com.fisioapp.backend.model.Usuario;
import com.fisioapp.backend.repository.EjercicioRepository;
import com.fisioapp.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //aqui se le asigna un ejercicio a un paciente
    public Ejercicio asignarEjercicio (Long usuarioId, Ejercicio nuevoEjercicio){

        //busqueda del paciente
        Usuario paciente = usuarioRepository.findById(usuarioId).orElse(null);

        // si existe le asignamos el ejercicio
        if (paciente != null){
            nuevoEjercicio.setPaciente(paciente);
            return ejercicioRepository.save(nuevoEjercicio);
        }
        return null; // por si falla no devolver nada
    }

    // metodo para ver la tabla de ejercicios de un paciente
    public List<Ejercicio> obtenerEjerciciosDePaciente (Long usuarioId){
        return ejercicioRepository.findByPacienteId(usuarioId);
    }

    public Ejercicio actualizarEjercicio(Long id, Ejercicio detallesActualizados){

        //Buscamos si existe el ejercicio en la bbdd
        Ejercicio ejercicioExistente = ejercicioRepository.findById(id).orElse(null);

        if (ejercicioExistente != null){

            //si exise lo actualizamos los datos
            ejercicioExistente.setNombre(detallesActualizados.getNombre());
            ejercicioExistente.setDescripcion(detallesActualizados.getDescripcion());
            ejercicioExistente.setSeries(detallesActualizados.getSeries());
            ejercicioExistente.setRepeticiones(detallesActualizados.getRepeticiones());

            //guardo el ejercicio modificado
            return ejercicioRepository.save(ejercicioExistente);
        }
        return null; //si no encuentra el ejercicio
    }

    //metodo para borrar ejercicio
    public boolean eliminarEjercicio(Long id){

        //comprobamos si existe antes de borrarlo
        if (ejercicioRepository.existsById(id)){
            ejercicioRepository.deleteById(id);
            return true; // ejercicio borrado
        }
        return false; //no se encuentra el ejercicio
    }




















}
