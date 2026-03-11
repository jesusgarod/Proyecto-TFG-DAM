package com.fisioapp.backend.model;

import jakarta.persistence.*;

@Entity
@Table (name = "citas")

public class Cita {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String fecha;
    private String hora;
    private String motivo;
    private String estado = "Pendiente";

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario paciente;

    public Cita() {
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
    }
}
