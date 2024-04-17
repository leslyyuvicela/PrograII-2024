package com.example.calculadora;

import java.util.Base64;

public class utilidades {
    //static String urlConsulta = "http://192.26.152:5984/database/tienda_online/_design/tienda_online/_view/tienda_online";
    static String urlConsulta = "http://192.168.0.20:5984/peliculas/_design/peliculas/_view/peliculas";
    static String urlMto = "http://192.168.0.20:5984/peliculas";
    static String user = "admin";
    static String passwd = "123456";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user +":"+ passwd).getBytes());
    public String generarIdUnico(){
        return java.util.UUID.randomUUID().toString();
    }
}
