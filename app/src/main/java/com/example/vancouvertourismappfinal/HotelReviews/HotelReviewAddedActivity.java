package com.example.vancouvertourismappfinal.HotelReviews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.MainActivity_1;
import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class HotelReviewAddedActivity extends AppCompatActivity {

    String COLLECTION_NAME="HotelReviews";
    String TAG="A freakin test";

    private FirebaseFirestore db;
    RatingBar rBar;
    TextView tvHotelName;
    TextView tvName;
    TextView tvPersonalComment;
    Button btnBack;

    float avgrating;

    Map<String,Object> hotelReview;
    String name,hotelName,personalComment,searchValue;
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_review_added);

        initialize();

        rBar=(RatingBar)findViewById(R.id.rBar);
        tvHotelName=(TextView)findViewById(R.id.tvHotelName);
        tvName=(TextView)findViewById(R.id.tvName);
        tvPersonalComment=(TextView)findViewById(R.id.tvComment);
        btnBack=(Button)findViewById(R.id.btnGoBack);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        name=prefs.getString("reviewName", "John Doe");
        hotelName=prefs.getString("hotelNameReview", "778-237-2575");
        personalComment=prefs.getString("pCom", "john@doe.com");
        avgrating=prefs.getFloat("avgrating", 3);
        searchValue=name.substring(0,2).concat("_"+hotelName.substring(0,2)).toUpperCase();
        Log.d(TAG, "onCreate: "+searchValue);

        rBar.setRating(avgrating);
        tvName.setText(name);
        tvHotelName.setText(hotelName);
        tvPersonalComment.setText(personalComment);

        hotelReview=new HashMap<>();
        hotelReview.put("UID",searchValue);
        hotelReview.put("Name",name);
        hotelReview.put("Hotel Name",hotelName);
        hotelReview.put("Rating",avgrating);
        hotelReview.put("Comment",personalComment);

        db.collection(COLLECTION_NAME).document(searchValue)
                .set(hotelReview).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelReviewAddedActivity.this, MainActivity_1.class);
                Toast.makeText(HotelReviewAddedActivity.this,"Comment",Toast.LENGTH_LONG).show();
                startActivity(i);
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
