package com.example.calculadora.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadora.R;
import com.example.calculadora.detectarInternet;
import com.example.calculadora.utilidades;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Agregar_Producto extends AppCompatActivity {
    TextView tempVal;
    Button btnGuardar, btnAgregarProducto;
    FloatingActionButton btnRegresar;
    private String accion="nuevo", urlFoto ="",idProducto="", actualizado="",
    categoria, codigo, descripcion, marca, nombre, rol;
    Double descuento,margenGanancia,precioCompra, stock;
    Intent tomarFotoIntent,cargarFotoIntent;
    EditText txtNombre,txtCodigo,txtMarca,txtDescripcion,txtCategoria, txtmargenGanancia,
    txtPrecioCompra,txtDescuento,txtStock;
    ImageView img;
    FirebaseAuth auth;
    utilidades utls;
    detectarInternet di;
    private FirebaseFirestore fStore;
    FirebaseUser usuario;
    DocumentReference doc;

    StorageReference sRef;
    String rutaFotoStorage = "productos/*";
    private static final int COD_SEL_STORAGE=200;
    private static final int COD_SEL_IMAGEN=300;
    private Uri uriLocal;
    String foto = "photo";
    String idd;
    Boolean nuevo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_producto);
        idProducto= getIntent().getStringExtra("idProducto");
        img = findViewById(R.id.imgProducto);
        btnGuardar= findViewById(R.id.btnGuardarProducto);
        txtCategoria=findViewById(R.id.txtCategoria);
        txtCodigo=findViewById(R.id.txtCodigo);
        txtDescripcion=findViewById(R.id.txtdescripcion);
        txtDescuento=findViewById(R.id.txtDescuento);
        txtMarca=findViewById(R.id.txtMarca);
        txtmargenGanancia=findViewById(R.id.txtMargenGanancia);
        txtNombre=findViewById(R.id.txtnombre);
        txtPrecioCompra=findViewById(R.id.txtPrecioCompra);
        txtStock=findViewById(R.id.txtStock);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        fStore = FirebaseFirestore.getInstance();
        fStore.setFirestoreSettings(settings);
        sRef = FirebaseStorage.getInstance().getReference();
        btnAgregarProducto=findViewById(R.id.btnGuardarProducto);
        nuevo = true;
        if (!idProducto.equals("")){
            nuevo=false;
            obtenerProducto(idProducto);
        }

        try {
            usuario = auth.getCurrentUser();
        } catch (Exception e) {

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
        }


