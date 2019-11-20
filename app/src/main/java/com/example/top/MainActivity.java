package com.example.top;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclreView;
    @BindView(R.id.containerMain)
    CoordinatorLayout containerMain;

    private ArtistaAdapter adapter;

    // public static final Artista sArtista = new Artista();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configAdapter();
        configRecyclerView();

        // generateArtist();
    }

    private void generateArtist() {
        String[] nombres = {"Carly", "Scottie"};
        String[] apellidos = {"Chaikin", "Thompson"};
        long[] nacimientos = {638409600000L, 374112000000l};
        String[] lugares = {"California", "Virginia"};
        short[] estaturas = {165, 170};
        String[] notas = {"Carly Hannah Chaikin (born March 26, 1990) is an American actress. She began acting in 2009 and received her breakout role two years later, co-starring as Dalia Royce in the ABC sitcom Suburgatory. She played the role until the series' cancellation in 2014, and one year later began playing the role of Darlene in the critically acclaimed USA Network television drama Mr. Robot.\nChaikin was born in Santa Monica, California, to a cardiologist father and a physiotherapist mother. She was raised Jewish. She went to The Archer School for Girls, a high school in Los Angeles. During this time she played a variety of sports, including volleyball, softball, basketball, and soccer.\nIn 2009, Chaikin landed the role of Veronica in the film The Consultants, released December 4, 2010 in the US. The same year, Chaikin starred alongside Miley Cyrus in the film adaptation of Nicholas Sparks' The Last Song, initially released in the US on March 31, 2010. She played the role of Blaze, the antagonist of the film, a rebel that stirs up trouble for Ronnie, played by Cyrus.\nIn 2011, Chaikin was cast as Dalia Oprah Royce in the ABC sitcom Suburgatory alongside Jeremy Sisto and Cheryl Hines. Chaikin's character, Dalia, was the mean girl to Jane Levy's Tessa, Her performance received universal acclaim; she quickly became a fan favorite and her performance a popular highlight of the show. Chaikin originally auditioned for the role of Tessa. Chaikin wrote a series of articles as her character, Dalia, for the magazine, Parade. As her character, Dalia, she shot a music video called \"You Missed A Spot.\" In 2013, Chaikin was nominated for the Critics' Choice Television Award for Best Supporting Actress in a Comedy Series for her role in Suburgatory and was discussed as a potential Emmy nomination. The series ended its run on May 14, 2014.\nChaikin appeared in the 2012 independent film My Uncle Rafael starring John Michael Higgins. In addition to acting, Chaikin is a writer and producer of short films, including Happy Fucking Birthday, and Nowhere to Go, which was honored at the First Glance Film Festival in 2013.\nIn 2014, Chaikin was cast in the USA Network TV series, Mr. Robot, starring Rami Malek (\"Elliot\") and Christian Slater (\"Mr. Robot\"). She plays the programmer Darlene, part of the fsociety group which writes malicious rootkit code. At the 2015 SXSW film festival, the show won the Audience Award for Episodic TV shows. Mr. Robot was picked up for a second season. Mr. Robot has received widespread critical acclaim. Chaikin auditioned for the roles of both Angela and Darlene. She said that it was a great pilot and that the bad-ass nature of the character really appealed to her.\nIn 2015, Chaikin guest-starred on Marc Maron's TV show, Maron, as Tina, a college teaching assistant whom Marc's friend (played by Adam Goldberg) has slept with.", "Scottie Thompson grew up in Richmond, Virginia. With a love for dance and performing Thompson started dancing ballet at an early age. She danced professionally with the Richmond Ballet, before going on to receive a bachelor's degree from Harvard University. While there, she discovered her love for acting. After graduating, she landed a recurring role on the Showtime series \"Brotherhood,\" and has gone on to continue working in various television shows and independent films."};
        String[] fotos = {"https://lsinsight.org/wp-content/uploads/2018/01/carly-chaikin.jpg", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Scottie_Thompson_2011.jpg/430px-Scottie_Thompson_2011.jpg"};

        for (int i = 0; i < 2; i++) {
            Artista artista = new Artista(nombres[i], apellidos[i], nacimientos[i], lugares[i], estaturas[i], notas[i], i + 1, fotos[i]);
            // adapter.add(artista);
            try {
                artista.insert();
                Log.i("DBFlow", "Inserción correcta de datos.");
            } catch (Exception e){
                e.printStackTrace();
                Log.i("DBFlow", "Error al insertar datos.");
            }
        }
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configAdapter() {
        adapter = new ArtistaAdapter(new ArrayList<Artista>(), this);
    }

    private void configRecyclerView() {
        recyclreView.setLayoutManager(new LinearLayoutManager(this));
        recyclreView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setList(getArtistasFromDB());
    }

    private List<Artista> getArtistasFromDB() {
        return SQLite
                .select()
                .from(Artista.class)
                .orderBy(Artista_Table.orden, true)
                .queryList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**************************
     * Metodp implementado por la interfaz OnItemClickListener
     **************************/
    @Override
    public void onItemClick(Artista artista) {
        /*sArtista.setId(artista.getId());
        sArtista.setNombre(artista.getNombre());
        sArtista.setApellido(artista.getApellido());
        sArtista.setFechaNacimiento(artista.getFechaNacimiento());
        sArtista.setEstatura(artista.getEstatura());
        sArtista.setLugarDeNacimiento(artista.getLugarDeNacimiento());
        sArtista.setOrden(artista.getOrden());
        sArtista.setNotas(artista.getNotas());
        sArtista.setFotoUrl(artista.getFotoUrl());*/

        Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
        intent.putExtra(Artista.ID, artista.getId());
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(Artista artista) {
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator!=null){
            vibrator.vibrate(60);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialog_title)
                .setMessage(
                        String.format(Locale.ROOT,
                                getString(R.string.main_dialog_message),
                                artista.getNombreCompleto()))
                .setPositiveButton(R.string.label_dialog_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            artista.delete();
                            adapter.remove(artista);
                            showMessage(R.string.main_message_delete_success);
                        } catch (Exception e){
                            e.printStackTrace();
                            showMessage(R.string.main_message_delete_fail);
                        }

                    }
                })
                .setNegativeButton(R.string.label_dialog_cancel, null);
        builder.show();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            adapter.add(sArtista);
        }
    }*/

    @OnClick(R.id.fab)
    public void addArtist() {
        Intent intent = new Intent(MainActivity.this, AddArtistActivity.class);
        intent.putExtra(Artista.ORDEN, adapter.getItemCount() + 1);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    private void showMessage(int resource) {
        Snackbar.make(containerMain, resource, Snackbar.LENGTH_SHORT).show();
    }
}
