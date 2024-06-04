package com.example.calculadora.Adaptadores.Firestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.calculadora.R;

import com.squareup.picasso.Picasso;

public class AdAnunciosFirestore extends PagerAdapter {
private Context contexto;
private String[] urls;
    public AdAnunciosFirestore(Context contexto, String[] urls) {
        this.contexto=contexto;
        this.urls= urls;
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View view = inflater.inflate(R.layout.imagen_anuncio, container,false);
        ImageView imageView = view.findViewById(R.id.imageView);
        Picasso.get().load(urls[position]).into(imageView);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
