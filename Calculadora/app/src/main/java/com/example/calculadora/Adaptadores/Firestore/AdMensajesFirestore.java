package com.example.calculadora.Adaptadores.Firestore;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculadora.Modelos.Mensajes;
import com.example.calculadora.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class AdMensajesFirestore extends FirestoreRecyclerAdapter <Mensajes,RecyclerView.ViewHolder> {
private static final int ENVIADO =0;
private static final int RECIBIDO =1;

 private Activity actividad;
 private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    public AdMensajesFirestore(@NonNull FirestoreRecyclerOptions<Mensajes> options, Activity actividad) {
        super(options);
        this.actividad = actividad;
    }
    @Override
    public int getItemViewType(int posicion){
        Mensajes mensaje = getItem(posicion);
        if (mensaje.getEnviadoPorUsuario()){
            return ENVIADO;
        }else{
            return RECIBIDO;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ENVIADO){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_enviado, parent,false);
            return new EnviadoViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_recibido, parent,false);
            return new RecibidoViewHolder(view);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Mensajes msg) {
        ((EnviadoViewHolder)holder).bind(msg);
/*if (holder instanceof EnviadoViewHolder){
    ((EnviadoViewHolder)holder).bind(msg);
}
else {
    ((RecibidoViewHolder)holder).bind(msg);
}*/
    }



    private class EnviadoViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvMensaje;
        ImageView imageView;
        CardView cvFoto;
        public EnviadoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje=itemView.findViewById(R.id.tvMensaje);
            tvFecha=itemView.findViewById(R.id.tvFecha);
            imageView=itemView.findViewById(R.id.imgFoto);
            cvFoto=itemView.findViewById(R.id.cvFoto);
        }
         void bind(Mensajes msg){
            String fechaMensaje = msg.getFecha().toString();
            tvFecha.setText(fechaMensaje);

            if(msg.getTipo().equals("texto")){
                cvFoto.setVisibility(View.GONE);
                tvMensaje.setVisibility(View.VISIBLE);

                tvMensaje.setText(msg.getContenido());

            } else if (msg.getTipo().equals("imagen")) {
                cvFoto.setVisibility(View.VISIBLE);
                tvMensaje.setVisibility(View.GONE);
                Picasso.get().load(msg.getContenido()).into(imageView);
            }

        }
    }
    private class RecibidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvMensaje;
        ImageView imageView;
        public RecibidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje=itemView.findViewById(R.id.tvMensaje);
            tvFecha=itemView.findViewById(R.id.tvFecha);
            imageView=itemView.findViewById(R.id.imgFoto);
        }
        void bind(Mensajes msg){
            String fechaMensaje = msg.getFecha().toString();
            tvFecha.setText(fechaMensaje);

if(msg.getTipo().equals("texto")){
    imageView.setVisibility(View.GONE);
    tvMensaje.setVisibility(View.VISIBLE);

    tvMensaje.setText(msg.getContenido());

} else if (msg.getTipo().equals("imagen")) {
    imageView.setVisibility(View.VISIBLE);
    tvMensaje.setVisibility(View.GONE);
    Picasso.get().load(msg.getContenido()).into(imageView);
}
        }
    }
}
