package com.example.examapplication.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.examapplication.R;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";
    TextView movieTitleTextView,movieReleaseYearTextView, movieRatingTextView, movieGenreTextView;
    ImageView movieImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieTitleTextView = findViewById(R.id.movie_details_title_text_view);
        movieReleaseYearTextView = findViewById(R.id.movie_details_release_year_text_view);
        movieRatingTextView = findViewById(R.id.movie_details_rating_text_view);
        movieGenreTextView = findViewById(R.id.movie_details_genre_text_view);
        movieImageView = findViewById(R.id.movie_details_movie_image_view);

        //home button
       if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //get extra
        getIncomingIntent();



       movieImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();



        }
        return super.onOptionsItemSelected(item);
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: ");
        if(getIntent().hasExtra("movie_title") && getIntent().hasExtra("movie_image")
                 && getIntent().hasExtra("movie_rating") && getIntent().hasExtra("movie_release_year")
                && getIntent().hasExtra("movie_genre")){
            Log.d(TAG, "getIncomingIntent: found extras");

            String movieTitle = getIntent().getStringExtra("movie_title");
            String movieImageURL = getIntent().getStringExtra("movie_image");
            String movieRating = getIntent().getStringExtra("movie_rating");
            String movieYear = getIntent().getStringExtra("movie_release_year");
            String movieGenre = getIntent().getStringExtra("movie_genre");

            setMovieDetails(movieTitle,movieImageURL,movieRating,movieYear,movieGenre);
        }

    }



    private void setMovieDetails(String title, String image, String rating, String year, String ganre){
        movieTitleTextView.setText(title);
        movieGenreTextView.setText(ganre);
        movieRatingTextView.setText(rating);
        movieReleaseYearTextView.setText(year);

        Picasso.with(this).
                load(image)
                .into(movieImageView);
    }
}
