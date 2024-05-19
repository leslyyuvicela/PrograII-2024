package com.example.calculadora.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculadora.Actividades.Agregar_Producto;
import com.example.calculadora.Modelos.Productos;
import com.example.calculadora.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class AdaptadorProductos extends FirestoreRecyclerAdapter<Productos,AdaptadorProductos.ViewHolder> {
DecimalFormat df =new DecimalFormat("#.##");
Activity actividad;
String campo, filtro;
String nombre;
String marca;
String codigo;
String descripcion;
String urlFoto;
private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdaptadorProductos(@NonNull FirestoreRecyclerOptions<Productos> options, Activity actividad, String campo, String filtro) {
        super(options);
        this.actividad = actividad;
        this.campo = campo;
        this.filtro = filtro;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Productos p) {
        DocumentSnapshot ds = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = ds.getId();
        codigo = p.getCodigo();
        nombre = p.getNombre();
        marca = p.getMarca();
        descripcion = p.getDescripcion();
        double descuento= p.getDescuento();
        double precioCompra=p.getPrecioCompra();
        double  margenGanancia =p.getMargenGanancia();
        urlFoto = p.getUrlFoto();


        if(aplicarFiltro(nombre) || aplicarFiltro(marca) || aplicarFiltro(descripcion)){
            holder.tvNombre.setText(p.getNombre());

        double precio = precioCompra*(1+margenGanancia/100);
        if(descuento==0){
            holder.cuadroDescuento.setVisibility(View.GONE);
            holder.tvPrecioAnterior.setVisibility(View.GONE);
            holder.tvPrecio.setText("$"+df.format(precio));
        } else {
            holder.tvDescuento.setText("- "+df.format(descuento)+"%");
            holder.tvPrecioAnterior.setPaintFlags(holder.tvPrecioAnterior.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.tvPrecioAnterior.setText("$"+df.format(precio));
            if(precio>0){
                holder.tvPrecio.setText("$"+df.format(precio*(1-descuento/100)));
            } else {
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
        try {
                Picasso.with(holder.imgProducto.getContext()).load(urlFoto).resize(150,150).into(holder.imgProducto);

        }catch (Exception e){
            Toast.makeText(actividad,e.getMessage(),Toast.LENGTH_SHORT);
        }

    //    Bitmap bitmap = BitmapFactory.decodeFile(p.getUrlFoto());
     //   holder.imgProducto.setImageBitmap(bitmap);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editarProducto(id);
                    actividad.finish();
                }
            });
   }
        else{
holder.itemView.setVisibility(View.GONE);
        }
    }

    private boolean aplicarFiltro(String campo) {
       return campo.toLowerCase().trim().matches("^"+filtro+".*");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_producto_principal, parent, false);
       return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvPrecioAnterior,tvDescuento;
        ImageView imgProducto;
        RelativeLayout cuadroDescuento;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre=itemView.findViewById(R.id.lblNombre);
            tvPrecio=itemView.findViewById(R.id.lblPrecio);
            tvPrecioAnterior=itemView.findViewById(R.id.lblPrecioAnterior);
            tvDescuento=itemView.findViewById(R.id.lblDescuento);
            cuadroDescuento= itemView.findViewById(R.id.cuadroDescuento);
            imgProducto = itemView.findViewById(R.id.imgFoto);
        }
    }
    private void borrarProducto(String id){
        fStore.collection("productos").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void editarProducto(String id){
        Intent editarProducto = new Intent(actividad, Agregar_Producto.class);
        editarProducto.putExtra("idProducto",id);
        actividad.startActivity(editarProducto);
    }


}
