package com.example.calculadora.Modelos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Anuncios {
@PrimaryKey
    String id;
    String url;
    String urlLocal="";

    public Anuncios(Anuncios anuncio) {}

    public Anuncios(String id, String url, String urlLocal) {
        this.id = id;
        this.url = url;
        this.urlLocal = urlLocal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlLocal() {
        return urlLocal;
    }

    public void setUrlLocal(String urlLocal) {
        this.urlLocal = urlLocal;
    }

}
