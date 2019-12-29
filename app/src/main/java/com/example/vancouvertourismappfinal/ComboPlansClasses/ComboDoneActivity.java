package com.example.vancouvertourismappfinal.ComboPlansClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.MainActivity_1;
import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class ComboDoneActivity extends AppCompatActivity {

    int numGuests;
    int numRooms;
    int numOfDays;
    String checkIn;
    String checkOut;
    String room1,room2,room3,selectedCar,selectedHotel;
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    String COLLECTION_NAME="ComboBookings";

    NumberFormat nf=NumberFormat.getNumberInstance();

    int price;
    double tax;
    double totalPrice;
    String cardType,name,expiry,cardnumber;

    TextView tvName,tvHotel,tvCar,tvID,tvTotal;
    Button btnGoBackMain;

    String rooms;

    Map<String,Object> data;
    private FirebaseFirestore db;

    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_done);

        initialize();
        tvCar=findViewById(R.id.tvSelectedCar);
        tvHotel=findViewById(R.id.tvSelectedHotel);
        tvName=findViewById(R.id.tvName);
        tvID=findViewById(R.id.tvBookingID);
        tvTotal=findViewById(R.id.tvTotalPrice);
        btnGoBackMain=findViewById(R.id.btnGoBackMain);

        btnGoBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ComboDoneActivity.this, MainActivity_1.class);
                startActivity(i);
            }
        });

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        room1=prefs.getString("room1_c", "No fullName defined");
        room2=prefs.getString("room2_c", "No fullName defined");
        room3=prefs.getString("room3_c", "No fullName defined");
        selectedCar=prefs.getString("selectedCar_c", "No fullName defined");
        selectedHotel=prefs.getString("selectedHotel_c", "No fullName defined");
        checkIn=prefs.getString("checkin", "No fullName defined");
        checkOut=prefs.getString("checkout", "No fullName defined");
        numGuests=prefs.getInt("numGuests", 1);
        numOfDays=prefs.getInt("numdays", 1);
        numRooms=prefs.getInt("numRooms_c", 1);

        name=prefs.getString("name_combo", "No fullName defined");
        cardnumber=prefs.getString("cardnum_combo", "No fullName defined");
        expiry=prefs.getString("expiry_combo", "No fullName defined");
        price=Integer.parseInt(prefs.getString("price_combo", "No fullName defined"));
        tax=Double.parseDouble(prefs.getString("tax_combo", "No fullName defined"));
        totalPrice=Double.parseDouble(prefs.getString("totalPrice_combo", "No fullName defined"));
        cardType=prefs.getString("cardType_combo", "No fullName defined");

        tvName.setText(name);
        tvTotal.setText(nf.format(totalPrice));
        tvID.setText((name.substring(0,2).concat("-").concat(cardnumber.substring(cardnumber.length()-2))).toUpperCase());
        tvHotel.setText(selectedHotel);
        tvCar.setText(selectedCar);

        if(numRooms==1)
        {
            rooms=room1;
        }else if (numRooms==3)
        {
            rooms=room1+", "+room2+", "+room3;
        }else if(numRooms==2)
        {
            rooms=room1+", "+room2;
        }

        data=new HashMap<>();
        data.put("ID",(name.substring(0,2).concat("-").concat(cardnumber.substring(cardnumber.length()-2))).toUpperCase());
        data.put("Name",name);
        data.put("Rooms",rooms);
        data.put("Price",price);
        data.put("Tax",tax);
        data.put("Total Price",totalPrice);
        data.put("Car",selectedCar);
        data.put("Hotel",selectedHotel);
        data.put("Card Number",cardnumber);
        data.put("Card Type",cardType);

        db.collection(COLLECTION_NAME).document(name.substring(0,2).concat("-").concat(cardnumber.substring(cardnumber.length()-2)))
                .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
}
