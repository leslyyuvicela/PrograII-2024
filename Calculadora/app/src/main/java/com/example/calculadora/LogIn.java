package com.example.calculadora;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    EditText txtCorreo, txtContraseña;
    Button btnRegistrarse, btnIniciarSesion;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
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
        setContentView(R.layout.log_in);


        mAuth = FirebaseAuth.getInstance();
        txtCorreo=findViewById(R.id.txtCorreoLog);
        txtContraseña= findViewById(R.id.txtContraseñaLog);
        btnRegistrarse= findViewById(R.id.btnRegistrarse);
        btnIniciarSesion=findViewById(R.id.btnIngresar);
        progressBar =findViewById(R.id.pbLogIn);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String correo, contraseña, confirmarContraseña;
                correo =  txtCorreo.getText().toString();
                contraseña= txtContraseña.getText().toString();
                if(correo.isEmpty() || contraseña.isEmpty()){
                    mostrarMsg("El correo o la contraseña no deben estar vacíos");
                    return;
                }
                mAuth.signInWithEmailAndPassword(correo, contraseña)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    mostrarMsg("Bienvenido");
                                    abrirPrincipal();

                                } else {
                                    mostrarMsg("Falló el inicio de sesión");
                                }
                            }
                        });
            }
        });
btnRegistrarse.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        abrirRegistrarse();
    }
});
    }

    private void abrirPrincipal(){

        Intent abrirPrincipal = new Intent(getApplicationContext(), Principal.class);
        startActivity(abrirPrincipal);
        finish();
    }
    private void abrirRegistrarse(){

        Intent abrirRegistrarse = new Intent(getApplicationContext(), Registrarse.class);
        startActivity(abrirRegistrarse);
        finish();
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}