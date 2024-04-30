package com.example.calculadora;

import java.util.Base64;

public class utilidades {
    //static String urlConsulta = "http://192.168.84.52:5984/cristopher/_design/lesly/_view/lesly";
    static String urlConsulta = "http://192.168.110.60:5984/andrea/_design/rodolfo/_view/rodolfo";
    static String urlMto = "http://192.168.110.60:5984/andrea";
    static String user = "admin";
    static String passwd = "31416";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user +":"+ passwd).getBytes());
    public String generarIdUnico(){
        return java.util.UUID.randomUUID().toString();
    }
}