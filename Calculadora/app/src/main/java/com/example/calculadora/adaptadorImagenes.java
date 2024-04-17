package com.example.calculadora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<Peliculas> datosPeliculasArrayList;
    Peliculas misPeliculas;
    LayoutInflater layoutInflater;

    public adaptadorImagenes(Context context, ArrayList<Peliculas> datosPeliculasArrayList) {
        this.context = context;
        this.datosPeliculasArrayList = datosPeliculasArrayList;
    }
    @Override
    public int getCount() {
        return datosPeliculasArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return datosPeliculasArrayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listview_imagenes, viewGroup, false);
        try{
            misPeliculas = datosPeliculasArrayList.get(i);


            TextView tempVal = itemView.findViewById(R.id.lblTitulo);
            tempVal.setText(misPeliculas.getTitulo());


            tempVal = itemView.findViewById(R.id.lblSinopsis);
            tempVal.setText("Sinopsis: " +misPeliculas.getSinopsis());

            tempVal = itemView.findViewById(R.id.lblDuracion);
            tempVal.setText(misPeliculas.getDuracion());

            tempVal = itemView.findViewById(R.id.lblActor);
            tempVal.setText(misPeliculas.getActor());



            ImageView imgView = itemView.findViewById(R.id.imgFoto);
            Bitmap bitmap = BitmapFactory.decodeFile(misPeliculas.getFoto());
            imgView.setImageBitmap(bitmap);

        }catch (Exception e){
            Toast.makeText(context, "Error al mostrar datos: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}
