package com.gabrielt.f21.model;

import java.util.List;


public class MisReservasView {

    private String mensaje;
    private List<Reserva> reservas;

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public class Reserva {
        private int id;
        private String fechaReserva;
        private int estadoReservaId;
        private String estadoReserva;
        private int claseHorarioId;
        private String fechaClase;
        private Hora horaClase; // Cambio aquí
        private String tipoDeClaseDescripcion;

        // Getters y Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getEstadoReserva() {
            return estadoReserva;
        }

        public void setEstadoReserva(String estadoReserva) {
            this.estadoReserva = estadoReserva;
        }

        public int getClaseHorarioId() {
            return claseHorarioId;
        }

        public void setClaseHorarioId(int claseHorarioId) {
            this.claseHorarioId = claseHorarioId;
        }

        public String getFechaClase() {
            return fechaClase;
        }

        public void setFechaClase(String fechaClase) {
            this.fechaClase = fechaClase;
        }

        public Hora getHoraClase() {
            return horaClase;
        }

        public void setHoraClase(Hora horaClase) {
            this.horaClase = horaClase;
        }

        public String getTipoDeClaseDescripcion() {
            return tipoDeClaseDescripcion;
        }

        public void setTipoDeClaseDescripcion(String tipoDeClaseDescripcion) {
            this.tipoDeClaseDescripcion = tipoDeClaseDescripcion;
        }
    }

    public class Hora {
        private int id;
        private String hora;
        private String dia;
        private boolean estado;
        private int maxParticipantes;
        private List<?> clasesHorarios; // permitir null

        // Getters y Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            this.dia = dia;
        }

        public boolean isEstado() {
            return estado;
        }

        public void setEstado(boolean estado) {
            this.estado = estado;
        }

        public int getMaxParticipantes() {
            return maxParticipantes;
        }

        public void setMaxParticipantes(int maxParticipantes) {
            this.maxParticipantes = maxParticipantes;
        }

        public List<?> getClasesHorarios() {
            return clasesHorarios;
        }

        public void setClasesHorarios(List<?> clasesHorarios) {
            this.clasesHorarios = clasesHorarios;
        }
    }
}
