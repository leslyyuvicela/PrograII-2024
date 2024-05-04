package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class inicio extends AppCompatActivity {

    Button btnLogIn, btnRegistrarse, btnExplorar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);


        // Codigo de los botones
        btnExplorar = findViewById(R.id.btnexplorar);
        btnLogIn = findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistrarse();
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLogIn();
            }
        });
        btnExplorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInicio();
            }
        });

    }


    private void abrirLogIn(){
        Intent abrirLogIn = new Intent(getApplicationContext(), LogIn.class);
        startActivity(abrirLogIn);
    }
    private void abrirRegistrarse(){
        Intent abrirRegistrarse = new Intent(getApplicationContext(), Registrarse.class);
        startActivity(abrirRegistrarse);
    }

    private void abrirInicio(){}
}