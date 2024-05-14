package com.example.calculadora.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.calculadora.Adaptadores.AdaptadorProductos;
import com.example.calculadora.Modelos.Productos;
import com.example.calculadora.R;
import com.example.calculadora.detectarInternet;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser usuario;
    FirebaseFirestore fStore;
    DocumentReference doc;
    CollectionReference colProductos;
    FloatingActionButton btnAgregarProducto;
    ImageButton btnBuscar, btnCarrito, btnChat, btnPerfil;
    detectarInternet di;
    EditText txtBuscar;
    Query query;
   RecyclerView rvDescuentos;
   AdaptadorProductos adProductos;
    String  rol = "", campo="nombre", filtro="";

Productos productos;
    final ArrayList<Productos> alProductos=new ArrayList<Productos>();



    @Override
    protected void onStart() {
        super.onStart();
        adProductos.startListening();
        mostrarProductos(campo,filtro);
    }
    @Override
    protected void onStop() {
        super.onStop();
adProductos.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        campo = "nombre";
        filtro="";
        fStore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        txtBuscar=findViewById(R.id.txtBuscar);
        btnCarrito=findViewById(R.id.btnCarrito);
        btnChat=findViewById(R.id.btnChat);
        btnAgregarProducto=findViewById(R.id.btnAgregarProducto);
        btnPerfil=findViewById(R.id.btnPerfil);
        btnAgregarProducto.setVisibility(View.GONE);
        btnBuscar= findViewById(R.id.btnBuscar);
        //grdDescuentos=findViewById(R.id.grdDescuentos);
        rvDescuentos=findViewById(R.id.rvDescuentos);
        rvDescuentos.setLayoutManager(new LinearLayoutManager(this));
        try {
            usuario = auth.getCurrentUser();
        }catch (Exception e){
            mostrarMsg(e.getMessage().toString());
        }
               if(usuario!=null) {

                   doc = fStore.collection("Usuarios").document(usuario.getUid());
                   doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                       @Override
                       public void onSuccess(DocumentSnapshot documentSnapshot) {
                           rol = documentSnapshot.getString("rol");
                           configurarBotonAñadir(rol);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           mostrarMsg(e.getMessage());
                       }
                   });
               }
               else{
                   rol="invitado";
               }


        mostrarProductos(campo,filtro);

               btnBuscar.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       filtro = txtBuscar.getText().toString();
                       mostrarProductos(campo,filtro);
                   }
               });

               btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       agregarProducto();
                   }
               });
               btnCarrito.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent i = new Intent(getApplicationContext(), ListaPedidos.class);
                       startActivity(i);
                   }
               });
               btnChat.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent chat = new Intent(getApplicationContext(), Chat.class);
                       startActivity(chat);
                   }
               });

btnPerfil.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        cerrarSesión();
    }
});
    }

    private void agregarProducto() {
        Intent agregarProducto = new Intent(getApplicationContext(), Agregar_Producto.class);
        agregarProducto.putExtra("idProducto","");
        startActivity(agregarProducto);
    }

    private void mostrarProductos(String campo, String filtro) {
        query = fStore.collection("productos");
        FirestoreRecyclerOptions<Productos> fOptions = new FirestoreRecyclerOptions.Builder<Productos>()
                .setQuery(query, Productos.class).build();
        adProductos = new AdaptadorProductos(fOptions,this,campo,filtro);
        adProductos.startListening();
        rvDescuentos.setAdapter(adProductos);
    }

    private void cerrarSesión(){
        FirebaseAuth.getInstance().signOut();
        Intent abrirLogIn = new Intent(getApplicationContext(), LogIn.class);
        startActivity(abrirLogIn);
        finish();
    }

    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    private int elementosListados(int count){
        for (int i = 0; i<adProductos.getItemCount(); i++){
            if(rvDescuentos.getLayoutManager().findViewByPosition(i).getVisibility() == View.VISIBLE){
                count++;
            }
        }
        return count;
    }
    private void configurarBotonAñadir(String rol){
        try {
            mostrarMsg(rol);
            if(!(rol.equals("admin"))) {
                btnAgregarProducto.setVisibility(View.GONE);

            }
            else {
                btnAgregarProducto.setVisibility(View.VISIBLE);
            }
        }catch (Exception e)
        {
            mostrarMsg(e.getMessage());
            txtBuscar.setText(e.getMessage());
        }
    }
   /* @Override
    public void onBackPressed(){
        finishAffinity();
    }*/
}