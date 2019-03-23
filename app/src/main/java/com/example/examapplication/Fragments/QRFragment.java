package com.example.examapplication.Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.examapplication.Activites.MainActivity;
import com.example.examapplication.Activites.MovieListActivity;
import com.example.examapplication.Activites.SplashActivity;
import com.example.examapplication.DB.DataBaseHelper;
import com.example.examapplication.Objects.Movie;
import com.example.examapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.support.design.widget.Snackbar;



import java.util.ArrayList;


public class QRFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {
    private static final String TAG = "QRFragment";


    TextView qrResultTextView;

    QRCodeReaderView qrCodeReaderView;
    AppCompatActivity appCompatActivity;


    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr, container, false);
          appCompatActivity = (AppCompatActivity) getActivity();

        //back home button
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        qrResultTextView = view.findViewById(R.id.scan_code_text_view);
        qrCodeReaderView = view.findViewById(R.id.qr_decoder_view);

        qrCodeReaderView.setOnQRCodeReadListener(this);
        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);
        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);
        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);
        // Use this function to set front camera preview
        //qrCodeReaderView.setFrontCamera();
        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();



        //string JSON TO TEXT
   /*     String jsonMovie = "{\n" +
                "  \"title\": \"The Godfather\",\n" +
                "  \"image\": \"https://www.imdb.com/title/tt0068646/mediaviewer/rm746868224\",\n" +
                "  \"rating\": 9.2,\n" +
                "  \"releaseYear\": 1972,\n" +
                "  \"genre\": [\n" +
                "    \"Crime\",\n" +
                "    \"Drama\"\n" +
                "  ]\n" +
                "}";

        //parse json
        Gson gson = new GsonBuilder().create();
        Movie newMovie = gson.fromJson(jsonMovie, Movie.class);
        Log.d(TAG, "onCreateView: --------------" + newMovie.getTitle());

        boolean answer = checkDataInDB(newMovie);
        Log.d(TAG, "onCreateView: " + answer);

        if(answer){
            SplashActivity.addDataToDB(newMovie);

        }else {
             Snackbar.make(appCompatActivity.findViewById(R.id.fragments_container),"Current movie already exist in the Database " ,Snackbar.LENGTH_SHORT).show();
        }
*/




        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();

    }

    @Override
    public void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private boolean checkDataInDB(Movie m) {

        boolean answerFromDB;
        //create DBHelper
        dataBaseHelper = new DataBaseHelper(getContext());
        db = dataBaseHelper.getReadableDatabase();

        // readFromData
        Cursor cursor = dataBaseHelper.readFromData();

        Log.d(TAG, "readFromData: cursor count " + cursor.getCount());
        cursor.moveToFirst();

        do {
            String[] strGenre = MovieListActivity.convertStringToArray(cursor.getString(4));

            Movie someMovie = new Movie(cursor.getString(0), cursor.getString(1),
                    cursor.getDouble(2), cursor.getInt(3), strGenre);



            //checking data
            if (!cursor.getString(0).equals(m.getTitle())){
                answerFromDB = true;
               // moviesList.add(someMovie);
            }
             else answerFromDB = false;

        } while (cursor.moveToNext());

        //close db
        cursor.close();
        dataBaseHelper.close();
        db.close();

        return answerFromDB;

    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.d(TAG, "onQRCodeRead: result is  " + text);

        Gson gson = new GsonBuilder().create();
        Movie newMovie = gson.fromJson(text, Movie.class);
        Log.d(TAG, "onCreateView: --------------" + newMovie.getTitle());

        boolean answer = checkDataInDB(newMovie);
        Log.d(TAG, "onCreateView: " + answer);

        if(answer){
            SplashActivity.addDataToDB(newMovie);
        }else {
            Snackbar.make(appCompatActivity.findViewById(R.id.fragments_container),"Current movie already exist in the Database " ,Snackbar.LENGTH_SHORT).show();
        }

        qrResultTextView.setText(text);
    }
}
