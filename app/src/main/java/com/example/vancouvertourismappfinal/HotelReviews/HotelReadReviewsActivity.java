package com.example.vancouvertourismappfinal.HotelReviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HotelReadReviewsActivity extends AppCompatActivity {

    Button btnSearch;
    Button btnViewAll;
    AutoCompleteTextView actvName;

    String COLLECTION_NAME="HotelReviews";
    String TAG="A freakin test";
    ArrayList<String> hotelData;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_read_reviews);

        initialize();

        btnSearch=(Button)findViewById(R.id.btnSearchName);
        btnViewAll=(Button)findViewById(R.id.btnViewAllReviews);
        actvName=(AutoCompleteTextView)findViewById(R.id.actvHotelName);

        String[] hotels = getResources().getStringArray(R.array.hotels);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,hotels);
        actvName.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotelData=new ArrayList<>();
                db.collection(COLLECTION_NAME)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getData().get("Hotel Name").toString().equals(actvName.getText().toString()))
                                        {

                                            hotelData.add(document.getData().get("UID").toString());

                                        }
                                    }


                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                                Intent z=new Intent(HotelReadReviewsActivity.this,HotelsReviewShowActivity.class);
                                z.putStringArrayListExtra("mfData",hotelData);
                                startActivity(z);
                            }
                        });

            }
        });
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotelData=new ArrayList<>();
                db.collection(COLLECTION_NAME)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                            hotelData.add(document.getData().get("UID").toString());
                                    }


                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                                Intent z=new Intent(HotelReadReviewsActivity.this,HotelsReviewShowActivity.class);
                                z.putStringArrayListExtra("mfData",hotelData);
                                startActivity(z);
                            }
                        });

            }
        });


    }

    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }
}