btnGuardar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        categoria = txtCategoria.getText().toString().trim();
        codigo = txtCodigo.getText().toString().trim();
        descripcion = txtDescripcion.getText().toString().trim();
        marca = txtMarca.getText().toString().trim();
        nombre = txtNombre.getText().toString().trim();
        descuento =Double.parseDouble(txtDescuento.getText().toString().trim());
        margenGanancia =Double.parseDouble(txtmargenGanancia.getText().toString().trim());
        precioCompra =Double.parseDouble(txtPrecioCompra.getText().toString().trim());
        stock =Double.parseDouble(txtStock.getText().toString().trim());
        subirFoto(uriLocal,nuevo);
    }
});



        utls = new utilidades();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFotoGaleria();
            }
        });



    }

    private void obtenerProducto(String id) {
        doc= fStore.collection("productos").document(id);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snap) {
                txtCategoria.setText(snap.getString("categoria"));
                txtCodigo.setText(snap.getString("codigo"));
                txtDescripcion.setText(snap.getString("descripcion"));
                txtDescuento.setText(snap.getDouble("descuento").toString());
                txtMarca.setText(snap.getString("marca"));
                txtmargenGanancia.setText(snap.getDouble("margenGanancia").toString());
                txtNombre.setText(snap.getString("nombre"));
                txtPrecioCompra.setText(snap.getDouble("precioCompra").toString());
                txtStock.setText(snap.getDouble("stock").toString());
                urlFoto= snap.getString("urlFoto");
                try{
                uriLocal= Uri.parse(urlFoto);

                Picasso.get().load(urlFoto).resize(240,240).into(img);
            }catch (Exception e){}
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mostrarMsg("No se pudo obtener el producto");
            }
        });
    }

    private void cargarFotoGaleria() {
        Intent galeria = new Intent(Intent.ACTION_PICK);
        galeria.setType("image/*");
        startActivityForResult(galeria, COD_SEL_IMAGEN);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

            if( requestCode==COD_SEL_IMAGEN && resultCode==RESULT_OK ){
                uriLocal =data.getData();
                nuevo = false;
                Picasso.get().load(uriLocal).resize(180,180).into(img);
            }else{
                mostrarMsg("Se cancelo la toma de la foto");

            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar la camara: "+ e.getMessage());
        }
    }

    private void subirFoto(Uri uriFoto, Boolean nuevo) {
        try {
            if (nuevo) {
                String rutaFoto = rutaFotoStorage + "" + foto + "" + utls.generarIdUnico();
                StorageReference referencia = sRef.child(rutaFoto);
                referencia.putFile(uriFoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                        while (!uriTask.isSuccessful()) ;
                        if (uriTask.isSuccessful()) {
                            uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    guardarProducto(uri.toString());
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            } else {
                guardarProducto(uriFoto.toString());
            }
        }catch (Exception e){
            guardarProducto("");
        }
    }

    private void guardarProducto( String uriServer) {try {
        Map<String, Object> map = new HashMap<>();
        urlFoto = uriServer;
        map.put("categoria", categoria);
        map.put("codigo", codigo);
        map.put("descripcion", descripcion);
        map.put("descuento", descuento);
        map.put("marca", marca);
        map.put("margenGanancia", margenGanancia);
        map.put("nombre", nombre);
        map.put("precioCompra", precioCompra);
        map.put("stock", stock);
        map.put("urlFoto", urlFoto);
        if (idProducto.equals("")) {
            fStore.collection("productos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    mostrarMsg("Producto guardado con exito!");
                    regresarListaProductos();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mostrarMsg("Error al guardar");
                }
            });
        } else {
            fStore.collection("productos").document(idProducto).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    mostrarMsg("Producto guardado con exito!");
                    regresarListaProductos();
                }
            });
        }
    }catch (Exception e){
        mostrarMsg(e.getMessage());
    }
    }

    private void guardarDatosServidor(){}
    private void guardarDatosSQLite(){}


    private void cargarFotoProducto(){
        cargarFotoIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if( cargarFotoIntent.resolveActivity(getPackageManager())!=null ){
            File fotoProducto = null;
            try{

                fotoProducto = crearImagenProducto();
                if( fotoProducto!=null ){
                    Uri uriFotoProducto = FileProvider.getUriForFile(Agregar_Producto.this, "com.example.calculadora.fileprovider", fotoProducto);
                    cargarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoProducto);
                    startActivityForResult(cargarFotoIntent, 1);
                }else{
                    mostrarMsg("NO pude tomar la foto");
                }
            }catch (Exception e){
                mostrarMsg("Error al tomar la foto: "+ e.getMessage());
            }
        }
    }


    private void tomarFotoProducto(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if( tomarFotoIntent.resolveActivity(getPackageManager())!=null ){
            File fotoProducto = null;
            try{

                fotoProducto = crearImagenProducto();
                if( fotoProducto!=null ){
                    Uri uriFotoProducto = FileProvider.getUriForFile(Agregar_Producto.this, "com.example.calculadora.fileprovider", fotoProducto);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoProducto);
                    startActivityForResult(tomarFotoIntent, 1);
                }else{
                    mostrarMsg("NO pude tomar la foto");
                }
            }catch (Exception e){
                mostrarMsg("Error al tomar la foto: "+ e.getMessage());
            }
        }else{
            mostrarMsg("No se selecciono una foto...");
        }
    }
    private File crearImagenProducto() throws Exception{

        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( !dirAlmacenamiento.exists() ){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlFoto = image.getAbsolutePath();
        return image;
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void regresarListaProductos(){
        Intent abrirVentana = new Intent(getApplicationContext(), Principal.class);
        startActivity(abrirVentana);
        finish();
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
        }
    }
}