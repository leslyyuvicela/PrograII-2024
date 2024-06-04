package com.example.calculadora.Modelos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.type.DateTime;

@Entity
public class Pedidos {
    @PrimaryKey
    String id;
    String cliente;
    String direccionEntrega;
    String estado;
    DateTime fechaPedido;
    DateTime fechaEntregado;
    String mensajeCancelacion;
    Boolean actualizado = true;

    public Pedidos(Pedidos pedido){}
    public Pedidos(String id, String cliente, String direccionEntrega, String estado, DateTime fechaPedido, DateTime fechaEntregado, String mensajeCancelacion, Boolean actualizado) {
        this.id=id;
        this.cliente = cliente;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.fechaEntregado = fechaEntregado;
        this.mensajeCancelacion = mensajeCancelacion;
        this.actualizado=actualizado;
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

    public DateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(DateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public DateTime getFechaEntregado() {
        return fechaEntregado;
    }

    public void setFechaEntregado(DateTime fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
    }

    public String getMensajeCancelacion() {
        return mensajeCancelacion;
    }

    public void setMensajeCancelacion(String mensajeCancelacion) {
        this.mensajeCancelacion = mensajeCancelacion;
    }

    public Boolean getActualizado() {
        return actualizado;
    }

    public void setActualizado(Boolean actualizado) {
        this.actualizado = actualizado;
    }
}
