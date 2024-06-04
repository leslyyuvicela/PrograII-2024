package com.example.calculadora.Modelos;

public class Pedido_Producto {
    String id;
    String idProducto;
    String idPedido;
    String cantidad;
    String precioUnidad;
    Boolean actualizado = true;

    public Pedido_Producto(Pedido_Producto pedido_producto){}
    public Pedido_Producto(String id, String idProducto, String idPedido, String cantidad, String precioUnidad, Boolean actualizado) {
        this.id = id;
        this.idProducto = idProducto;
        this.idPedido = idPedido;
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
        this.actualizado=actualizado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(String precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Boolean getActualizado() {
        return actualizado;
    }

    public void setActualizado(Boolean actualizado) {
        this.actualizado = actualizado;
    }
}
