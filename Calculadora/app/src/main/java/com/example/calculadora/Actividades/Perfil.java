package com.example.calculadora.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.calculadora.R;
import com.google.firebase.auth.FirebaseAuth;

public class Perfil extends AppCompatActivity {
    ImageButton btnPrincipal, btnCarrito,btnChat;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        btnCarrito=findViewById(R.id.btnCarrito);
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        btnChat=findViewById(R.id.btnChat);
        btnPrincipal=findViewById(R.id.btnPrincipal);

        btnPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Principal.class);
                startActivity(i);
                finish();
            }
        });

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaPedidos.class);
                startActivity(i);
                finish();
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Chat.class);
                startActivity(i);
                finish();
            }
        });
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesión();
            }
        });
    }
    private void cerrarSesión() {
        FirebaseAuth.getInstance().signOut();
        Intent abrirLogIn = new Intent(getApplicationContext(), LogIn.class);
        startActivity(abrirLogIn);
        finish();
    }
}