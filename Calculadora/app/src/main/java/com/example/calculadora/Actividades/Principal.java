package com.example.calculadora.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.calculadora.Adaptadores.Firestore.AdAnunciosFirestore;
import com.example.calculadora.Adaptadores.Firestore.AdProductosFirestore;
import com.example.calculadora.GestorImagenes;
import com.example.calculadora.Modelos.Productos;
import com.example.calculadora.R;
import com.example.calculadora.detectarInternet;
import com.example.calculadora.utilidades;
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
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {
    StorageReference sref;

    FirebaseAuth auth;
    GestorImagenes gestorImagenes;
    FirebaseUser usuario;
    FirebaseFirestore fStore;
    DocumentReference doc;
    FloatingActionButton btnAgregarProducto;
    ImageButton btnBuscar, btnCarrito, btnChat, btnPerfil, btnMenu, btnMapa;
    detectarInternet di;
    EditText txtBuscar;
    Query query;
    RecyclerView rvDescuentos;
    AdProductosFirestore adProductosFirestore;
    AdAnunciosFirestore adAnuncios;
    String rol = "invitado", campo = "nombre", filtro = "";
    int anuncioActual;
    private ViewPager vpAnuncios;
    private Handler handler;

    Productos productos;
    final ArrayList<Productos> alProductos = new ArrayList<Productos>();
    utilidades utils;

    private final Runnable actualizarAnuncios = () -> {
        anuncioActual = vpAnuncios.getCurrentItem();
         if (anuncioActual == vpAnuncios.getAdapter().getCount() - 1) {
            anuncioActual = 0;
        }
        vpAnuncios.setCurrentItem(anuncioActual + 1, true);

    };


    @Override
    protected void onStart() {
        super.onStart();
        adProductosFirestore.startListening();
        mostrarProductos(campo, filtro.toLowerCase().trim());
    }

    @Override
    protected void onStop() {
        super.onStop();
            adProductosFirestore.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(actualizarAnuncios);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        gestorImagenes = new GestorImagenes();
        di = new detectarInternet(getApplicationContext());
        campo = "nombre";
        filtro = "";
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        fStore = FirebaseFirestore.getInstance();
        fStore.setFirestoreSettings(settings);
        auth = FirebaseAuth.getInstance();
        txtBuscar = findViewById(R.id.txtBuscar);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnChat = findViewById(R.id.btnChat);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnMapa = findViewById(R.id.btnUbicacion);
        btnMenu = findViewById(R.id.btnMenu);
        btnAgregarProducto.setVisibility(View.GONE);
        btnBuscar = findViewById(R.id.btnBuscar);
        //grdDescuentos=findViewById(R.id.grdDescuentos);
        rvDescuentos = findViewById(R.id.rvDescuentos);
        rvDescuentos.setLayoutManager(new LinearLayoutManager(this));
        vpAnuncios = findViewById(R.id.vpAnuncios);

        fStore.collection("categorias").get();
        fStore.collection("subCategorias").get();
        handler = new Handler();
        mostrarAnuncios();


        handler.postDelayed(actualizarAnuncios, 5000);
        mostrarProductos(campo, filtro.toLowerCase().trim());
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
                    mostrarMsg("Nooo "+e.getMessage());
                }
            });
        } else {
            rol = "invitado";
            configurarBotonAñadir(rol);
        }


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtro = txtBuscar.getText().toString();
                mostrarProductos(campo, filtro.toLowerCase().trim());
            }
        });
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Mapa.class);
                startActivity(i);
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
                if (rol.equals("admin")) {
                    Intent chat = new Intent(getApplicationContext(), ListaChats.class);
                    startActivity(chat);
                } else {
                    Intent chat = new Intent(getApplicationContext(), Chat.class);
                    startActivity(chat);
                }
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Perfil.class);
                startActivity(i);
            }
        });
        vpAnuncios.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(actualizarAnuncios);
                handler.postDelayed(actualizarAnuncios, 10000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void mostrarAnuncios() {
        fStore.collection("anuncios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> urlsAnuncios = new ArrayList<>();
                for (QueryDocumentSnapshot doc : task.getResult()
                ) {
                    urlsAnuncios.add(doc.getString("url"));
                }
                adAnuncios = new AdAnunciosFirestore(getApplicationContext(), urlsAnuncios.toArray(new String[urlsAnuncios.size()]));
                vpAnuncios.setAdapter(adAnuncios);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCategorias();
            }
        });

    }

    private void mostrarCategorias() {
        CollectionReference col = fStore.collection("categorias");
        col.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PopupMenu menu = new PopupMenu(Principal.this, btnMenu);
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String categoria = doc.getString("nombre");
                        if (categoria != null) {
                            menu.getMenu().add(categoria);
                        }
                    }
                    menu.setOnMenuItemClickListener(categoria -> {
                        mostrarSubCategorias(categoria.getTitle().toString());
                        return true;
                    });
                    menu.show();
                }
            }
        });
    }

    private void mostrarSubCategorias(String categoria) {
        fStore.collection("subCategorias").whereEqualTo("categoriaPrincipal", categoria)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            PopupMenu menu = new PopupMenu(Principal.this, btnMenu);
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String categoria = doc.getString("nombre");
                                if (categoria != null) {
                                    menu.getMenu().add(categoria);
                                }
                            }
                            menu.setOnMenuItemClickListener(categoria -> {
                                abrirListaProductos(categoria.getTitle().toString());
                                return true;
                            });
                            menu.show();
                        }
                    }
                });
    }

    private void abrirListaProductos(String categoria) {
        Intent i = new Intent(getApplicationContext(), Lista_Productos.class);
        i.putExtra("categoria", categoria);
        startActivity(i);
    }

    private void agregarProducto() {
        Intent agregarProducto = new Intent(getApplicationContext(), Agregar_Producto.class);
        agregarProducto.putExtra("idProducto", "");
        startActivity(agregarProducto);
    }

    private void mostrarProductos(String campo, String filtro) {
        query = fStore.collection("productos").whereGreaterThan("descuento", 0);
        FirestoreRecyclerOptions<Productos> fOptions = new FirestoreRecyclerOptions.Builder<Productos>()
                .setQuery(query, Productos.class).build();
        adProductosFirestore = new AdProductosFirestore(fOptions, this, campo, filtro);
        adProductosFirestore.startListening();
        rvDescuentos.setAdapter(adProductosFirestore);
    }

    private void mostrarMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private int elementosListados(int count) {
        for (int i = 0; i < adProductosFirestore.getItemCount(); i++) {
            if (rvDescuentos.getLayoutManager().findViewByPosition(i).getVisibility() == View.VISIBLE) {
                count++;
            }
        }
        return count;
    }

    private void configurarBotonAñadir(String rol) {
        try {
            if (!(rol.equals("admin"))) {
                btnAgregarProducto.setVisibility(View.GONE);
                if (!rol.equals("cliente")) {
                    btnChat.setVisibility(View.GONE);
                    btnCarrito.setVisibility(View.GONE);
                } else {

                    btnChat.setVisibility(View.VISIBLE);
                    btnCarrito.setVisibility(View.VISIBLE);
                }
            } else {
                btnAgregarProducto.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            mostrarMsg(e.getMessage());
        }
    }
   /* @Override
    public void onBackPressed(){
        finishAffinity();
    }*/
}
