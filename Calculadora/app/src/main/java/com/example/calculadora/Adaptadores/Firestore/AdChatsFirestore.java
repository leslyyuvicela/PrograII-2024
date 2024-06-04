package com.example.calculadora.Adaptadores.Firestore;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculadora.Modelos.Mensajes;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

public class AdChatsFirestore extends FirestoreRecyclerAdapter<Mensajes, AdChatsFirestore.ViewHolder> {
    Activity actividad;
    String contenido;
    DateTime fecha;
    String idUsuario;
    Boolean visto;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public AdChatsFirestore.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    public AdChatsFirestore(@NonNull FirestoreRecyclerOptions<Mensajes> options, Activity actividad) {
        super(options);
        this.actividad=actividad;
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Mensajes model) {

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
