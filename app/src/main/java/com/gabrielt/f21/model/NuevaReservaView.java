package com.gabrielt.f21.model;

import java.util.Date;

public class NuevaReservaView {

    //clase_horario id
     private int id;

    //clase descripcion: funcional,zumba,etc
    private String descripcion;

    private int max_participantes;
    private String dia;
    private String hora;
    private String fecha;
    private boolean estado;
    private boolean cerrado;

    public NuevaReservaView(int id, String descripcion, int max_participantes, String dia, String hora, String fecha, boolean estado, boolean cerrado) {
        this.id = id;
        this.descripcion = descripcion;
        this.max_participantes = max_participantes;
        this.dia = dia;
        this.hora = hora;
        this.fecha = fecha;
        this.estado = estado;
        this.cerrado = cerrado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMax_participantes() {
        return max_participantes;
    }

    public void setMax_participantes(int max_participantes) {
        this.max_participantes = max_participantes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isCerrado() {
        return cerrado;
    }

    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }
}
