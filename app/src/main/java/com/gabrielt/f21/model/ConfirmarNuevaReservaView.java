package com.gabrielt.f21.model;

import java.util.List;

import java.util.Date;
public class ConfirmarNuevaReservaView {

    // Atributos para la reserva
    private int id;
    private int usuarioId;
    private int claseHorarioId;
    private String fechaReserva;  // Formato "yyyy-MM-dd HH:mm:ss"
    private int estadoReservaId;

    // Atributos del usuario
    private String nombre;
    private String apellido;
    private int creditosDisponibles;
    private boolean deudaClase;

    // Atributo adicional para el mensaje de estado
    private String mensaje;

    // Constructor
    public ConfirmarNuevaReservaView(int id, int usuarioId, int claseHorarioId, String fechaReserva,
                                     int estadoReservaId, String nombre, String apellido,
                                     int creditosDisponibles, boolean deudaClase, String mensaje) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.claseHorarioId = claseHorarioId;
        this.fechaReserva = fechaReserva;
        this.estadoReservaId = estadoReservaId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.creditosDisponibles = creditosDisponibles;
        this.deudaClase = deudaClase;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getClaseHorarioId() {
        return claseHorarioId;
    }

    public void setClaseHorarioId(int claseHorarioId) {
        this.claseHorarioId = claseHorarioId;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getEstadoReservaId() {
        return estadoReservaId;
    }

    public void setEstadoReservaId(int estadoReservaId) {
        this.estadoReservaId = estadoReservaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getCreditosDisponibles() {
        return creditosDisponibles;
    }

    public void setCreditosDisponibles(int creditosDisponibles) {
        this.creditosDisponibles = creditosDisponibles;
    }

    public boolean isDeudaClase() {
        return deudaClase;
    }

    public void setDeudaClase(boolean deudaClase) {
        this.deudaClase = deudaClase;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // Método toString actualizado para incluir el mensaje
    @Override
    public String toString() {
        return "ConfirmarNuevaReservaView{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", claseHorarioId=" + claseHorarioId +
                ", fechaReserva='" + fechaReserva + '\'' +
                ", estadoReservaId=" + estadoReservaId +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", creditosDisponibles=" + creditosDisponibles +
                ", deudaClase=" + deudaClase +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
