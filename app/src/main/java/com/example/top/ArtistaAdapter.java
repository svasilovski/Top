package com.example.top;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ViewHolder> {

    private List<Artista> artistas;
    private Context context;
    private OnItemClickListener listener;

    public ArtistaAdapter(List<Artista> artistas, OnItemClickListener listener) {
        this.artistas = artistas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                                        R.layout.item_artist, parent, false);

        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Artista artista = this.artistas.get(position);

        holder.setListner(artista, listener);

        holder.tvNombre.setText(artista.getNombreCompleto());
        holder.tvOrden.setText(String.valueOf(artista.getOrden()));

        if (artista.getFotoUrl() != null){
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.centerCrop();
            options.placeholder(R.drawable.ic_sentiment_satisfied);

            Glide.with(context)
                    .load(artista.getFotoUrl())
                    .apply(options)
                    .into(holder.imgFoto);
        } else {
            holder.imgFoto.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_account_box));
        }
    }

    @Override
    public int getItemCount() {
        return this.artistas.size();
    }

    public void add(Artista artista){
        if (!this.artistas.contains(artista)) {
            this.artistas.add(artista);
            notifyDataSetChanged();
        }
    }

    public void setList(List<Artista> list) {
        this.artistas=list;
        notifyDataSetChanged();
    }

    public void remove(Artista artista) {
        if(artistas.contains(artista)){
            artistas.remove(artista);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imgFoto)
        AppCompatImageView imgFoto;
        @BindView(R.id.tvNombre)
        AppCompatTextView tvNombre;
        @BindView(R.id.tvOrden)
        AppCompatTextView tvOrden;
        @BindView(R.id.containerArtist)
        RelativeLayout containerArtist;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setListner(Artista artista, OnItemClickListener listener){
            containerArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(artista);
                }
            });

            containerArtist.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(artista);
                    return true;
                }
            });
        }
    }
}
