package com.gabrielt.f21.model;

public class CuotaActualView {
    private String mensaje;
    private String creditosDisponibles;
    private String fechaVencimiento;

    // Constructor vacío
    public CuotaActualView() {
    }

    // Constructor completo
    public CuotaActualView(String mensaje, String creditosDisponibles, String fechaVencimiento) {
        this.mensaje = mensaje;
        this.creditosDisponibles = creditosDisponibles;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCreditosDisponibles() {
        return creditosDisponibles;
    }

    public void setCreditosDisponibles(String creditosDisponibles) {
        this.creditosDisponibles = creditosDisponibles;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "CuotaActualView{" +
                "mensaje='" + mensaje + '\'' +
                ", creditosDisponibles=" + creditosDisponibles +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                '}';
    }
}




