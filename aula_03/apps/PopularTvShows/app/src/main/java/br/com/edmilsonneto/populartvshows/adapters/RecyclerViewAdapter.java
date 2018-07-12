package br.com.edmilsonneto.populartvshows.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.edmilsonneto.populartvshows.R;
import br.com.edmilsonneto.populartvshows.models.TvShow;

import android.content.Context;

import com.squareup.picasso.Picasso;

/**
 * Created by Edmilson Neto on 22/10/2017.
 */

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<TvShow> tvShows;
    private LayoutInflater mInflater;
    private Context context;

    public RecyclerViewAdapter(Context context, List<TvShow> tvShows) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.tvShows = tvShows;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TvShow tvShow = tvShows.get(position);

        holder.txvTexto.setText(tvShow.getName());

        Picasso.with(context).load(tvShow.getPoster_path()).into(holder.imvCapa);
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvTexto;
        ImageView imvCapa;

        ViewHolder(View itemView) {
            super(itemView);
            txvTexto = (TextView) itemView.findViewById(R.id.txvTexto);
            imvCapa = itemView.findViewById(R.id.imvCapa);
        }
    }
}
