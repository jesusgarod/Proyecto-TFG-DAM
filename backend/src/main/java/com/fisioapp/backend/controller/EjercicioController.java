package com.fisioapp.backend.controller;

import com.fisioapp.backend.model.Ejercicio;
import com.fisioapp.backend.service.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")

public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    // POST para mandarle el ejercicio nuevo al paciente
    @PostMapping("/asignar/{usuarioId}")
    public ResponseEntity<Ejercicio> asignarEjercicio (@PathVariable Long usuarioId,@RequestBody Ejercicio ejercicio){
        Ejercicio guardado = ejercicioService.asignarEjercicio(usuarioId,ejercicio);

        if (guardado != null){
            return ResponseEntity.ok(guardado); //deberia dar 200 OK
        }
        return ResponseEntity.badRequest().build(); // error 400 si no existe paciente
    }

    //GET para ver los ejercicios de un paciente
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ejercicio>> verEjercicios(@PathVariable Long usuarioId){
        List<Ejercicio> lista = ejercicioService.obtenerEjerciciosDePaciente(usuarioId);
        return ResponseEntity.ok(lista);
    }

    //PUT para modificar un ejercicio
    @PutMapping("/{id}")
    public ResponseEntity<Ejercicio> actualizarEjercicio (@PathVariable Long id,@RequestBody Ejercicio detalles){
        Ejercicio actualizado = ejercicioService.actualizarEjercicio(id, detalles);

        if (actualizado != null){
            return ResponseEntity.ok(actualizado); // 200 OK
        }
        return ResponseEntity.notFound().build(); // error 404 si no existe el ejercicio
    }

    // DELETE para borrar un ejercicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEjercicio (@PathVariable Long id){

        boolean borrado = ejercicioService.eliminarEjercicio(id);

        if (borrado){
            return ResponseEntity.ok().build(); // 200 OK, borrado
        }
        return ResponseEntity.notFound().build(); // error 404 si no existe

    }














}
