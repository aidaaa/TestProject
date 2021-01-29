package com.example.testproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.R;
import com.example.testproject.data.net.model.MoviesData;
import com.example.testproject.data.room.model.Genres;
import com.example.testproject.data.room.model.MoviesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context context;
    private List<MoviesModel> list = new ArrayList<>();
    private List<Genres> genresList = new ArrayList<>();

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movies_list_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.txt.setText(list.get(position).getTitle());
        String genres = "genres: ";
        for (int i = 0; i < genresList.size(); i++) {
            int id = genresList.get(i).getId();
            if (id == list.get(position).getId()) {
                genres += genresList.get(i).getGenres_name();
                genres+="  ";
            }
        }
        holder.txt_genres.setText(genres);
        Picasso.with(context).load(list.get(position).getPoster())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<MoviesModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setGenresList(List<Genres> genresList) {
        this.genresList = genresList;
        notifyDataSetChanged();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txt, txt_genres;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt_movies);
            img = itemView.findViewById(R.id.img_movie);
            txt_genres = itemView.findViewById(R.id.txt_genres);
        }
    }
}
