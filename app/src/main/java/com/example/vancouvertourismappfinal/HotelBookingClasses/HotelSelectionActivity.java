package com.example.vancouvertourismappfinal.HotelBookingClasses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vancouvertourismappfinal.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;

public class HotelSelectionActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private final String TAG = "ReadAllData";
    ArrayList<String> data=new ArrayList<>();
    ArrayList<String> typesTruncated=new ArrayList<>();
    String COLLECTION_NAME="VehicleInventory2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selection);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.getStringArrayList("mfData");

            Log.d(TAG, "onCreate: " + data.size());
        }
        initialize();
        // readAllData();

        HotelMasterFragment m = (HotelMasterFragment) getSupportFragmentManager().findFragmentById(R.id.theNames);
        //Log.d("onCreate","MainActivity");
        m.setTheData(data);
        //   removeDuplicates(types);
    }

    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }
}
