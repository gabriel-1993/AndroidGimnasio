package com.gabrielt.f21.model;

import java.util.List;

public class MisCreditosView {

    private String mensaje; // Mensaje de la respuesta
    private List<Cuota> cuotas; // Array de cuotas

    // Getters y setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    // Clase interna que representa cada objeto del array "cuotas"
    public static class Cuota {
        private int id;
        private int usuarioId;
        private String fechaPago;
        private String fechaVencimiento;
        private boolean estado;
        private Integer planId;
        private Integer planCantidadCreditos;
        private Double planPrecio;
        private Boolean planEstado;
        private int creditosUsados;

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

        public String getFechaPago() {
            return fechaPago;
        }

        public void setFechaPago(String fechaPago) {
            this.fechaPago = fechaPago;
        }

        public String getFechaVencimiento() {
            return fechaVencimiento;
        }

        public void setFechaVencimiento(String fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
        }

        public boolean isEstado() {
            return estado;
        }

        public void setEstado(boolean estado) {
            this.estado = estado;
        }

        public Integer getPlanId() {
            return planId;
        }

        public void setPlanId(Integer planId) {
            this.planId = planId;
        }

        public Integer getPlanCantidadCreditos() {
            return planCantidadCreditos;
        }

        public void setPlanCantidadCreditos(Integer planCantidadCreditos) {
            this.planCantidadCreditos = planCantidadCreditos;
        }

        public Double getPlanPrecio() {
            return planPrecio;
        }

        public void setPlanPrecio(Double planPrecio) {
            this.planPrecio = planPrecio;
        }

        public Boolean getPlanEstado() {
            return planEstado;
        }

        public void setPlanEstado(Boolean planEstado) {
            this.planEstado = planEstado;
        }

        public int getCreditosUsados() {
            return creditosUsados;
        }

        public void setCreditosUsados(int creditosUsados) {
            this.creditosUsados = creditosUsados;
        }
    }
}