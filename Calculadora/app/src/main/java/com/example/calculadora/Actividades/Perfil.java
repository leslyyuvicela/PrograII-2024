package com.example.calculadora.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.calculadora.R;
import com.example.calculadora.detectarInternet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends AppCompatActivity {
    ImageButton btnPrincipal, btnCarrito,btnChat;
    FirebaseFirestore fStore;
    detectarInternet di;
    FirebaseAuth fAuth;
    Button btnCerrarSesion,btnModificar;
    FirebaseUser usuario;
    EditText txtNombres, txtApellidos, txtCorreo, txtContraseña, txtDireccion, txtTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        di = new detectarInternet(getApplicationContext());
        fAuth = FirebaseAuth.getInstance();
        usuario = fAuth.getCurrentUser();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        fStore = FirebaseFirestore.getInstance();
        fStore.setFirestoreSettings(settings);
        txtCorreo=findViewById(R.id.txtCorreoReg);
        txtContraseña=findViewById(R.id.txtContraseñaReg);
        txtNombres=findViewById(R.id.txtNombres);
        txtApellidos=findViewById(R.id.txtApellidos);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtTelefono=findViewById(R.id.txtTelefono);
        btnCarrito=findViewById(R.id.btnCarrito);
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        btnChat=findViewById(R.id.btnChat);
        btnPrincipal=findViewById(R.id.btnPrincipal);
        btnModificar=findViewById(R.id.btnModificarDatos);
        obtenerUsuario();

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarInfoUsuario();
                finish();
            }
        });

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
    private void obtenerUsuario(){
        DocumentReference doc= fStore.collection("Usuarios").document(usuario.getUid());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snap) {
                txtCorreo.setText(usuario.getEmail());
                txtNombres.setText(snap.getString("nombres"));
                txtApellidos.setText(snap.getString("apellidos"));
                txtDireccion.setText(snap.getString("direccion"));
                txtTelefono.setText(snap.getString("telefono"));
            }
        });
    }
    private void actualizarInfoUsuario(){
            Map<String, Object> map = new HashMap<>();
            map.put("nombres", txtNombres.getText().toString().trim());
            map.put("apellidos", txtApellidos.getText().toString().trim());
            map.put("direccion", txtDireccion.getText().toString().trim());
            map.put("telefono", txtTelefono.getText().toString().trim());

            if(!txtContraseña.getText().toString().trim().isEmpty() && di.hayConexionInternet()){
                usuario.updatePassword(txtContraseña.getText().toString().trim());
            }
        fStore.collection("Usuarios").document(usuario.getUid()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                mostrarMsg("Producto guardado con exito!");
                regresarListaProductos();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mostrarMsg("Error al guardar");
            }
        });
    }
    private void cerrarSesión() {
        FirebaseAuth.getInstance().signOut();
        Intent abrirLogIn = new Intent(getApplicationContext(), LogIn.class);
        startActivity(abrirLogIn);
        finish();
    }
    private void regresarListaProductos(){
        Intent abrirVentana = new Intent(getApplicationContext(), Principal.class);
        startActivity(abrirVentana);
        finish();
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}