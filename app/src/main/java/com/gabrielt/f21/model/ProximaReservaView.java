package com.gabrielt.f21.model;

public class ProximaReservaView {

        private String msj;
        private int reservaId;
        private String fechaReserva;  // Formato ISO: "2024-12-17T20:00:00"
        private String fechaClase;    // Formato: "2024-12-17"
        private String horaClase;     // Formato: "21:00"
        private String estadoReserva;
        private int claseId;

        // Constructor vacío (requerido para la deserialización)
        public ProximaReservaView() {}

        // Getters y Setters
        public int getReservaId() {
            return reservaId;
        }

        public void setReservaId(int reservaId) {
            this.reservaId = reservaId;
        }

        public String getFechaReserva() {
            return fechaReserva;
        }

        public void setFechaReserva(String fechaReserva) {
            this.fechaReserva = fechaReserva;
        }

        public String getFechaClase() {
            return fechaClase;
        }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
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

        public String getEstadoReserva() {
            return estadoReserva;
        }

        public void setEstadoReserva(String estadoReserva) {
            this.estadoReserva = estadoReserva;
        }

        public int getClaseId() {
            return claseId;
        }

        public void setClaseId(int claseId) {
            this.claseId = claseId;
        }

    @Override
    public String toString() {
        return
                fechaClase +"\n"+  horaClase ;
    }
}
