package com.example.examapplication.Activites;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.examapplication.Adapter.RecyclerViewAdapter;
import com.example.examapplication.DB.DataBaseHelper;
import com.example.examapplication.Objects.Movie;
import com.example.examapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieListActivity extends AppCompatActivity {


    private static final String LIST_SEPARATOR = "::";

    private static final String TAG = "MovieListActivity";

    RecyclerView recyclerView;
    public static RecyclerViewAdapter adapter;
    public static ArrayList<Movie> moviesList;
    Set<Movie> movieSet;


    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;

    Button addMovieButton;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        moviesList = new ArrayList<>();

        movieSet = new HashSet<>();

        final Intent i = new Intent(this, MainActivity.class);


        addMovieButton = findViewById(R.id.movie_list_add_movie_button);
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });


        //create DBHelper
        dataBaseHelper = new DataBaseHelper(this);
        db = dataBaseHelper.getReadableDatabase();

        //read from db
        readFromData();


        //recycler view
        initRecyclerView();
        Log.d(TAG, "onCreate: load movies size: " + moviesList.size());


    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: start");
        super.onStart();
        Movie newMovie = checkFromDataBase();
        if(!moviesList.contains(newMovie))
            moviesList.add(newMovie);

        //recycler view
        initRecyclerView();
    }

    public void readFromData() {
        cursor = dataBaseHelper.readFromData(db);

        Log.d(TAG, "readFromData: cursor count " + cursor.getCount());
        cursor.moveToFirst();

        do {
            String[] strGenre = convertStringToArray(cursor.getString(4));
            Movie someMovie = new Movie(cursor.getString(0), cursor.getString(1),
                    cursor.getDouble(2), cursor.getInt(3), strGenre);
                    moviesList.add(someMovie);
            Log.d(TAG, "readFromData: movie list size  " + moviesList.size());
        } while (cursor.moveToNext());

    }

    public Movie checkFromDataBase() {
        cursor = dataBaseHelper.readFromData(db);

        Movie m;
        Log.d(TAG, "readFromData: cursor count " + cursor.getCount());
        cursor.moveToFirst();

        do {
            String[] strGenre = convertStringToArray(cursor.getString(4));
             m = new Movie(cursor.getString(0), cursor.getString(1),
                    cursor.getDouble(2), cursor.getInt(3), strGenre);
            Log.d(TAG, "readFromData: movie list size  " + moviesList.size());
        } while (cursor.moveToNext());

        return m;
    }

    public void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init");
        recyclerView = findViewById(R.id.recyclerViewMovies);
        adapter = new RecyclerViewAdapter(moviesList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public static String[] convertStringToArray(String str) {
        String[] string = str.split(LIST_SEPARATOR);
        return string;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //close db
        cursor.close();
        dataBaseHelper.close();
        db.close();
    }
}
