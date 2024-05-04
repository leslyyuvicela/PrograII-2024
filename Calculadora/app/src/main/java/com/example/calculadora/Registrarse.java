package com.example.calculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Registrarse extends AppCompatActivity {
EditText txtCorreo, txtContraseña, txtConfirmarContraseña;
Button btnRegistrarse;
FirebaseAuth mAuth;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);
        mAuth = FirebaseAuth.getInstance();
        txtCorreo=findViewById(R.id.txtCorreoReg);
        txtContraseña= findViewById(R.id.txtContraseñaReg);
        txtConfirmarContraseña= findViewById(R.id.txtConfirmarContraseña);
        btnRegistrarse= findViewById(R.id.btnRegistrarse);
        progressBar =findViewById(R.id.pbRegistrarse);

btnRegistrarse.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        String correo, contraseña, confirmarContraseña;
        correo =  txtCorreo.getText().toString();
        contraseña= txtContraseña.getText().toString();
        confirmarContraseña= txtConfirmarContraseña.getText().toString();
        if(correo.isEmpty() || contraseña.isEmpty()|| confirmarContraseña.isEmpty()){
            mostrarMsg("Hay campos vacíos");
            return;
        }
        if(confirmarContraseña.equals(contraseña)) {
            mAuth.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                mostrarMsg("Usuario registrado con éxito");
                                abrirPrincipal();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                               mostrarMsg("La autenticación falló");
                            }
                        }
                    });
        }
        else {mostrarMsg("Las contraseñas no coinciden");
            progressBar.setVisibility(View.GONE);
        }
    }
});
    }
    private void abrirPrincipal(){

        Intent abrirPrincipal = new Intent(getApplicationContext(), Principal.class);
        startActivity(abrirPrincipal);
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}