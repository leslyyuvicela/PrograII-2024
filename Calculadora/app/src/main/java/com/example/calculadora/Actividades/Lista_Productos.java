package com.example.calculadora.Actividades;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lista_Productos extends AppCompatActivity {/*
    Bundle parametros = new Bundle();
    FloatingActionButton btn;
    ListView lts;
    Cursor cProductos;
    DB dbProductos;

    EditText txt;

    Productos misProductos;
    final ArrayList<Productos> alProductos=new ArrayList<Productos>();
    final ArrayList<Productos> alProductosCopy=new ArrayList<Productos>();

    JSONArray datosJSON;
    JSONObject jsonObject;
    obtenerDatosServidor datosServidor;
    detectarInternet di;
    int posicion =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos);
        dbProductos = new DB(Lista_Productos.this, "", null, 1);

        btn = findViewById(R.id.btnAbrirNuevosProductos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirActividad(parametros);
            }
        });
        btn=findViewById(R.id.btnSincronizarDatosProductos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                di = new detectarInternet(getApplicationContext());
                if(di.hayConexionInternet()) {
                    mostrarMsg("Sincronizando datos con el servidor...");
                    subirDatos();
                    obtenerDatosProductosServidor();
                    bajarDatos();
                    mostrarMsg("Sincronizacion completada");
                }else { mostrarMsg("No hay conexion");}
                mostrarDatosProductos();
            }
        });
        try {
            obtenerProductos();
            di = new detectarInternet(getApplicationContext());
            if(di.hayConexionInternet()) {
                obtenerDatosProductosServidor();

            }
            else{
                mostrarMsg("No hay conexión a internet");
            }

            mostrarDatosProductos();
        }catch(Exception e){mostrarMsg("Error al detectar si hay conexion "+e.getMessage());}
        buscarProductos();

    }
    private void eliminarDatosServidor(){
        try {

        }catch (Exception e){}
    }
    private void obtenerDatosProductosServidor(){
        try{
            datosServidor = new obtenerDatosServidor();
            String data = datosServidor.execute().get();
            jsonObject = new JSONObject(data);
            datosJSON = jsonObject.getJSONArray("rows");

            // mostrarDatosProductos();
        }catch (Exception e){
            mostrarMsg("Error al obtener datos desde el servidor: "+ e.getMessage());
        }
    }
    private void guardarDatosServidor(JSONObject datosProductos){
        try {
            datosProductos.put("actualizado","si");
            String respuesta = "";

            enviarDatosServidor objGuardarDatosServidor = new enviarDatosServidor(getApplicationContext());
            respuesta = objGuardarDatosServidor.execute(datosProductos.toString()).get();
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
              if (misDatosJSONObject.getString("actualizado").equals("no")) {
                    if(misDatosJSONObject.getString("_id").equals("")&& misDatosJSONObject.getString("_rev").equals("")){

                        misDatosJSONObject.remove("_id");
                        misDatosJSONObject.remove("_rev");
                    }
                  guardarDatosServidor(misDatosJSONObject);
                }
            }
            }

        }catch (Exception e){mostrarMsg("Error al sincronizar: "+e.getMessage());}
    }
    private void bajarDatos(){
        try{
            dbProductos.vaciar();
            JSONObject misDatosJSONObject;
            if (datosJSON.length()>0){
                for (int i=0; i<datosJSON.length(); i++) {
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");

                    String[] datos={
                            misDatosJSONObject.getString("idProducto"),
                            misDatosJSONObject.getString("nombre"),
                            misDatosJSONObject.getString("descripcion"),
                            misDatosJSONObject.getString("marca"),
                            misDatosJSONObject.getString("presentacion"),
                            misDatosJSONObject.getString("precioCompra"),
                            misDatosJSONObject.getString("margenGanancia"),
                            misDatosJSONObject.getString("stock"),
                            misDatosJSONObject.getString("urlCompletaImg"),
                            misDatosJSONObject.getString("_id"),
                            misDatosJSONObject.getString("_rev"),
                            misDatosJSONObject.getString("actualizado")
                    };
                    dbProductos.administrar_Productos("nuevo",datos);

                }
            }}catch (Exception e){mostrarMsg("Error al bajar los datos: "+e.getMessage());}
    }

    private void mostrarDatosProductos(){
        try{
            if( datosJSON.length()>0 ){
                lts = findViewById(R.id.ltsProductos);

                alProductos.clear();
                alProductosCopy.clear();

                JSONObject misDatosJSONObject;
                for (int i=0; i<datosJSON.length(); i++){
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");
                    misProductos = new Productos(
                            misDatosJSONObject.getString("idProducto"),
                            misDatosJSONObject.getString("nombre"),
                            misDatosJSONObject.getString("descripcion"),
                            misDatosJSONObject.getString("marca"),
                            misDatosJSONObject.getString("presentacion"),
                            misDatosJSONObject.getString("precioCompra"),
                            misDatosJSONObject.getString("margenGanancia"),
                            misDatosJSONObject.getString("stock"),
                            misDatosJSONObject.getString("urlCompletaImg"),
                            misDatosJSONObject.getString("_id"),
                            misDatosJSONObject.getString("_rev"),
                            misDatosJSONObject.getString("actualizado")
                    );
                    alProductos.add(misProductos);
                    alProductosCopy.add(misProductos);
                }
                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alProductos);
                lts.setAdapter(adImagenes);
                //alProductos.addAll(alProductos);

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
            menu.setHeaderTitle(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("nombre"));//1 es el nombre del Producto
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
                    parametros.putString("lesly", datosJSON.getJSONObject(posicion).toString());
                    abrirActividad(parametros);
                    break;
                case R.id.mnxEliminar:
                    eliminarDatosServidor();
                    eliminarProducto();
                    break;
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error en menu: "+ e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarProducto(){
        try {

            AlertDialog.Builder confirmacion = new AlertDialog.Builder(Lista_Productos.this);
            confirmacion.setTitle("Esta seguro de Eliminar a: ");

            confirmacion.setMessage(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("nombre"));

            confirmacion.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int i) {
                    try {
                        String respuesta = dbProductos.administrar_Productos("eliminar", new String[]{datosJSON.getJSONObject(posicion).getJSONObject("value").getString("idProducto")});

                        if (respuesta.equals("ok")){
                            mostrarMsg("Producto eliminado con exito.");
                            obtenerProductos();
                        }
                        else
                            mostrarMsg("Error al eliminar producto: "+respuesta);
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
        Intent abriVentana = new Intent(getApplicationContext(), Datos_Producto.class);
        abriVentana.putExtras(parametros);
        startActivity(abriVentana);
    }
    private void obtenerProductos(){
        try{
            alProductos.clear();
            alProductosCopy.clear();

            cProductos = dbProductos.consultar_Productos();

            if ( cProductos.moveToFirst() ){
                lts = findViewById(R.id.ltsProductos);
                datosJSON=new JSONArray();
                do{
                    jsonObject = new JSONObject();
                    JSONObject jsonObjectValue= new JSONObject();

                    jsonObject.put("idProducto", cProductos.getString(0));//idProducto
                    jsonObject.put("nombre",cProductos.getString(1));//nombre
                    jsonObject.put("descripcion",cProductos.getString(2));//descripcion
                    jsonObject.put("marca",cProductos.getString(3));//marca
                    jsonObject.put("presentacion",cProductos.getString(4));//presentacion
                    jsonObject.put("precioCompra", cProductos.getString(5));//precioCompra
                    jsonObject.put("margenGanancia", cProductos.getString(6));//margenGanancia
                    jsonObject.put("stock", cProductos.getString(7));//stock
                    jsonObject.put("urlCompletaImg",cProductos.getString(8));//foto
                    jsonObject.put("_id",cProductos.getString(9));//_id
                    jsonObject.put("_rev",cProductos.getString(10));//_rev
                    jsonObject.put("actualizado",cProductos.getString(11));//_rev


                    jsonObjectValue.put("value",jsonObject);
                    datosJSON.put(jsonObjectValue);

                }while(cProductos.moveToNext());
                //mostrarDatosProductos();

            }else{
                mostrarMsg("No hay Productos que mostrar");
            }
        }catch (Exception e){
            txt=findViewById(R.id.txtBuscarProductos);
            txt.setText(e.getMessage());
            mostrarMsg("Error al obtener Productos: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void buscarProductos(){
        EditText tempVal;
        tempVal = findViewById(R.id.txtBuscarProductos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    alProductos.clear();
                    String valor = tempVal.getText().toString().trim().toLowerCase();
                    if( valor.equals("")){
                        alProductos.addAll(alProductosCopy);
                    }else{
                        for( Productos Producto : alProductosCopy ){
                            String nombre = Producto.getNombre();
                            String descripcion = Producto.getDescripcion();
                            String marca = Producto.getMarca();
                            if( nombre.toLowerCase().trim().contains(valor) ||
                                    descripcion.toLowerCase().trim().contains(valor) ||
                                    marca.trim().contains(valor)  ){
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
*/
}