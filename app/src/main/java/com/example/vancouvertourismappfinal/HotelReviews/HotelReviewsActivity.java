package com.example.vancouvertourismappfinal.HotelReviews;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.vancouvertourismappfinal.R;

public class HotelReviewsActivity extends AppCompatActivity {

    Button btnAddReview;
    Button btnReadReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reviews);

        btnAddReview=(Button)findViewById(R.id.btnReviewHotel);
        btnReadReview=(Button)findViewById(R.id.btnReadReviews);

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelReviewsActivity.this,HotelsAddReview.class);
                startActivity(i);

            }
        });
        btnReadReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelReviewsActivity.this,HotelReadReviewsActivity.class);
                startActivity(i);
            }
        });
    }
}
