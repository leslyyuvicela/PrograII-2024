package com.example.calculadora.Adaptadores;

import static com.example.calculadora.R.*;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculadora.Modelos.Productos;

import java.util.List;

public class adaptadorRecyclerView extends RecyclerView.Adapter<adaptadorRecyclerView.ViewHolder> {
    private List<Productos> productos;
    public adaptadorRecyclerView(List<Productos> productos){
        this.productos=productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(layout.vista_producto, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
Productos p= productos.get(position);
String nombre = p.getNombre();
Double precioCompra = p.getPrecioCompra();
Double margenGanancia = p.getMargenGanancia();
Double descuento = p.getDescuento();
Double precioVenta = precioCompra*(1+margenGanancia/100);

if(descuento==0){
    holder.cuadroDescuento.setVisibility(View.GONE);
    holder.tvPrecioAnterior.setVisibility(View.GONE);
    holder.tvPrecio.setText("$"+String.format("%.2f",precioVenta));
} else {
    holder.tvDescuento.setText("- "+descuento+"%");
    holder.tvPrecioAnterior.setText("$"+String.format("%.2f",precioVenta));
    holder.tvPrecio.setText("$"+precioVenta*(1-descuento/100));
    holder.tvPrecioAnterior.setPaintFlags(holder.tvPrecioAnterior.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    if (descuento>0 && descuento < 30 ) {
        holder.cuadroDescuento.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), color.Yellow));

} else if (descuento>=30 && descuento < 60) {
        holder.cuadroDescuento.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), color.Orange));
} else if (descuento>=60) {
        holder.cuadroDescuento.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), color.Red));
}
}
        Bitmap bitmap = BitmapFactory.decodeFile(p.getUrlFoto());
        holder.img.setImageBitmap(bitmap);
        holder.tvNombre.setText(nombre);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout cuadroDescuento;
        TextView tvNombre, tvPrecio, tvPrecioAnterior, tvDescuento;
        ImageView img;
        public ViewHolder(View vista){
            super(vista);

            tvNombre= vista.findViewById(id.lblNombre);
            tvPrecio= vista.findViewById(id.lblPrecio);
            tvPrecio= vista.findViewById(id.lblPrecioAnterior);
            tvPrecio= vista.findViewById(id.lblDescuento);
            cuadroDescuento=vista.findViewById(id.cuadroDescuento);
            img=vista.findViewById(id.imgFoto);
        }

    }

}
