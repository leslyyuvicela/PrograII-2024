package com.example.calculadora.Modelos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Productos {
    @PrimaryKey
            @NonNull
    String id = "";

    String codigo;
    String nombre;
    String descripcion;
    String marca;
    Double precioCompra;
    Double margenGanancia;
    Double descuento;
    String categoria;
    Double stock;
    String urlFoto;
    String fotoLocal = "";

Boolean actualizado = true;



    public Productos(String _id, String codigo, String categoria, String nombre, String descripcion, String marca, Double precioCompra, Double margenGanancia, Double descuento, Double stock, String urlFoto, String fotoLocal, Boolean actualizado) {
        this.id =_id;
        this.codigo = codigo;
        this.categoria=categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precioCompra = precioCompra;
        this.margenGanancia = margenGanancia;
        this.descuento=descuento;
        this.stock = stock;
        this.urlFoto = urlFoto;
        this.fotoLocal=fotoLocal;
        this.actualizado=actualizado;
    }

    public Productos(Productos productos) {
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setIdProducto(String idProducto) {
        this.codigo = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String direccion) {
        this.descripcion = direccion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String telefono) {
        this.marca = telefono;
    }


    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getMargenGanancia() {
        return margenGanancia;
    }

    public void setMargenGanancia(Double margenGanancia) {
        this.margenGanancia = margenGanancia;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Productos() {}

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Boolean getActualizado() {
        return actualizado;
    }

    public void setActualizado(Boolean actualizado) {
        this.actualizado = actualizado;
    }

    public String getFotoLocal() {
        return fotoLocal;
    }

    public void setFotoLocal(String fotoLocal) {
        this.fotoLocal = fotoLocal;
    }
}