package com.example.calculadora.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadora.Actividades.Principal;
import com.example.calculadora.Actividades.Registrarse;
import com.example.calculadora.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogIn extends AppCompatActivity {
    EditText txtCorreo, txtContraseña;
    TextView btnRegistrarse;
    Button  btnIniciarSesion;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;

    String correo, contraseña;
    Bundle parametros;



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
                correo =  txtCorreo.getText().toString();
                contraseña= txtContraseña.getText().toString();
                if(correo.isEmpty() || contraseña.isEmpty()){
                    mostrarMsg("El correo o la contraseña no deben estar vacíos");
                }
                else {
                    mAuth.signInWithEmailAndPassword(correo, contraseña).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    mostrarMsg("Bienvenido");
                                    abrirPrincipal();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mostrarMsg("Falló el inicio de sesión");
                                }
                            });
                }
                progressBar.setVisibility(View.GONE);
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








    private void obtenerRol(String uid){
        DocumentReference doc = fStore.collection("Usuarios").document(uid);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                parametros.putString("rol",documentSnapshot.getString("rol"));
            }
        });
    }
}