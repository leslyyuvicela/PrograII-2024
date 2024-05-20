package com.example.calculadora.Modelos;

public class Producto {
    String id;
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




    public Producto(String _id, String codigo, String categoria, String nombre, String descripcion, String marca, Double precioCompra, Double margenGanancia, Double descuento, Double stock, String urlFoto) {
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
    }

    public Producto(Producto productos) {
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

    public Producto() {}

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
}