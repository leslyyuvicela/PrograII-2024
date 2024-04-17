package com.example.calculadora;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "db_Peliculas";
    private static final int v=1;
    private static final String SQldb = "CREATE TABLE Peliculas( idPelicula txt,titulo text, duracion text, sinopsis text, actor text, foto text,id text,rev text)";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQldb);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        //para hacer la actualizacion de la BD
    }
    public void vaciar(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Peliculas");
    }
    public Boolean hayRegistros(){
        SQLiteDatabase db = getReadableDatabase();
        long cantidadFilas = DatabaseUtils.queryNumEntries(db,"Peliculas");
        return cantidadFilas!=0;
    }
    public String administrar_Peliculas(String accion, String[] datos){
        try {
            ContentValues valoresPelicula = new ContentValues();
            String[] whereArgs={String.valueOf(datos[0])};
            String whereClause="idPelicula = ?";
            String miTabla = "Peliculas";
            valoresPelicula.put("idPelicula", datos[0]);
            if (datos.length>1) {

                valoresPelicula.put("titulo", datos[1]);
                valoresPelicula.put("duracion", datos[2]);
                valoresPelicula.put("sinopsis", datos[3]);
                valoresPelicula.put("actor", datos[4]);
                valoresPelicula.put("foto", datos[5]);
                valoresPelicula.put("id",datos[6]);
                valoresPelicula.put("rev",datos[7]);
            }
            SQLiteDatabase db = getWritableDatabase();
            switch (accion){
                case "nuevo":
                    this.getWritableDatabase().insert(miTabla,null,valoresPelicula);
                    break;
                case "modificar":
                    this.getWritableDatabase().update(miTabla,valoresPelicula,whereClause,whereArgs);
                    break;
                case "eliminar":


                    this.getWritableDatabase().delete(miTabla,whereClause,whereArgs);
//db.execSQL("DELETE FROM Peliculas WHERE idPelicula= ''"+datos[2]+"'");
                    break;
            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }
    public Cursor consultar_Peliculas(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Peliculas ORDER BY titulo", null);
        return cursor;

    }
}
