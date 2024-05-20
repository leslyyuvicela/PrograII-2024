package com.example.calculadora.Modelos;

public class Pedido {
    String id;
    String cliente;
    String direccionEntrega;
    String estado;
    String fechaPedido;
    String fechaEntregado;
    String mensajeCancelacion;

    public Pedido(Pedido pedido){}
    public Pedido(String id,String cliente, String direccionEntrega, String estado, String fechaPedido, String fechaEntregado, String mensajeCancelacion) {
        this.id=id;
        this.cliente = cliente;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.fechaEntregado = fechaEntregado;
        this.mensajeCancelacion = mensajeCancelacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getFechaEntregado() {
        return fechaEntregado;
    }

    public void setFechaEntregado(String fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
    }

    public String getMensajeCancelacion() {
        return mensajeCancelacion;
    }

    public void setMensajeCancelacion(String mensajeCancelacion) {
        this.mensajeCancelacion = mensajeCancelacion;
    }
}
