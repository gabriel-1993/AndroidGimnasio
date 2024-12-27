package com.gabrielt.f21.model;

public class NovedadView {


        private int id;
        private String titulo;
        private String descripcion;
        private String url_Imagen;
        private String fechaCreacion;
        private String fechaActualizacion;

        // Getters y Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

    public String getUrl_Imagen() {
        return url_Imagen;
    }

    public void setUrl_Imagen(String url_Imagen) {
        this.url_Imagen = url_Imagen;
    }

    public String getFechaCreacion() {
            return fechaCreacion;
        }

        public void setFechaCreacion(String fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
        }

        public String getFechaActualizacion() {
            return fechaActualizacion;
        }

        public void setFechaActualizacion(String fechaActualizacion) {
            this.fechaActualizacion = fechaActualizacion;
        }
}
