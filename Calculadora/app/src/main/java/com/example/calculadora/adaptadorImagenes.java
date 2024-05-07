package com.example.calculadora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<Productos> datosProductosArrayList;
    Productos misProductos;
    LayoutInflater layoutInflater;
    TextView tvNombre, tvPrecio, tvPrecioAnterior,tvDescuento;
    RelativeLayout cuadroDescuento;
    DecimalFormat df;

    public adaptadorImagenes(Context context, ArrayList<Productos> datosProductosArrayList) {
        this.context = context;
        this.datosProductosArrayList = datosProductosArrayList;
    }
    @Override
    public int getCount() {
        return datosProductosArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return datosProductosArrayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listview_imagenes, viewGroup, false);
        tvNombre=itemView.findViewById(R.id.lblNombre);
        tvPrecio=itemView.findViewById(R.id.lblPrecio);
        tvPrecioAnterior=itemView.findViewById(R.id.lblPrecioAnterior);
        tvDescuento=itemView.findViewById(R.id.lblDescuento);
        cuadroDescuento= itemView.findViewById(R.id.cuadroDescuento);
        df =new DecimalFormat("#.##");

        try{

            misProductos = datosProductosArrayList.get(i);
            double precioAnterior=0;
            double descuento= misProductos.getDescuento();
            double costo=misProductos.getCosto();
            double ganancia =misProductos.getGanancia();
            double precio = costo*(1+ganancia/100);


            if(descuento==0){
                cuadroDescuento.setVisibility(View.GONE);
                tvPrecioAnterior.setVisibility(View.GONE);
                tvPrecio.setText("$"+df.format(precio));
            } else {
                tvDescuento.setText("- "+df.format(descuento)+"%");
                tvPrecioAnterior.setPaintFlags(tvPrecioAnterior.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                tvPrecioAnterior.setText("$"+df.format(precio));
                if(precio>0){
                    tvPrecio.setText("$"+df.format(precio*(1-descuento/100)));
                }
                else {
                    tvPrecio.setText("Gratis");
                    tvPrecio.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.Red));
                }

                if (descuento>0 && descuento < 30 ) {
                    cuadroDescuento.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.Yellow));

                } else if (descuento>=30 && descuento < 60) {
                    cuadroDescuento.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.Orange));
                } else if (descuento>=60) {
                    cuadroDescuento.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.Red));
                }
            }
            tvNombre.setText(misProductos.getNombre());


            ImageView imgView = itemView.findViewById(R.id.imgFoto);
            Bitmap bitmap = BitmapFactory.decodeFile(misProductos.getFoto());
            imgView.setImageBitmap(bitmap);

        }catch (Exception e){
            Toast.makeText(context, "Error al mostrar datos: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}