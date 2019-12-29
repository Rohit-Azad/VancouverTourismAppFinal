package com.example.vancouvertourismappfinal.CarRentalClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.HotelReviews.HotelReadReviewsActivity;
import com.example.vancouvertourismappfinal.HotelReviews.HotelsReviewShowActivity;
import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CarRentalReviewActivity extends AppCompatActivity {
    AutoCompleteTextView actvSearch;
    Button btnSearchDB;
    Button btnReset;


    private FirebaseFirestore db;
    NumberFormat nf=NumberFormat.getCurrencyInstance();

    String COLLECTION_NAME2="CarCustomerInfo";
    String COLLECTION_NAME="VehicleRentalInfo";
    String TAG="A freakin test";

    TextView tvName;
    TextView tvPhone;
    TextView tvEMail;
    TextView tvCardType;
    TextView tvCardNum;
    TextView tvCar;
    TextView tvInsurance;
    TextView tvTax;
    TextView tvLicense;
    TextView tvPrice;
    TextView tvTotal;
    TextView tvColor;
    TextView tvPickUp;
    TextView tvDropOff;

    ArrayList<String> uidList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rental_review);

        initialize();
        uidList=new ArrayList<>();



        actvSearch=(AutoCompleteTextView)findViewById(R.id.actvBookingID);
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                uidList.add((String) document.getData().get("UID"));

                            }


                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,uidList);
        actvSearch.setAdapter(adapter);

        tvName=(TextView)findViewById(R.id.tvName);
        tvPhone=(TextView)findViewById(R.id.tvPhone);
        tvCar=(TextView)findViewById(R.id.tvCar);
        tvCardNum=(TextView)findViewById(R.id.tvCardNum);
        tvCardType=(TextView)findViewById(R.id.tvCard);
        tvColor=(TextView)findViewById(R.id.tvColor);
        tvDropOff=(TextView)findViewById(R.id.tvDrop);
        tvEMail=(TextView)findViewById(R.id.tvEMail);
        tvInsurance=(TextView)findViewById(R.id.tvInsurance);
        tvLicense=(TextView)findViewById(R.id.tvLicense);
        tvPickUp=(TextView)findViewById(R.id.tvPick);
        tvTax=(TextView)findViewById(R.id.tvTax);
        tvPrice=(TextView)findViewById(R.id.tvPrice);
        tvTotal=(TextView)findViewById(R.id.tvTotal);

        btnReset=(Button)findViewById(R.id.btnReset);
        btnSearchDB=(Button)findViewById(R.id.btnSearchcBookingDB);

        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection(COLLECTION_NAME)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getData().get("UID").toString().equals(actvSearch.getText().toString()))
                                        {
                                            tvName.setText((String) document.getData().get("Name"));
                                            tvCar.setText((String) document.getData().get("Selected Car"));
                                            tvColor.setText((String) document.getData().get("Selected Color"));
                                            tvPrice.setText(nf.format((Double.parseDouble(document.getData().get("Car Price").toString()))));
                                            tvDropOff.setText((String) document.getData().get("DropOff"));
                                            tvPickUp.setText((String) document.getData().get("Pickup"));
                                            tvInsurance.setText(nf.format(Double.parseDouble(document.getData().get("Insurance").toString())));
                                            tvTotal.setText(nf.format((Double.parseDouble(document.getData().get("Total Price").toString()))));
                                            tvTax.setText(nf.format((Double.parseDouble(document.getData().get("Tax(15%)").toString()))));



                                        }
                                    }


                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
                db.collection(COLLECTION_NAME2)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getData().get("UID").toString().equals(actvSearch.getText().toString()))
                                        {

                                            tvCardNum.setText((String) document.getData().get("Card Number"));
                                            tvLicense.setText((String) document.getData().get("License"));
                                            tvEMail.setText((String) document.getData().get("EMail"));
                                            tvPhone.setText((String) document.getData().get("Phone"));
                                            tvCardType.setText((String) document.getData().get("Card Type"));
                                        }
                                    }


                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCardNum.setText("");
                tvLicense.setText("");
                tvEMail.setText("");
                tvPhone.setText("");
                tvCardType.setText("");
                tvName.setText("");
                tvCar.setText("");
                tvColor.setText("");
                tvPrice.setText("");
                tvDropOff.setText("");
                tvPickUp.setText("");
                tvInsurance.setText("");
                tvTotal.setText("");
                tvTax.setText("");
                actvSearch.getText().clear();

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
