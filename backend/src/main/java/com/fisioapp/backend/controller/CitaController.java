package com.fisioapp.backend.controller;

import com.fisioapp.backend.model.Cita;
import com.fisioapp.backend.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //GET para todas las citas de un paciente en concreto
    @GetMapping("/usuario/{usuarioId}")
    public List<Cita> verCitas ( @PathVariable Long usuarioId){

        //pido la lista al servicio
        List<Cita> listaCitas = citaService.obtenerCitasDePaciente(usuarioId);
        //se devuelve la lista
        return ResponseEntity.ok(listaCitas).getBody();
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Cita>> verTodasLasCitas(){

        List<Cita> listaCompleta = citaService.obtenerTodas();

        return ResponseEntity.ok(listaCompleta);
    }

    // DELETE para cancelar una cita
    @DeleteMapping("/cancelar/{citaId}")
    public ResponseEntity<String> cancelarCita (@PathVariable Long citaId){

        boolean borrada = citaService.cancelarCita(citaId);

        if (borrada) {
            return ResponseEntity.ok("Cita cancelada correctamente.");
        }
        return ResponseEntity.status(404).body("Error: La cita no existe.");
    }

    @PutMapping("/estado/{citaId}")
    public ResponseEntity<Cita> actualizarEstadoCita (@PathVariable Long citaId, @RequestParam String estado){

        Cita citaActualizada = citaService.cambiarEstado (citaId, estado);

        if (citaActualizada != null){

            return ResponseEntity.ok(citaActualizada);
        }
        return ResponseEntity.notFound().build();
    }




























}
