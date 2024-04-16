package com.example.calculadora;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datos_Pelicula extends AppCompatActivity {
    TextView tempVal;
    Button btn;
    FloatingActionButton btnRegresar;
    String accion="nuevo", id="", urlCompletaImg="",rev="",idPelicula="";
    Intent tomarFotoIntent,cargarFotoIntent;
    ImageView img;
    utilidades utls;
    detectarInternet di;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_pelicula);

        utls = new utilidades();

        img = findViewById(R.id.imgPelicula);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFotoPelicula();
            }
        });

        btnRegresar = findViewById(R.id.btnRegresarListaPeliculas);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarListaPeliculas();
            }
        });
        btn = findViewById(R.id.btnGuardarPeliculas);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tempVal = findViewById(R.id.txttitulo);
                    String titulo = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtduracion);
                    String duracion = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtSinopsis);
                    String sinopsis = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtPresentacion);
                    String presentacion = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtPrecio);
                    String precio = tempVal.getText().toString();

                    if (!(titulo.equals("") || sinopsis.equals("") || duracion.equals("") || presentacion.equals("") || precio.equals(""))) {
                        //Guardar en el server
                        String[] datos = new String[]{idPelicula, titulo, duracion, sinopsis, presentacion, precio, urlCompletaImg,id,rev};

                        try {
                            di = new detectarInternet(getApplicationContext());
                            if(di.hayConexionInternet()) {
                                guardarDatosServidor(datos);
                            }
                            else {
                                guardarDatosSQLite(datos);
                            }
                            mostrarMsg("Pelicula Registrado con Exito.");
                            regresarListaPeliculas();

                        }catch(Exception e){mostrarMsg("Error al detectar si hay conexion "+e.getMessage());}


                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Hay campos vacíos", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){mostrarMsg("Error al guardar: "+e.getMessage());}
            }

        });
        mostrarDatosPeliculas();
    }
    private void guardarDatosServidor(String[] datos){
        try {
            JSONObject datosPeliculas = new JSONObject();
            if (accion.equals("modificar") && datos[7].length() > 0 && datos[8].length() > 0) {
                datosPeliculas.put("_id", datos[7]);
                datosPeliculas.put("_rev", datos[8]);

            }
            datosPeliculas.put("idPelicula", datos[0]);
            datosPeliculas.put("titulo", datos[1]);
            datosPeliculas.put("duracion", datos[2]);
            datosPeliculas.put("sinopsis", datos[3]);
            datosPeliculas.put("presentacion", datos[4]);
            datosPeliculas.put("precio", datos[5]);
            datosPeliculas.put("urlCompletaImg", datos[6]);

            String respuesta = "";
            enviarDatosServidor objGuardarDatosServidor = new enviarDatosServidor(getApplicationContext());
            respuesta = objGuardarDatosServidor.execute(datosPeliculas.toString()).get();
            JSONObject respuestaJSONObject = new JSONObject(respuesta);

            if (respuestaJSONObject.getBoolean("ok")) {
                id = respuestaJSONObject.getString("id");
                rev = respuestaJSONObject.getString("rev");
            } else {
                respuesta = "Error al guardar en servidor: " + respuesta;
            }
        }catch (Exception e) {
            mostrarMsg("Error en server: "+e.getMessage());
        }

    }
    private void guardarDatosSQLite(String[] datos){
        try {
            DB db = new DB(getApplicationContext(), "", null, 1);
            String respuesta = "";

            respuesta = db.administrar_Peliculas(accion, datos);

        }catch (Exception e){mostrarMsg("Error SQLite"+e.getMessage());}

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

            if( requestCode==1 && resultCode==RESULT_OK ){
                Bitmap imagenBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                img.setImageBitmap(imagenBitmap);
            }else{
                mostrarMsg("Se cancelo la toma de la foto");
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar la camara: "+ e.getMessage());
        }
    }
    private void cargarFotoPelicula(){
        cargarFotoIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if( cargarFotoIntent.resolveActivity(getPackageManager())!=null ){
            File fotoPelicula = null;
            try{

                fotoPelicula = crearImagenPelicula();
                if( fotoPelicula!=null ){
                    Uri uriFotoPelicula = FileProvider.getUriForFile(Datos_Pelicula.this, "com.ugb.calculadora.fileprovider", fotoPelicula);
                    cargarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoPelicula);
                    startActivityForResult(cargarFotoIntent, 1);
                }else{
                    mostrarMsg("NO pude tomar la foto");
                }
            }catch (Exception e){
                mostrarMsg("Error al tomar la foto: "+ e.getMessage());
            }
        }
    }


    private void tomarFotoPelicula(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if( tomarFotoIntent.resolveActivity(getPackageManager())!=null ){
            File fotoPelicula = null;
            try{

                fotoPelicula = crearImagenPelicula();
                if( fotoPelicula!=null ){
                    Uri uriFotoPelicula = FileProvider.getUriForFile(Datos_Pelicula.this, "com.ugb.calculadora.fileprovider", fotoPelicula);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoPelicula);
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
    private File crearImagenPelicula() throws Exception{

        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( !dirAlmacenamiento.exists() ){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void regresarListaPeliculas(){
        Intent abrirVentana = new Intent(getApplicationContext(), Lista_Peliculas.class);
        startActivity(abrirVentana);
    }
    private void mostrarDatosPeliculas(){
        try {
            Bundle parametros = getIntent().getExtras();
            accion = parametros.getString("accion");
            if( accion.equals("modificar") ){
                JSONObject jsonObject = new JSONObject(parametros.getString("tienda_online")).getJSONObject("value");
                id=jsonObject.getString("_id");
                rev =jsonObject.getString("_rev");
                idPelicula=jsonObject.getString("idPelicula");

                tempVal = findViewById(R.id.txttitulo);
                tempVal.setText(jsonObject.getString("titulo"));

                tempVal = findViewById(R.id.txtduracion);
                tempVal.setText(jsonObject.getString("duracion"));

                tempVal = findViewById(R.id.txtSinopsis);
                tempVal.setText(jsonObject.getString("sinopsis"));

                tempVal = findViewById(R.id.txtPresentacion);
                tempVal.setText(jsonObject.getString("presentacion"));

                tempVal = findViewById(R.id.txtPrecio);
                tempVal.setText(jsonObject.getString("precio"));

                urlCompletaImg = jsonObject.getString("urlCompletaImg");
                Bitmap bitmap = BitmapFactory.decodeFile(urlCompletaImg);
                img.setImageBitmap(bitmap);
            }else
                idPelicula=utls.generarIdUnico();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al mostrar los datos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}