package com.example.calculadora.Modelos;

import com.google.type.DateTime;

import java.sql.Timestamp;

public class Mensajes {
    String id;
    String tipo;
    Boolean enviadoPorUsuario;
    String usuario;
    String contenido;
    String fecha;
    Boolean  visto = false;
    Boolean actualizado = true;

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

    public Mensajes() {
    }

    public Boolean getEnviadoPorUsuario() {
        return enviadoPorUsuario;
    }

    public void setEnviadoPorUsuario(Boolean enviadoPor) {
        this.enviadoPorUsuario = enviadoPor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }


    public String  getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

    public Boolean getActualizado() {
        return actualizado;
    }

    public void setActualizado(Boolean actualizado) {
        this.actualizado = actualizado;
    }

    public Mensajes(Mensajes mensaje){}
    public Mensajes(String id, String tipo, Boolean enviadoPor, String enviadoA, String contenido, String fecha, Boolean visto, Boolean actualizado) {
        this.id = id;
        this.tipo = tipo;
        this.enviadoPorUsuario = enviadoPor;
        this.usuario = enviadoA;
        this.contenido = contenido;
        this.fecha = fecha;
        this.visto=visto;
        this.actualizado=actualizado;
    }
}
