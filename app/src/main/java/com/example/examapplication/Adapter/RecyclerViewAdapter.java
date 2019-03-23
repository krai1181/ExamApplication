package com.example.examapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.examapplication.Activites.MainActivity;
import com.example.examapplication.Activites.MovieDetailsActivity;
import com.example.examapplication.Objects.Movie;
import com.example.examapplication.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    ArrayList<Movie> movies;
    private Context context;
    public final int REQUEST_CODE = 1232;

    FragmentManager fragmentManager;


    //constructors
    public RecyclerViewAdapter(ArrayList<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    public void add(List<Movie> movie) {
        movies.add((Movie) movie);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View item = inflater.inflate(R.layout.row_moview_recyclerview, viewGroup, false);
        return new MovieViewHolder(item);

    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        final Movie movie = movies.get(position);
        Log.d(TAG, "onBindViewHolder: Movies size is : " + movies.size());

        Collections.sort(movies);


        Picasso.with(context).
                load(movie.getImage())
                .into(movieViewHolder.movieImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });


        final String str = Arrays.toString(movie.getGenre());
        movieViewHolder.genreTextView.setText(str);
        movieViewHolder.ratingTextView.setText(String.valueOf(movie.getRating()));
        movieViewHolder.titleTextView.setText(movie.getTitle());
        movieViewHolder.releaseYearTextView.setText(String.valueOf(movie.getReleaseYear()));

        movieViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, movie.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie_title", movie.getTitle());
                intent.putExtra("movie_image", movie.getImage());
                intent.putExtra("movie_rating", String.valueOf(movie.getRating()));
                intent.putExtra("movie_release_year", String.valueOf(movie.getReleaseYear()));
                intent.putExtra("movie_genre", str);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}


class MovieViewHolder extends RecyclerView.ViewHolder {

    TextView titleTextView, releaseYearTextView, ratingTextView, genreTextView;
    ImageView movieImageView;
    ConstraintLayout parentLayout;

    public MovieViewHolder(View itemView) {
        super(itemView);

        titleTextView = itemView.findViewById(R.id.title_text_view);
        releaseYearTextView = itemView.findViewById(R.id.release_year_text_view);
        ratingTextView = itemView.findViewById(R.id.rating_text_view);
        genreTextView = itemView.findViewById(R.id.genre_text_view);
        movieImageView = itemView.findViewById(R.id.movie_image_view);

        parentLayout = itemView.findViewById(R.id.parent_layout);
    }
}
