package com.example.calculadora.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.calculadora.Actividades.Principal;
import com.example.calculadora.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.Utils;
import com.google.type.DateTime;

import java.util.HashMap;
import java.util.Map;

public class Registrarse extends AppCompatActivity {
EditText txtCorreo, txtContraseña, txtConfirmarContraseña,txtNombres, txtApellidos, txtTelefono, txtDireccion;
Button btnRegistrarse;
FirebaseAuth mAuth;
FirebaseFirestore fStore;
ProgressBar progressBar;

    String correo, contraseña, confirmarContraseña,nombres, apellidos, telefono, direccion;
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
        txtNombres=findViewById(R.id.txtNombres);
        txtApellidos=findViewById(R.id.txtApellidos);
        txtTelefono=findViewById(R.id.txtTelefono);
        txtDireccion=findViewById(R.id.txtDireccion);

btnRegistrarse.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        fStore = FirebaseFirestore.getInstance();
        nombres=txtNombres.getText().toString();
        apellidos=txtApellidos.getText().toString();
        telefono=txtTelefono.getText().toString();
        direccion=txtDireccion.getText().toString();
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
                                FirebaseUser usuario = mAuth.getCurrentUser();
                                DocumentReference doc = fStore.collection("Usuarios").document(usuario.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("nombres", nombres);
                                userInfo.put("apellidos", apellidos);
                                userInfo.put("telefono", telefono);
                                userInfo.put("direccion", direccion);
                                userInfo.put("rol", "cliente");
                                doc.set(userInfo);
                                mostrarMsg("Usuario registrado con éxito");
                                darBienvenida(usuario.getUid());
                                abrirPrincipal();
                            } else {
                                // If sign in fails, display a message to the user.
                               mostrarMsg("Hubo un error al registrar el usuario");
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

    private void darBienvenida(String id) {
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("tipo", "texto");
        mensaje.put("contenido", "Bienvenido a Techno Store");
        mensaje.put("enviadoPor", "Techno Store");
        mensaje.put("enviadoA", id);
        mensaje.put("fecha", Timestamp.now());
        mensaje.put("visto", false);
        fStore.collection("mensajes").add(mensaje);
    }

    private void abrirPrincipal(){

        Intent abrirPrincipal = new Intent(getApplicationContext(), Principal.class);
        startActivity(abrirPrincipal);
        finish();
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}