package com.example.calculadora.Adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculadora.Principal;
import com.example.calculadora.Productos;
import com.example.calculadora.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.viewHolderProducto> {

    ArrayList<Productos> listaProductos;

    DecimalFormat df =new DecimalFormat("#.##");
    double costo, descuento, ganancia, precioVenta;


    public static class viewHolderProducto extends RecyclerView.ViewHolder{
TextView tvNombre, tvPrecio, tvPrecioAnterior, tvDescuento;
ImageView imgProducto;
RelativeLayout cuadroDescuento;

        public viewHolderProducto(@NonNull View itemView) {
            super(itemView);
            tvNombre=itemView.findViewById(R.id.lblNombre);
            tvPrecio=itemView.findViewById(R.id.lblPrecio);
            tvPrecioAnterior=itemView.findViewById(R.id.lblPrecioAnterior);
            tvDescuento=itemView.findViewById(R.id.lblDescuento);
            cuadroDescuento= itemView.findViewById(R.id.cuadroDescuento);
            imgProducto = itemView.findViewById(R.id.imgFoto);
        }
    }
    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductos.viewHolderProducto holder, int position) {
        Productos producto = listaProductos.get(position);
        descuento = producto.getDescuento();
        costo = producto.getCosto();
        ganancia=producto.getGanancia();
        precioVenta = costo*(1+ganancia/100);

        if(descuento==0){
            holder.cuadroDescuento.setVisibility(View.GONE);
            holder.tvPrecioAnterior.setVisibility(View.GONE);
            holder.tvPrecio.setText("$"+df.format(precioVenta));
        } else {
            holder.tvDescuento.setText("- "+df.format(descuento)+"%");
            holder.tvPrecioAnterior.setPaintFlags(holder.tvPrecioAnterior.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.tvPrecioAnterior.setText("$"+df.format(precioVenta));
            if(precioVenta>0){
                holder.tvPrecio.setText("$"+df.format(precioVenta*(1-descuento/100)));
            }
            else {
                holder.tvPrecio.setText("Gratis");
                holder.tvPrecio.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Red));
            }

            if (descuento>0 && descuento < 30 ) {
                holder.cuadroDescuento.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Yellow));

            } else if (descuento>=30 && descuento < 60) {
                holder.cuadroDescuento.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Orange));
            } else if (descuento>=60) {
                holder.cuadroDescuento.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.Red));
            }
        }
        holder.tvNombre.setText(producto.getNombre());

        Bitmap bitmap = BitmapFactory.decodeFile(producto.getFoto());
        holder.imgProducto.setImageBitmap(bitmap);
    }

    @NonNull
    @Override
    public viewHolderProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_imagenes, parent,false);
        return new viewHolderProducto(v);
    }

    public AdaptadorProductos(ArrayList<Productos> listaProductos) {
        this.listaProductos = (ArrayList<Productos>) listaProductos;
    }
}
