package com.gabrielt.f21.model;

public class CancelarReservaView {

    private String mensaje;
    private Reserva reserva;
    private Usuario usuario;

    // Getters y setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Clase interna para la reserva
    public static class Reserva {
        private int id;
        private int usuarioId;
        private int claseHorarioId;
        private int estadoReservaId;

        // Getters y setters
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

        public int getEstadoReservaId() {
            return estadoReservaId;
        }

        public void setEstadoReservaId(int estadoReservaId) {
            this.estadoReservaId = estadoReservaId;
        }
    }

    // Clase interna para el usuario
    public static class Usuario {
        private String nombre;
        private String apellido;
        private int creditosDisponibles;
        private boolean deudaClase;

        // Getters y setters
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
    }
}

