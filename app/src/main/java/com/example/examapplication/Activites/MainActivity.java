package com.example.examapplication.Activites;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.examapplication.Fragments.QRFragment;
import com.example.examapplication.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    QRFragment qrFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        qrFragment = new QRFragment();
        replaceFragment(qrFragment,"QR Fragment");



    }


    @Override
    public void onBackPressed() {
            finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(fragment,tag);
        fragmentTransaction.replace(R.id.fragments_container,fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }










}
