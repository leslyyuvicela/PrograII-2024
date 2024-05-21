package com.example.calculadora.Adaptadores;

import android.app.Activity;
import android.content.Intent;
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
import com.example.calculadora.Modelos.Mensaje;
import com.example.calculadora.Modelos.Productos;
import com.example.calculadora.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.util.List;

public class adaptadorChats extends FirestoreRecyclerAdapter<Mensaje,adaptadorChats.ViewHolder> {
    Activity actividad;
    String contenido;
    DateTime fecha;
    String idUsuario;
    Boolean visto;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public adaptadorChats.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    public adaptadorChats(@NonNull FirestoreRecyclerOptions<Mensaje> options, Activity actividad) {
        super(options);
        this.actividad=actividad;
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Mensaje model) {

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


}
