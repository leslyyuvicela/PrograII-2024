package com.example.calculadora.Modelos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Categorias {
    @PrimaryKey
    String id;
    String nombre;

    public Categorias(Categorias categoria) {
    }

    public Categorias(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
