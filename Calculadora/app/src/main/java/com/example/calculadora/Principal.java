package com.example.calculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadora.Adaptadores.AdaptadorProductos;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser usuario;
    FirebaseFirestore fStore;
    DocumentReference doc;
    CollectionReference colProductos;
    FloatingActionButton btnAgregarProducto;
    detectarInternet di;
    TextView txtBuscar;
    ImageButton btnSalir;
    Query query;
   GridView grdDescuentos;
   RecyclerView rvDescuentos;
   AdaptadorProductos adProductos;
    String  rol = "invitado";
Productos productos;
    final ArrayList<Productos> alProductos=new ArrayList<Productos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        txtBuscar=findViewById(R.id.txtBuscar);
        btnAgregarProducto=findViewById(R.id.btnAgregarProducto);
        //grdDescuentos=findViewById(R.id.grdDescuentos);
rvDescuentos=findViewById(R.id.rvDescuentos);
        fStore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
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
                       }
                   });
               }
               else{
                   rol="invitado";
               }
                txtBuscar.setText(rol);
               try {


               if(!rol.equals("admin")) {
                   btnAgregarProducto.setVisibility(View.GONE);

               }
               }catch (Exception e)
               {
                   mostrarMsg(e.getMessage());
                   txtBuscar.setText(e.getMessage());
               }
               btnSalir=findViewById(R.id.btnFiltrar);
               btnSalir.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       cerrarSesión();
                   }
               });

        mostrarProductos();
    }

    private void mostrarProductos() {
        /*try{
        colProductos = fStore.collection("productos");
        colProductos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    try {
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        productos = new Productos(documento.getId(),
                                documento.getString("codigo"),
                                documento.getString("categoria"),
                                documento.getString("nombre"),
                                documento.getString("descripcion"),
                                documento.getString("marca"),
                                documento.getDouble ("precioCompra"),
                                documento.getDouble("margenGanancia"),
                                documento.getDouble("descuento"),
                                documento.getDouble("stock"),
                                documento.getString("urlFoto"),
                                documento.getBoolean("actualizado"));
    alProductos.add(productos);

}

                        //adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(),alProductos);
                        //grdDescuentos.setAdapter(adImagenes);
                    }catch (Exception e){
    txtBuscar.setText(e.getMessage());
}
                    }
                }
            }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mostrarMsg(e.getMessage());
                txtBuscar.setText(e.getMessage());
            }
        });
    }catch (Exception e){
        mostrarMsg(e.getMessage());
    }}

         */
        try {
            rvDescuentos.setLayoutManager(new LinearLayoutManager(this));
            query = fStore.collection("productos");
            FirestoreRecyclerOptions<Productos> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Productos>()
                    .setQuery(query, Productos.class).build();
            //adProductos = new AdaptadorProductos(firestoreRecyclerOptions, this, getSupportFragmentManager());
            //adProductos.notifyDataSetChanged();
            //rvDescuentos.setAdapter(adProductos);
        }catch (Exception e){}
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
   /* @Override
    public void onBackPressed(){
        finishAffinity();
    }*/
}