package com.example.vancouvertourismappfinal.HotelBookingClasses;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.MainActivity_1;
import com.example.vancouvertourismappfinal.R;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristAttActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class BookingConfirmationActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    String COLLECTION_NAME="HotelBookings";
    String TAG="A freakin test";
    String COLLECTION_NAME2="HotelGuestInfo";
    NumberFormat nf=NumberFormat.getCurrencyInstance();
    String name;
    String phoneNumber;
    String eMail;
    String ccType;
    String ccNum;
    String ccExpiry;
    String ccCVV;
    String payWhen;
    int numGuests;
    int numRooms;
    int numOfDays;
    String checkIn;
    String checkOut;
    int dailyPrice1;
    int dailyPrice2;
    int dailyPrice3;

    double tax,totalAmount;
    String roomType1;
    String roomType2;
    String roomType3;
    String hotelName;
    String uniqueID;
    String uniqueID_1;

    TextView tvUniqueID;

    private FirebaseFirestore db;

    Map<String,Object> dataBookings;
    Map<String,Object> dataCustInfo;

    Button details;

    TableRow tr_1;
    TableRow.LayoutParams tRowParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    TableLayout.LayoutParams tLayoutParams=new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

    ScrollView scrollView;
    TextView tv;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        tvUniqueID=(TextView)findViewById(R.id.tvUniqueID);

        initialize();

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        name=prefs.getString("name", "John Doe");
        phoneNumber=prefs.getString("guestPhoneNum", "778-237-2575");
        eMail=prefs.getString("guestEmail", "john@doe.com");
        ccType=prefs.getString("ccType", "Mastercard");
        ccNum=prefs.getString("cardNumber", "4357-3333-4444-5555");
        ccExpiry=prefs.getString("expiry", "11/23");
        ccCVV=prefs.getString("CVV", "666");

        payWhen=prefs.getString("payWhen", "Online");
        numGuests =prefs.getInt("numGuests", 2);
        numRooms=prefs.getInt("numRooms", 1);
        numOfDays=prefs.getInt("numdays", 1);
        checkIn=prefs.getString("checkin", "No fullName defined");
        checkOut=prefs.getString("checkout", "No fullName defined");
        dailyPrice1=prefs.getInt("dailyPrice1", 169);
        dailyPrice2=prefs.getInt("dailyPrice2", 179);
        dailyPrice3=prefs.getInt("dailyPrice3", 199);
        roomType1=prefs.getString("roomType1", "Standard");
        roomType2=prefs.getString("roomType2", "Double");
        roomType3=prefs.getString("roomType3", "Suite");
        hotelName=prefs.getString("hotelName", "Anonymous Hotel");

        uniqueID=name.substring(0,2).concat("_"+phoneNumber.substring(1,3)).toUpperCase();
        uniqueID_1=name.substring(0,2).concat("_"+eMail.substring(0,1).concat("_"+phoneNumber.substring(1,2))).toUpperCase();

        tvUniqueID.setText(uniqueID);


        switch(numRooms)
        {
            case 1:
            {
                tax=(dailyPrice1*numOfDays)*0.15;
                totalAmount=(dailyPrice1*numOfDays)*1.15;
                break;
            }
            case 2:
            {
                tax=((dailyPrice1+dailyPrice2)*numOfDays)*0.15;
                totalAmount=((dailyPrice1+dailyPrice2)*numOfDays)*1.15;
                break;
            }
            case 3:
            {
                tax=((dailyPrice1+dailyPrice2+dailyPrice3)*numOfDays)*0.15;
                totalAmount=((dailyPrice1+dailyPrice2+dailyPrice3)*numOfDays)*1.15;
            }
        }

        dataBookings=new HashMap<>();
        dataBookings.put("UID",uniqueID);
        dataBookings.put("Hotel Name",hotelName);
        dataBookings.put("Guest Name",name);
        dataBookings.put("Check in",checkIn);
        dataBookings.put("Check out",checkOut);
        dataBookings.put("Payment",payWhen);
        dataBookings.put("Payment Method",ccType);
        dataBookings.put("Total Price",totalAmount);

        db.collection(COLLECTION_NAME).document(uniqueID)
                .set(dataBookings).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        dataCustInfo=new HashMap<>();
        dataCustInfo.put("UID",uniqueID_1);
        dataCustInfo.put("Unique Booking ID",uniqueID);
        dataCustInfo.put("Name",name);
        dataCustInfo.put("Phone",phoneNumber);
        dataCustInfo.put("E-Mail",eMail);
        dataCustInfo.put("CC Type",ccType);
        dataCustInfo.put("CC Number",ccNum);
        dataCustInfo.put("CVV",ccCVV);
        dataCustInfo.put("CC Expiry",ccExpiry);

        db.collection(COLLECTION_NAME2).document(uniqueID_1)
                .set(dataCustInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });



        scrollView= findViewById(R.id.theScroller);

        details= findViewById(R.id.btnViewDetails);

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.removeAllViews();
                TableLayout tableLayout_1=new TableLayout(BookingConfirmationActivity.this);
                LinearLayout l = new LinearLayout(BookingConfirmationActivity.this);
                l.setOrientation(LinearLayout.VERTICAL);
                l.setLayoutParams(p);

                tv=new TextView(BookingConfirmationActivity.this);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(24);
                tv.append("Name: "+name+"\n");
                tv.append("Check-in: "+checkIn+"\n");
                tv.append("Check-out: "+checkOut+"\n");
                tv.append("Rooms: "+numRooms+"\n\n");
                if(numRooms==1) {
                    tv.append("Daily Price: " + nf.format(dailyPrice1) + "\n");
                    tv.append("Room Type: " + roomType1 + "\n");

                    tv.append("Price: "+nf.format(dailyPrice1*numOfDays)+"\n");
                    tv.append("tax(15%): "+nf.format(tax)+"\n");
                    tv.append("Total Price(with tax): "+nf.format(totalAmount)+"\n");
                }else if(numRooms==2) {
                    tv.append("ROOM 1:"+"\n");
                    tv.append("Daily Price: " + dailyPrice1 + "\n");
                    tv.append("Room Type: " + roomType1 + "\n\n");
                    tv.append("ROOM 2:"+"\n");
                    tv.append("Daily Price: " + dailyPrice2 + "\n");
                    tv.append("Room Type: " + roomType2 + "\n");

                    tv.append("Price: "+nf.format((dailyPrice1+dailyPrice2)*numOfDays)+"\n");
                    tv.append("tax(15%): "+nf.format(((dailyPrice1+dailyPrice2)*numOfDays)*0.15)+"\n");
                    tv.append("Total Price(with tax): "+nf.format(((dailyPrice1+dailyPrice2)*numOfDays)*1.15)+"\n");
                }else if(numRooms==3) {
                    tv.append("ROOM 1:"+"\n");
                    tv.append("Daily Price: " + dailyPrice1 + "\n");
                    tv.append("Room Type: " + roomType1 + "\n\n");
                    tv.append("ROOM 2:"+"\n");
                    tv.append("Daily Price: " + dailyPrice2 + "\n");
                    tv.append("Room Type: " + roomType2 + "\n\n");
                    tv.append("ROOM 3:"+"\n");
                    tv.append("Daily Price: " + dailyPrice3 + "\n");
                    tv.append("Room Type: " + roomType3 + "\n");
                    tv.append("Price: "+nf.format((dailyPrice1+dailyPrice2+dailyPrice3)*numOfDays)+"\n");
                    tv.append("tax(15%): "+nf.format(((dailyPrice1+dailyPrice2+dailyPrice3)*numOfDays)*0.15)+"\n");
                    tv.append("Total Price(with tax): "+nf.format(((dailyPrice1+dailyPrice2+dailyPrice3)*numOfDays)*0.15)+"\n");






                }
                btnExit=new Button(BookingConfirmationActivity.this);
                btnExit.setLayoutParams(p);
                btnExit.setText("Exit To Main Screen");
                btnExit.setTextSize(30);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(BookingConfirmationActivity.this, MainActivity_1.class);
                        startActivity(i);
                    } });


                        l.addView(tv);
                l.addView(btnExit);
                scrollView.addView(l);
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
