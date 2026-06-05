package com.example.practicacontenedoresui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno> {

    public AlumnoAdapter(Context context, List<Alumno> alumnos) {
        super(context, 0, alumnos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_alumno, parent, false);
        }

        Alumno alumno = getItem(position);
        if (alumno != null) {
            ((TextView) convertView.findViewById(R.id.tvNombre)).setText(alumno.getNombres());
            ((TextView) convertView.findViewById(R.id.tvCorreo)).setText(alumno.getCorreo());
            ((TextView) convertView.findViewById(R.id.tvTelefono)).setText(alumno.getTelefono());

            ImageView ivFoto = convertView.findViewById(R.id.ivFoto);
            String fotoUrl = MainActivity.BASE_FOTO_URL + alumno.getFoto();
            Glide.with(getContext())
                    .load(fotoUrl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(ivFoto);
        }

        return convertView;
    }
}
