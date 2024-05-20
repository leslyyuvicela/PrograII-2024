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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadora.Adaptadores.AdaptadorProductos;
import com.example.calculadora.Modelos.Productos;
import com.example.calculadora.R;
import com.example.calculadora.detectarInternet;
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

public class Lista_Productos extends AppCompatActivity { FirebaseAuth auth;
    FirebaseUser usuario;
    FirebaseFirestore fStore;
    DocumentReference doc;
    FloatingActionButton btnAgregarProducto;
    ImageButton btnBuscar, btnCarrito, btnChat, btnPerfil, btnMenu,btnPrincipal;
    detectarInternet di;
    EditText txtBuscar;
    TextView tvCategoria;
    Query query;
    RecyclerView rvProductos;
    AdaptadorProductos adProductos;
    String rol = "", campo = "nombre", filtro = "", categoria;

    Productos productos;
    final ArrayList<Productos> alProductos = new ArrayList<Productos>();

    @Override
    protected void onStart() {
        super.onStart();
        adProductos.startListening();
        mostrarProductos(campo, filtro, categoria);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adProductos.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos);
        categoria = getIntent().getStringExtra("categoria");
        campo = "nombre";
        filtro = "";
        tvCategoria=findViewById(R.id.tvCategoria);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        txtBuscar = findViewById(R.id.txtBuscar);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnChat = findViewById(R.id.btnChat);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
        btnPrincipal=findViewById(R.id.btnPrincipal);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnMenu=findViewById(R.id.btnMenu);
        btnAgregarProducto.setVisibility(View.GONE);
        btnBuscar = findViewById(R.id.btnBuscar);
        //grdDescuentos=findViewById(R.id.grdDescuentos);
        rvProductos = findViewById(R.id.rvDescuentos);
        rvProductos.setLayoutManager(new LinearLayoutManager(this));
        mostrarProductos(campo, filtro,categoria);
        try {
            usuario = auth.getCurrentUser();
        } catch (Exception e) {
            mostrarMsg(e.getMessage().toString());
        }
        if (usuario != null) {

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
        } else {
            rol = "invitado";
        }


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtro = txtBuscar.getText().toString();
                mostrarProductos(campo, filtro,categoria);
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
                Intent i = new Intent(getApplicationContext(), Perfil.class);
                startActivity(i);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCategorias();
            }
        });
        btnPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPrincipal();
                finish();
            }
        });

    }




    private void mostrarCategorias() {
        CollectionReference col = fStore.collection("categorias");
        col.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    PopupMenu menu = new PopupMenu(Lista_Productos.this,btnMenu);
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        String categoria = doc.getString("nombre");
                        if(categoria != null){
                            menu.getMenu().add(categoria);
                        }
                    }
                    menu.setOnMenuItemClickListener(categoria ->{
                        mostrarSubCategorias( categoria.getTitle().toString());
                        return true;
                    });
                    menu.show();
                }
            }
        });
    }

    private void mostrarSubCategorias(String categoria) {
        fStore.collection("subCategorias").whereEqualTo("categoriaPrincipal",categoria)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            PopupMenu menu = new PopupMenu(Lista_Productos.this,btnMenu);
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                String categoria = doc.getString("nombre");
                                if(categoria != null){
                                    menu.getMenu().add(categoria);
                                }
                            }
                            menu.setOnMenuItemClickListener(categoria ->{
                                mostrarProductos("nombre","",categoria.getTitle().toString());
                                return true;
                            });
                            menu.show();
                        }
                    }
                });
    }

    private void abrirPrincipal() {
        Intent i = new Intent(getApplicationContext(), Principal.class);
        startActivity(i);
    }

    private void agregarProducto() {
        Intent agregarProducto = new Intent(getApplicationContext(), Agregar_Producto.class);
        agregarProducto.putExtra("idProducto", "");
        startActivity(agregarProducto);
    }

    private void mostrarProductos(String campo, String filtro, String categoria) {
        tvCategoria.setText(categoria);
        query = fStore.collection("productos").whereEqualTo("categoria",categoria);
        FirestoreRecyclerOptions<Productos> fOptions = new FirestoreRecyclerOptions.Builder<Productos>()
                .setQuery(query, Productos.class).build();
        adProductos = new AdaptadorProductos(fOptions, this, campo, filtro);
        adProductos.startListening();
        rvProductos.setAdapter(adProductos);
    }

    private void cerrarSesión() {
        FirebaseAuth.getInstance().signOut();
        Intent abrirLogIn = new Intent(getApplicationContext(), LogIn.class);
        startActivity(abrirLogIn);
        finish();
    }

    private void mostrarMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private int elementosListados(int count) {
        for (int i = 0; i < adProductos.getItemCount(); i++) {
            if (rvProductos.getLayoutManager().findViewByPosition(i).getVisibility() == View.VISIBLE) {
                count++;
            }
        }
        return count;
    }

    private void configurarBotonAñadir(String rol) {
        try {
            if (!(rol.equals("admin"))) {
                btnAgregarProducto.setVisibility(View.GONE);

            } else {
                btnAgregarProducto.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            mostrarMsg(e.getMessage());
            txtBuscar.setText(e.getMessage());
        }
    }
}