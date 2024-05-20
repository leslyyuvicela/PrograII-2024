package com.example.calculadora.Modelos;

import com.google.type.DateTime;

public class Mensaje {
    String id;
    String tipo;
    String enviadoPor;
    String enviadoA;
    String contenido;
    DateTime fecha;
    Boolean  visto;

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


    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

    public Mensaje(Mensaje mensaje){}
    public Mensaje(String id, String tipo, String enviadoPor, String enviadoA, String contenido, DateTime fecha, Boolean visto) {
        this.id = id;
        this.tipo = tipo;
        this.enviadoPor = enviadoPor;
        this.enviadoA = enviadoA;
        this.contenido = contenido;
        this.fecha = fecha;
        this.visto=visto;
    }
}
