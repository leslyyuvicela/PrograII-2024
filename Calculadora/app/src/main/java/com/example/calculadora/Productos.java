package com.example.calculadora;

public class Productos {
    String _id;
    String codigoProducto;
    String nombre;
    String descripcion;
    String marca;
    Double costo;
    Double ganancia;
    Double descuento;
    String categoria;
    Double stock;
    String foto;
    Boolean actualizado;




    public Productos(String _id, String codigoProducto, String categoria, String nombre, String descripcion, String marca, Double costo, Double ganancia, Double descuento, Double stock, String foto, Boolean actualizado) {
        this._id=_id;
        this.codigoProducto = codigoProducto;
        this.categoria=categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.costo = costo;
        this.ganancia = ganancia;
        this.descuento=descuento;
        this.stock = stock;
        this.foto = foto;
        this.actualizado = actualizado;
    }


    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setIdProducto(String idProducto) {
        this.codigoProducto = idProducto;
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


    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getGanancia() {
        return ganancia;
    }

    public void setGanancia(Double ganancia) {
        this.ganancia = ganancia;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }
    public Boolean getActualizado() {
        return actualizado;
    }

    public void setActualizado(Boolean actualizado) {
        this.actualizado = actualizado;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}