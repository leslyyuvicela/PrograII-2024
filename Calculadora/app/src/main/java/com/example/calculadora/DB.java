package com.example.calculadora;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "db_Productos";
    private static final int v=1;
    private static final String tablaProductos = "CREATE TABLE Productos( idProducto text,codigo text,nombre text, descripcion text, categoria text, marca text, precioCompra text, margenGanancia text,descuento text, stock text,fotoServidor text, fotoLocal text, actualizado text)";
    private static final String tablaUsuarios ="CREATE TABLE Usuarios(idUsuario text, correo text, contraseña text, nombres text, apellidos text, direccion text, telefono text, rol text, actualizado text)";
    private static final String tablaUbicaciones ="CREATE TABLE Ubicaciones(idUbicacion text, nombre text, coordenadas text, direccion text, actualizado text )";
    private static final String tablaMensajes ="CREATE TABLE Mensajes(idMensaje, tipo text, contenido text, enviadoPor text, enviadoA text, fecha text, actualizado text)";
    private static final String tablaAnuncios ="CREATE TABLE Anuncios(idAnuncio text, urlServidor text, urlLocal text, actualizado text)";
    private static final String tablaPedidos ="CREATE TABLE Pedidos(idPedido text, fechaPedido text, fechaEntregado text, direccionEntrega text, cliente text, estado text, mensajeCancelacion text, actualizado text)";
    private static final String tablaPedidos_Productos ="CREATE TABLE Pedido_Producto(idPedido text,idProducto text, cantidad text, precioUnidad text, actualizado text)";
    private static final String tablaCategorias ="CREATE TABLE Categorias(idCategoria text, nombre text, actualizado text)";
    private static final String tablaSubCategorias ="CREATE TABLE SubCategorias(idSubCategoria text, nombre text, categoriaPrincipal text, actualizado text)";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tablaProductos);
        sqLiteDatabase.execSQL(tablaUsuarios);
        sqLiteDatabase.execSQL(tablaUbicaciones);
        sqLiteDatabase.execSQL(tablaMensajes);
        sqLiteDatabase.execSQL(tablaAnuncios);
        sqLiteDatabase.execSQL(tablaPedidos);
        sqLiteDatabase.execSQL(tablaPedidos_Productos);
        sqLiteDatabase.execSQL(tablaUbicaciones);
        sqLiteDatabase.execSQL(tablaCategorias);
        sqLiteDatabase.execSQL(tablaSubCategorias);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        //para hacer la actualizacion de la BD
    }
    public void vaciar(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Productos");
    }
    public Boolean hayRegistros(){
        SQLiteDatabase db = getReadableDatabase();
        long cantidadFilas = DatabaseUtils.queryNumEntries(db,"Productos");
        return cantidadFilas!=0;
    }
    public String administrar_Productos(String accion, String[] datos){
        try {
            ContentValues valoresProducto = new ContentValues();
            String[] whereArgs={String.valueOf(datos[0])};
            String whereClause="idProducto = ?";
            String miTabla = "Productos";
            valoresProducto.put("idProducto", datos[0]);
            if (datos.length>1) {

                valoresProducto.put("nombre", datos[1]);
                valoresProducto.put("descripcion", datos[2]);
                valoresProducto.put("marca", datos[3]);
                valoresProducto.put("presentacion", datos[4]);
                valoresProducto.put("precioCompra", datos[5]);
                valoresProducto.put("margenGanancia", datos[6]);
                valoresProducto.put("stock", datos[7]);
                valoresProducto.put("foto", datos[8]);
                valoresProducto.put("id",datos[9]);
                valoresProducto.put("rev",datos[10]);
                valoresProducto.put("actualizado",datos[11]);
            }
            SQLiteDatabase db = getWritableDatabase();
            switch (accion){
                case "nuevo":
                    this.getWritableDatabase().insert(miTabla,null,valoresProducto);
                    break;
                case "modificar":
                    this.getWritableDatabase().update(miTabla,valoresProducto,whereClause,whereArgs);
                    break;
                case "eliminar":


                    this.getWritableDatabase().delete(miTabla,whereClause,whereArgs);
//db.execSQL("DELETE FROM Productos WHERE idProducto= ''"+datos[2]+"'");
                    break;
            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }
    public Cursor consultar_Productos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Productos ORDER BY nombre", null);
        return cursor;
    }
}