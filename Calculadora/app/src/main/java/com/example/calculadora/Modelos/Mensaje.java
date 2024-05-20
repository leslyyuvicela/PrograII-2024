package com.example.calculadora.Modelos;

public class Mensaje {
    String id;
    String tipo;
    String enviadoPor;
    String enviadoA;
    String contenido;
    String fecha;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEnviadoPor() {
        return enviadoPor;
    }

    public void setEnviadoPor(String enviadoPor) {
        this.enviadoPor = enviadoPor;
    }

    public String getEnviadoA() {
        return enviadoA;
    }

    public void setEnviadoA(String enviadoA) {
        this.enviadoA = enviadoA;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Mensaje(Mensaje mensaje){}
    public Mensaje(String id, String tipo, String enviadoPor, String enviadoA, String contenido, String fecha) {
        this.id = id;
        this.tipo = tipo;
        this.enviadoPor = enviadoPor;
        this.enviadoA = enviadoA;
        this.contenido = contenido;
        this.fecha = fecha;
    }
}
