package com.fisioapp.backend.controller;

import com.fisioapp.backend.model.Cita;
import com.fisioapp.backend.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    //POST para crear una cita
    @PostMapping("/crear/{usuarioId}")
    public ResponseEntity<Cita> crearCita(@PathVariable Long usuarioId, @RequestBody Cita cita){

        //le pasamos ID y los datos
        Cita citaGuardada = citaService.crearCita(usuarioId, cita);

        if (citaGuardada != null){
            return ResponseEntity.ok(citaGuardada);
        }
        return ResponseEntity.badRequest().build();
    }
}
