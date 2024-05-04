package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Principal extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;

    String  correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        user = auth.getCurrentUser();

        if(user==null){
            abrirLogIn();
        }
        else{
            correo=user.getEmail();
        }

    }
    private void salir(){
        FirebaseAuth.getInstance().signOut();
        abrirLogIn();
    }
    private void abrirLogIn(){
        Intent abrirLogIn = new Intent(getApplicationContext(), LogIn.class);
        startActivity(abrirLogIn);
        finish();
    }
}