package com.example.calculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Lista_Peliculas extends AppCompatActivity {
    Bundle parametros = new Bundle();
    FloatingActionButton btn;
    ListView lts;
    Cursor cPeliculas;
    DB dbPeliculas;

    EditText txt;

    Peliculas misPeliculas;
    final ArrayList<Peliculas> alPeliculas=new ArrayList<Peliculas>();
    final ArrayList<Peliculas> alPeliculasCopy=new ArrayList<Peliculas>();

    JSONArray datosJSON;
    JSONObject jsonObject;
    obtenerDatosServidor datosServidor;
    detectarInternet di;
    int posicion =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_peliculas);
        dbPeliculas = new DB(Lista_Peliculas.this, "", null, 1);

        btn = findViewById(R.id.btnAbrirNuevosPeliculas);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirActividad(parametros);
            }
        });
        btn=findViewById(R.id.btnSincronizarDatosPeliculas);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                di = new detectarInternet(getApplicationContext());
                if(di.hayConexionInternet()) {
                    mostrarMsg("Sincronizando datos con el servidor...");
                    subirDatos();
                    obtenerDatosPeliculasServidor();
                    bajarDatos();
                    mostrarMsg("Sincronizacion completada");
                }else { mostrarMsg("No hay conexion");}
                mostrarDatosPeliculas();
            }
        });
        try {
            obtenerPeliculas();
            di = new detectarInternet(getApplicationContext());
            if(di.hayConexionInternet()) {
                obtenerDatosPeliculasServidor();

            }
            else{
                mostrarMsg("No hay conexión a internet");
            }

            mostrarDatosPeliculas();
        }catch(Exception e){mostrarMsg("Error al detectar si hay conexion "+e.getMessage());}
        buscarPeliculas();

    }
    private void eliminarDatosServidor(){
        try {

        }catch (Exception e){}
    }
    private void obtenerDatosPeliculasServidor(){
        try{
            datosServidor = new obtenerDatosServidor();
            String data = datosServidor.execute().get();
            jsonObject = new JSONObject(data);
            datosJSON = jsonObject.getJSONArray("rows");
            // mostrarDatosPeliculas();
        }catch (Exception e){
            mostrarMsg("Error al obtener datos desde el servidor: "+ e.getMessage());
        }
    }
    private void guardarDatosServidor(JSONObject datosPeliculas){
        try {
            String respuesta = "";
            datosPeliculas.remove("_id");
            datosPeliculas.remove("_rev");
            enviarDatosServidor objGuardarDatosServidor = new enviarDatosServidor(getApplicationContext());
            respuesta = objGuardarDatosServidor.execute(datosPeliculas.toString()).get();
            JSONObject respuestaJSONObject = new JSONObject(respuesta);

            if (respuestaJSONObject.getBoolean("ok")) {
                //   respuesta = "Error al guardar en servidor: " + respuesta;
            }
            else{respuesta = "Error al guardar en servidor: " + respuesta;}
        }catch (Exception e) {

            mostrarMsg("Error en server: "+e.getMessage());
        }

    }
    private void subirDatos(){
        try{
            JSONObject misDatosJSONObject;
            if (datosJSON.length()>0){
                for (int i=0; i<datosJSON.length(); i++) {
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");

                    if(misDatosJSONObject.getString("_id").equals("")&& misDatosJSONObject.getString("_rev").equals("")){
                        guardarDatosServidor(misDatosJSONObject);
                        // mostrarMsg(misDatosJSONObject.toString());
                    }
                }
            }

        }catch (Exception e){mostrarMsg("Error al sincronizar: "+e.getMessage());}
    }
    private void bajarDatos(){
        try{
            dbPeliculas.vaciar();
            JSONObject misDatosJSONObject;
            if (datosJSON.length()>0){
                for (int i=0; i<datosJSON.length(); i++) {
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");

                    String[] datos={
                            misDatosJSONObject.getString("idPelicula"),
                            misDatosJSONObject.getString("titulo"),
                            misDatosJSONObject.getString("duracion"),
                            misDatosJSONObject.getString("sinopsis"),
                            misDatosJSONObject.getString("urlCompletaImg"),
                            misDatosJSONObject.getString("_id"),
                            misDatosJSONObject.getString("_rev")
                    };
                    dbPeliculas.administrar_Peliculas("nuevo",datos);

                }
            }}catch (Exception e){mostrarMsg("Error al bajar los datos: "+e.getMessage());}
    }

    private void mostrarDatosPeliculas(){
        try{
            if( datosJSON.length()>0 ){
                lts = findViewById(R.id.ltsPeliculas);

                alPeliculas.clear();
                alPeliculasCopy.clear();

                JSONObject misDatosJSONObject;
                for (int i=0; i<datosJSON.length(); i++){
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");
                    misPeliculas = new Peliculas(
                            misDatosJSONObject.getString("idPelicula"),
                            misDatosJSONObject.getString("titulo"),
                            misDatosJSONObject.getString("duracion"),
                            misDatosJSONObject.getString("sinopsis"),
                            misDatosJSONObject.getString("presentacion"),
                            misDatosJSONObject.getString("precio"),
                            misDatosJSONObject.getString("urlCompletaImg"),
                            misDatosJSONObject.getString("_id"),
                            misDatosJSONObject.getString("_rev")
                    );
                    alPeliculas.add(misPeliculas);
                    alPeliculasCopy.add(misPeliculas);
                }
                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alPeliculas);
                lts.setAdapter(adImagenes);
                //alPeliculas.addAll(alPeliculas);

                registerForContextMenu(lts);
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar los datos: "+e.getMessage());
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            menu.setHeaderTitle(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("titulo"));//1 es el titulo del Pelicula
        }catch (Exception e){mostrarMsg("Error al mostrar el menu: "+e.getMessage());}
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirActividad(parametros);
                    break;
                case R.id.mnxModificar:

                    parametros.putString("accion","modificar");
                    parametros.putString("tienda_online", datosJSON.getJSONObject(posicion).toString());
                    abrirActividad(parametros);
                    break;
                case R.id.mnxEliminar:
                    eliminarDatosServidor();
                    eliminarPelicula();
                    break;
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error en menu: "+ e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarPelicula(){
        try {

            AlertDialog.Builder confirmacion = new AlertDialog.Builder(Lista_Peliculas.this);
            confirmacion.setTitle("Esta seguro de Eliminar a: ");

            confirmacion.setMessage(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("titulo"));

            confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int i) {
                    try {
                        String respuesta = dbPeliculas.administrar_Peliculas("eliminar", new String[]{datosJSON.getJSONObject(posicion).getJSONObject("value").getString("idPelicula")});

                        if (respuesta.equals("ok")){
                            mostrarMsg("Pelicula eliminado con exito.");
                            obtenerPeliculas();
                        }
                        else
                            mostrarMsg("Error al eliminar Pelicula: "+respuesta);
                    }catch (Exception e){mostrarMsg("Error al eliminar datos: "+e.getMessage());}
                }
            });

            confirmacion.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        }catch (Exception e){
            mostrarMsg("Error al eliminar: "+ e.getMessage());
        }
    }
    private void abrirActividad(Bundle parametros){
        Intent abriVentana = new Intent(getApplicationContext(), Datos_Pelicula.class);
        abriVentana.putExtras(parametros);
        startActivity(abriVentana);
    }
    private void obtenerPeliculas(){
        try{
            alPeliculas.clear();
            alPeliculasCopy.clear();

            cPeliculas = dbPeliculas.consultar_Peliculas();

            if ( cPeliculas.moveToFirst() ){
                lts = findViewById(R.id.ltsPeliculas);
                datosJSON=new JSONArray();
                do{
                    jsonObject = new JSONObject();
                    JSONObject jsonObjectValue= new JSONObject();

                    jsonObject.put("idPelicula", cPeliculas.getString(0));//idPelicula
                    jsonObject.put("titulo",cPeliculas.getString(1));//titulo
                    jsonObject.put("duracion",cPeliculas.getString(2));//duracion
                    jsonObject.put("sinopsis",cPeliculas.getString(3));//sinopsis
                    jsonObject.put("presentacion",cPeliculas.getString(4));//presentacion
                    jsonObject.put("precio", cPeliculas.getString(5));//precio
                    jsonObject.put("urlCompletaImg",cPeliculas.getString(6));//foto
                    jsonObject.put("_id",cPeliculas.getString(7));//_id
                    jsonObject.put("_rev",cPeliculas.getString(8));//_rev


                    jsonObjectValue.put("value",jsonObject);
                    datosJSON.put(jsonObjectValue);

                }while(cPeliculas.moveToNext());
                //mostrarDatosPeliculas();
               /* alPeliculasCopy.addAll(alPeliculas);

                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alPeliculas);
                lts.setAdapter(adImagenes);

                registerForContextMenu(lts);

                */
            }else{
                mostrarMsg("No hay Peliculas que mostrar");
            }
        }catch (Exception e){
            txt=findViewById(R.id.txtBuscarPeliculas);
            txt.setText(e.getMessage());
            mostrarMsg("Error al obtener Peliculas: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void buscarPeliculas(){
        EditText tempVal;
        tempVal = findViewById(R.id.txtBuscarPeliculas);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    alPeliculas.clear();
                    String valor = tempVal.getText().toString().trim().toLowerCase();
                    if( valor.equals("")){
                        alPeliculas.addAll(alProductosCopy);
                    }else{
                        for( productos Producto : alProductosCopy ){
                            String titulo = Producto.getTitulo();
                            String duracion = Producto.getDuracion();
                            String sinopsis = Producto.getSinopsis();
                            String presentacion = Producto.getPresentacion();
                            if( titulo.toLowerCase().trim().contains(valor) ||
                                    duracion.toLowerCase().trim().contains(valor) ||
                                    sinopsis.trim().contains(valor) ||
                                    presentacion.trim().toLowerCase().contains(valor) ){
                                alProductos.add(Producto);
                            }
                        }
                        adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alProductos);
                        lts.setAdapter(adImagenes);
                    }
                }catch (Exception e){
                    mostrarMsg("Error al buscar: "+e.getMessage() );
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}