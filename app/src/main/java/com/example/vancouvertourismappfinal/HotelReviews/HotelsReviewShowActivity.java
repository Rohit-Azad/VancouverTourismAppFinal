package com.example.vancouvertourismappfinal.HotelReviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vancouvertourismappfinal.R;

import java.util.ArrayList;

/*
    Notes:
    To make it landscape
    <activity android:name=".MainActivity"
            android:screenOrientation="landscape">

     To create Master/Detail using Fragment
     1) Create a Fragment (File -> New -> Fragment -> Fragment (blank), uncheck all except the layout option
        a. Call it MasterFragment
        b. Language: Java
     2) add the <fragment> tag , see the layout
     3) Edit the fragment_review_master , see the layout
     4) Create a Fragment (File -> New -> Fragment -> Fragment (blank), uncheck all except the layout option
        a. Call it DetailFragment
        b. Language: Java
     5) add the <FrameLayout> tag , see the layout
     6) Edit the fragment_review_detail, see the Layout

 */
public class HotelsReviewShowActivity extends AppCompatActivity {
    ArrayList<String> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reviews);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.getStringArrayList("mfData");

            Log.d("TAG", "onCreate: " + data.size());
        }

        ReviewsMasterFragment m = (ReviewsMasterFragment) getSupportFragmentManager().findFragmentById(R.id.theNames);
        // passing the data to the MasterFragment
        Log.d("onCreate","MainActivity");
        m.setTheData(data);
    }

}
