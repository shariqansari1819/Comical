package com.codebosses.comical.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.RowMoviesBinding;
import com.codebosses.comical.pojo.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Movie> movieList = new ArrayList<>();

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowMoviesBinding moviesBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_movies, parent, false);
        return new MoviesHolder(moviesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {
        holder.moviesBinding.setMovie(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MoviesHolder extends RecyclerView.ViewHolder {

        RowMoviesBinding moviesBinding;

        public MoviesHolder(@NonNull RowMoviesBinding rowMoviesBinding) {
            super(rowMoviesBinding.getRoot());
            this.moviesBinding = rowMoviesBinding;
        }
    }
}
