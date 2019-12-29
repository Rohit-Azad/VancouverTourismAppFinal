package com.example.vancouvertourismappfinal.TouristAttractionsActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelMasterFragment;
import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelsDB;
import com.example.vancouvertourismappfinal.R;

import java.util.ArrayList;

public class TouristSearchResults extends AppCompatActivity {
    ArrayList<TouristSpotsDB> touristSpotsDB;
    ArrayList<String> names=new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toursit_search_results);

        Bundle extra = getIntent().getBundleExtra("listSpots");
        touristSpotsDB = (ArrayList<TouristSpotsDB>) extra.getSerializable("list");

        for(int i=0;i<touristSpotsDB.size();i++) {
            names.add(touristSpotsDB.get(i).name);
        }
        Log.d("TAGgggggg", "onCreate: "+touristSpotsDB.get(0).location);
        TouristMasterFragment m = (TouristMasterFragment) getSupportFragmentManager().findFragmentById(R.id.theNames);
        //Log.d("onCreate","MainActivity");
        m.setTheData(names);
    }
}
