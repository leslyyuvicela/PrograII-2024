package com.example.calculadora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GestorImagenes {
    public GestorImagenes(){
        firebaseStorage =FirebaseStorage.getInstance();
    }
    FirebaseStorage firebaseStorage;
    public void guardarFStore(Context contexto, String url,String ubicacion,String nombre, CallbackImagen callback){
File archivo = new File(url);
if (!archivo.exists()){
    callback.onFailure(new IOException("Foto no encontrada"));
    return;
}
Uri uriFoto = Uri.fromFile(archivo);
        StorageReference ref = firebaseStorage.getReference().child(ubicacion+"/"+archivo.getName());
        ref.putFile(uriFoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        callback.onSucces(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e);
                    }
                });
            }
        });
    }
    public void guardarSQLite(Context contexto, String url,String ubicacion,String nombre, CallbackImagen callback) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    String urlLocal = guardarBitMapLocal(contexto, bitmap, ubicacion, nombre);
                    callback.onSucces(urlLocal);
                } catch (IOException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                callback.onFailure(e);
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable){

            }
        });
    }

    private String guardarBitMapLocal(Context contexto, Bitmap bitmap, String ubicacion, String nombre) throws IOException{
        File carpeta = new File(contexto.getFilesDir(),ubicacion);
        if(!carpeta.exists()){
            carpeta.mkdirs();
        }
        File archivo = new File(carpeta,nombre);
        FileOutputStream fos = new FileOutputStream(archivo);
        bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
        fos.close();
        return archivo.getAbsolutePath();
    }
    public interface CallbackImagen{
        void onSucces(String url);
        void onFailure(Exception e);
    }
}
