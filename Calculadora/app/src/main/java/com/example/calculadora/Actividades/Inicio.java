package com.example.calculadora.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calculadora.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio extends AppCompatActivity {

    Button btnLogIn, btnRegistrarse, btnExplorar;
    FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            abrirPrincipal();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        mAuth = FirebaseAuth.getInstance();

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
                abrirPrincipal();
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

    private void abrirPrincipal(){
        Intent abrirprincipal = new Intent(getApplicationContext(), Principal.class);
        startActivity(abrirprincipal);
finish();
    }
}