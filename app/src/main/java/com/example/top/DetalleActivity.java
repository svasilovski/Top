package com.example.top;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleActivity extends AppCompatActivity {

    @BindView(R.id.imgFoto)
    AppCompatImageView imgFoto;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.etNombre)
    TextInputEditText etNombre;
    @BindView(R.id.etApellidos)
    TextInputEditText etApellidos;
    @BindView(R.id.etFechaNacimiento)
    TextInputEditText etFechaNacimiento;
    @BindView(R.id.etEdad)
    TextInputEditText etEdad;
    @BindView(R.id.etEstatura)
    TextInputEditText etEstatura;
    @BindView(R.id.etOrden)
    TextInputEditText etOrden;
    @BindView(R.id.etLugarNacimiento)
    TextInputEditText etLugarNacimiento;
    @BindView(R.id.etNotas)
    TextInputEditText etNotas;
    @BindView(R.id.containerMain)
    NestedScrollView containerMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Artista mArtista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ButterKnife.bind(this);

        configArtista(getIntent());
        configActionBar();
        configImageView(mArtista.getFotoUrl());
        configCalendar();
    }

    private void configArtista(Intent intent) {
        //mArtista = MainActivity.sArtista;

        getArtista(intent.getLongExtra(Artista.ID, 0));

        etNombre.setText(mArtista.getNombre());
        etApellidos.setText(mArtista.getApellido());
        etFechaNacimiento.setText(
                new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                .format(mArtista.getFechaNacimiento())
        );
        etEdad.setText(getEdad(mArtista.getFechaNacimiento()));
        etEstatura.setText(String.valueOf(mArtista.getEstatura()));
        etOrden.setText(String.valueOf(mArtista.getOrden()));
        etLugarNacimiento.setText(mArtista.getLugarDeNacimiento());
        etNotas.setText(mArtista.getNotas());
    }

    private void getArtista(long id) {
        mArtista = SQLite
                .select()
                .from(Artista.class)
                .where(Artista_Table.id.is(id))
                .querySingle();
    }

    private String getEdad(long fechaNacimiento) {
        Long time = Calendar.getInstance().getTimeInMillis()/1000 - fechaNacimiento/1000;
        final int years = Math.round(time)/31536000;
        return String.valueOf(years);
    }

    private void configActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        configTitle();
    }

    private void configTitle() {
        toolbarLayout.setTitle(mArtista.getNombreCompleto());
    }

    private void configImageView(String fotoUrl) {
        if(fotoUrl != null){
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop();

            Glide.with(this)
                    .load(fotoUrl)
                    .apply(options)
                    .into(imgFoto);
        } else {
            imgFoto.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_photo_size_select_actual));
        }

        mArtista.setFotoUrl(fotoUrl);
    }

    private void configCalendar() {
    }
}
