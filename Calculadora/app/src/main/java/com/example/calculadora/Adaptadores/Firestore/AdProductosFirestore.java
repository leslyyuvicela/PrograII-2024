package com.example.calculadora.Adaptadores.Firestore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class AdProductosFirestore extends FirestoreRecyclerAdapter<Productos, AdProductosFirestore.ViewHolder> {
DecimalFormat df =new DecimalFormat("#.##");
Activity actividad;
String campo, filtro;
String nombre;
String marca;
String codigo;
String descripcion;
String urlFoto;
int cantidadCarrito = 0;
FirebaseAuth auth;
FirebaseUser usuario;
    String idPedido;
    String idPedidoProducto;
 String idProducto;
 double precio = 0;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdProductosFirestore(@NonNull FirestoreRecyclerOptions<Productos> options, Activity actividad, String campo, String filtro) {
        super(options);
        this.actividad = actividad;
        this.campo = campo;
        this.filtro = filtro;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Productos p) {
        DocumentSnapshot ds = getSnapshots().getSnapshot(holder.getAdapterPosition());
        idProducto = ds.getId();
        codigo = p.getCodigo();
        nombre = p.getNombre();
        marca = p.getMarca();
        descripcion = p.getDescripcion();
        double descuento= p.getDescuento();
        double precioCompra=p.getPrecioCompra();
        double  margenGanancia =p.getMargenGanancia();
        urlFoto = p.getUrlFoto();
        auth= FirebaseAuth.getInstance();
        usuario= auth.getCurrentUser();
        cantidadCarrito=0;
        obtenerCantidad();


        if(aplicarFiltro(nombre) || aplicarFiltro(marca) || aplicarFiltro(descripcion)){
            holder.tvNombre.setText(p.getNombre());
            if(cantidadCarrito > 0){
                holder.btnCarrito.setVisibility(View.GONE);
                holder.btnAgregar.setVisibility(View.VISIBLE);
                holder.btnQuitar.setVisibility(View.VISIBLE);
                holder.tvCantidad.setVisibility(View.VISIBLE);
                holder.tvCantidad.setText(String.valueOf(cantidadCarrito));

            }else{
                holder.btnCarrito.setVisibility(View.VISIBLE);
                holder.btnAgregar.setVisibility(View.GONE);
                holder.btnQuitar.setVisibility(View.GONE);
                holder.tvCantidad.setVisibility(View.GONE);
            }
         precio = precioCompra*(1+margenGanancia/100);
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
                Picasso.get().load(urlFoto).resize(150,150).into(holder.imgProducto);

        }catch (Exception e){
            Toast.makeText(actividad,e.getMessage(),Toast.LENGTH_SHORT);
        }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editarProducto(idProducto);
                    actividad.finish();
                }
            });
   }
        else{
holder.itemView.setVisibility(View.GONE);
        }
        holder.btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarACarrito();
            }
        });
    }

    private void agregarACarrito() {
        Map<String, Object> map = new HashMap<>();
        map.put("cantidad", cantidadCarrito+1);
        map.put("idPedido",idPedido);
        map.put("idProducto", idProducto);
        map.put("precioUnidad",precio);
        if(cantidadCarrito==0){
            fStore.collection("pedido_producto").add(map);
        }
        else{
            fStore.collection("pedido_producto").document(idPedidoProducto).update(map);
        }
    }
    private void quitarDeCarrito(){
        Map<String, Object> map = new HashMap<>();
        map.put("cantidad", cantidadCarrito-1);
        map.put("idPedido",idPedido);
        map.put("idProducto", idProducto);
        map.put("precioUnidad",precio);
        fStore.collection("pedido_producto").document(idPedidoProducto).update(map);
    }

    private void obtenerCantidad() {
        cantidadCarrito=0;
        fStore.collection("pedidos").whereEqualTo("cliente",usuario.getUid()).whereEqualTo("estado", "actual").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot pedido: task.getResult()
                                 ) {
                                idPedido = pedido.getId();
                                fStore.collection("pedido_producto").whereEqualTo("idPedido",idPedido)
                                        .whereEqualTo("idProducto",idProducto).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                if(task2.isSuccessful()){
                                                    for (QueryDocumentSnapshot detallePedido:task2.getResult()) {
                                                        idPedidoProducto = detallePedido.getId();
                                                        cantidadCarrito=detallePedido.getLong("cantidad").intValue();
                                                    }
                                                }else{
                                                    cantidadCarrito=0;
                                                }
                                            }
                                        });
                            }
                        }else {
                            Toast.makeText(actividad.getApplicationContext(),"no existe el pedido actual",Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    private boolean aplicarFiltro(String campo) {
       return campo.toLowerCase().trim().matches("^"+filtro+".*");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_producto, parent, false);
       return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvPrecioAnterior,tvDescuento,tvCantidad;
        ImageView imgProducto;
        RelativeLayout cuadroDescuento;
        ImageButton btnCarrito;
        Button btnAgregar,btnQuitar;
        @SuppressLint("ResourceType")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre=itemView.findViewById(R.id.lblNombre);
            tvPrecio=itemView.findViewById(R.id.lblPrecio);
            tvPrecioAnterior=itemView.findViewById(R.id.lblPrecioAnterior);
            tvDescuento=itemView.findViewById(R.id.lblDescuento);
            cuadroDescuento= itemView.findViewById(R.id.cuadroDescuento);
            imgProducto = itemView.findViewById(R.id.imgFoto);
            btnAgregar= itemView.findViewById(R.id.btnSumarCarrito);
            btnCarrito= itemView.findViewById(R.id.btnAñadirAPedido);
            btnQuitar= itemView.findViewById(R.id.btnRestarCarrito);
            tvCantidad= itemView.findViewById(R.id.tvCantidadPedida);
        }
    }

    private void editarProducto(String id){
        Intent editarProducto = new Intent(actividad, Agregar_Producto.class);
        editarProducto.putExtra("idProducto",id);
        actividad.startActivity(editarProducto);
    }


}
