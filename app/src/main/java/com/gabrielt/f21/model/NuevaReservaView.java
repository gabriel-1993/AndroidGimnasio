package com.gabrielt.f21.model;

import java.util.List;

//MODEL PARA MOSTRAR LA LISTA DE NUEVA RESERVA
public class NuevaReservaView {

    private String mensaje;
    //lista con todas las clases para reservar
    private List<Reserva> claseHorarios;

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Reserva> getClaseHorarios() {
        return claseHorarios;
    }

    public void setClaseHorarios(List<Reserva> claseHorarios) {
        this.claseHorarios = claseHorarios;
    }


    // Clase interna Reserva/Clase
    public static class Reserva {
        private int id;
        private String fechaClase;
        private String horaClase;
        private String diaClase;
        private int maxParticipantes;
        private int reservasHechas;
        private int lugaresDisponibles;
        private int claseId;
        private String descripcionClase;

        // Getters y Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFechaClase() {
            return fechaClase;
        }

        public void setFechaClase(String fechaClase) {
            this.fechaClase = fechaClase;
        }

        public String getHoraClase() {
            return horaClase;
        }

        public void setHoraClase(String horaClase) {
            this.horaClase = horaClase;
        }

        public String getDiaClase() {
            return diaClase;
        }

        public void setDiaClase(String diaClase) {
            this.diaClase = diaClase;
        }

        public int getMaxParticipantes() {
            return maxParticipantes;
        }

        public void setMaxParticipantes(int maxParticipantes) {
            this.maxParticipantes = maxParticipantes;
        }

        public int getReservasHechas() {
            return reservasHechas;
        }

        public void setReservasHechas(int reservasHechas) {
            this.reservasHechas = reservasHechas;
        }

        public int getLugaresDisponibles() {
            return lugaresDisponibles;
        }

        public void setLugaresDisponibles(int lugaresDisponibles) {
            this.lugaresDisponibles = lugaresDisponibles;
        }

        public int getClaseId() {
            return claseId;
        }

        public void setClaseId(int claseId) {
            this.claseId = claseId;
        }

        public String getDescripcionClase() {
            return descripcionClase;
        }

        public void setDescripcionClase(String descripcionClase) {
            this.descripcionClase = descripcionClase;
        }
    }
}
