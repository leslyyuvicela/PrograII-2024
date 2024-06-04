package com.example.calculadora.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.calculadora.Adaptadores.Firestore.AdMensajesFirestore;
import com.example.calculadora.Modelos.Mensajes;
import com.example.calculadora.R;
import com.example.calculadora.detectarInternet;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


public class Chat extends AppCompatActivity {
    Query query;
    AdMensajesFirestore adMensajesFirestore;
    detectarInternet di;
    ImageButton btnListaPedidos, btnPrincipal;
    FirebaseFirestore fStore;
    RecyclerView rvChat;
    EditText txtEscribirMensaje;
    ImageButton btnFoto, btnEnviar;
    FirebaseAuth fAuth;

    FirebaseUser usuario;
    String rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        di = new detectarInternet(getApplicationContext());
        fAuth=FirebaseAuth.getInstance();
        usuario=fAuth.getCurrentUser();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        fStore = FirebaseFirestore.getInstance();
        fStore.setFirestoreSettings(settings);
        obtenerRol();
        btnListaPedidos= findViewById(R.id.btnCarrito);
        btnPrincipal=findViewById(R.id.btnPrincipal);
        btnEnviar= findViewById(R.id.btnEnviar);
        btnFoto = findViewById(R.id.btnEnviarArchivo);
        txtEscribirMensaje=findViewById(R.id.txtEscribirMsg);
rvChat=findViewById(R.id.rvMensajes);
rvChat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mostrarMensajes();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje(txtEscribirMensaje.getText().toString(),"texto");
                mostrarMensajes();
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
        btnListaPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaPedidos.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void enviarMensaje(String contenido, String tipo) {


        Map<String, Object> map = new HashMap<>();
        map.put("usuario", usuario.getUid());
        map.put("contenido", contenido);
        map.put("fecha", Timestamp.now());
        map.put("tipo", tipo);
        map.put("visto", false);
        map.put("enviadoPorUsuario",rol.equals("cliente"));

        fStore.collection("mensajes").add(map);
    }

    private void obtenerRol() {
        DocumentReference doc;
        if (usuario != null) {
            doc = fStore.collection("Usuarios").document(usuario.getUid());
            doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    establecerRol(documentSnapshot.getString("rol"));
                }
            });
        }
}

    @Override
    protected void onStart() {
        super.onStart();
      //  adMensajesFirestore.startListening();
        mostrarMensajes();
    }

    private void mostrarMensajes() {
        if (di.hayConexionInternet()) {
            //sincronizarDatos();
           // obtenerMensajesFirestore();
        } else {
            obtenerMensajesSQLite();
        }
    }

    private void obtenerMensajesSQLite() {

    }

    @Override
    protected void onStop() {
        super.onStop();
       // adMensajesFirestore.stopListening();
    }

    private void obtenerMensajesFirestore() {
        query = fStore.collection("mensajes");
        FirestoreRecyclerOptions<Mensajes> fOptions = new FirestoreRecyclerOptions.Builder<Mensajes>()
                .setQuery(query,Mensajes.class).build();
        adMensajesFirestore = new AdMensajesFirestore(fOptions, this);
        adMensajesFirestore.startListening();
        rvChat.setAdapter(adMensajesFirestore);
    }
    private void mostrarMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void establecerRol(String rol) {
        this.rol=rol;
    }
}
