package com.example.examapplication.Activites;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examapplication.Adapter.RecyclerViewAdapter;
import com.example.examapplication.DB.DataBaseHelper;
import com.example.examapplication.Objects.Movie;
import com.example.examapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    TextView jsonResultTextView;
    public static String TAG = "SplashActivity";
    private static final String LIST_SEPARATOR = "::";


    static DataBaseHelper dataBaseHelper;
    List<Movie> movies;
    private Boolean isSuccsess = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: loading.....");

        //progress bar
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        //db
        dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.deleteData();


        //work with json
        jsonResultTextView = findViewById(R.id.jsonTextView);

        OkHttpClient client = new OkHttpClient();

        String url = "https://api.androidhive.info/json/movies.json";

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: called");

                    String someResponse = response.body().string();
                  //  Log.d(TAG, "onResponse: " + someResponse);

                    Gson gson = new GsonBuilder().create();

                     movies = Arrays.asList(gson.fromJson(someResponse, Movie[].class));
                     isSuccsess = true;
                     for(Movie m:movies){
                         Log.d(TAG, "onResponse: Title " + m.getTitle() + " utl Image "+ m.getImage() + " Rating " + m.getRating()
                         + "genre " + m.getGenre() + "MOVIES SIZE : " + movies.size());

                         //save to db
                         addDataToDB(m);
                         dataBaseHelper.close();

                         Log.d(TAG, "onResponse: movies.size" + movies.size());
                     }
                    if(isSuccsess){

                        callActivity(MovieListActivity.class);
                    }
                }
            }
        });



    }

    // adding data to SQLiteDatabase
    public static void addDataToDB(Movie movie){
       String stringGenre =  convertListToString(movie.getGenre());

        boolean insertData = dataBaseHelper.addData(movie.getTitle(),movie.getImage(),movie.getRating(),
                movie.getReleaseYear(),stringGenre);

        if(insertData){
            Log.d(TAG, "addDataToDB: SUCCESS");
        }else{
            Log.d(TAG, "addDataToDB: Something went wrong");

        }
    }

    public static String convertListToString(String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : stringArray) {
            stringBuilder.append(str).append(LIST_SEPARATOR);
        }

        // Remove last separator
        stringBuilder.setLength(stringBuilder.length() - LIST_SEPARATOR.length());

        return stringBuilder.toString();
    }




    public void callActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        Log.d(TAG, "callActivity: ");
    }


}


