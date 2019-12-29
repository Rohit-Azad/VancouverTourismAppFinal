package com.example.vancouvertourismappfinal.CarRentalClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.MainActivity_1;
import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RentalFinalizeActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    private FirebaseFirestore db;
    NumberFormat nf=NumberFormat.getCurrencyInstance();

    String COLLECTION_NAME="CarCustomerInfo";
    String TAG="A freakin test";

    String location;
    String dailyOrHourly;
    String insuranceReq;
    String payMethod;
    String cardNumber;
    String startTimeDate;
    String endTimeDate;
    String fullName;
    String suiteNum,streetName,cityName,zipcode,state;
    String phone;
    String email;
    String licenseNum;
    String randomRegNum;
    TextView referenceNumber;
    Button details;
    ScrollView scrollView;

    String selectedCar;
    String selectedColor;

    int price;
    int time;
    int insuranceAmount;
    int nextID;

    String finalReferenceNumber;
    ArrayList<CustomerInfoDB> newCustomer;
    ArrayList<Address>addrDetails;
    ArrayList<Booking> bkdetails;
    Map<String,Object> custInfo;
    Map<String,Object> addressInfo;
    Map<String,Object> rentalInfo;


    CustomerInfoDB custDB;
    Address addr;
    Booking booking;


    TableRow tr_1;
    TableRow.LayoutParams tRowParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    TableLayout.LayoutParams tLayoutParams=new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams imgParam =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_finalize);

        initialize();
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        payMethod=prefs.getString("CCType", "No fullName defined");
        cardNumber=prefs.getString("ccNumber", "No fullName defined");
        insuranceReq=prefs.getString("insurance", "No fullName defined");
        dailyOrHourly=prefs.getString("dOrH", "No fullName defined");
        location=prefs.getString("location", "No fullName defined");
        startTimeDate=prefs.getString("sTD", "No fullName defined");
        endTimeDate=prefs.getString("eTD", "No fullName defined");

        licenseNum=prefs.getString("licenseNum", "No fullName defined");
        fullName =prefs.getString("fullName", "No fullName defined");
        suiteNum=prefs.getString("suiteNum", "No fullName defined");
        streetName=prefs.getString("streetName", "No fullName defined");
        cityName=prefs.getString("cityName", "No fullName defined");
        zipcode=prefs.getString("zipcode", "No fullName defined");
        phone=prefs.getString("phone", "No fullName defined");
        email=prefs.getString("email", "No fullName defined");
        randomRegNum=prefs.getString("randomRegNum", "No fullName defined");
        state=prefs.getString("state","N/A");

        selectedCar=prefs.getString("selectedCar", "No fullName defined");
        selectedColor=prefs.getString("selectedColor", "No fullName defined");
        price=prefs.getInt("price", 0);
        time=prefs.getInt("time", 0);
        if(insuranceReq.equals("Yes") && dailyOrHourly.equals("Daily"))
        {
            insuranceAmount=15*time;
        }
        else if(insuranceReq.equals("Yes") && dailyOrHourly.equals("Hourly"))
        {
            insuranceAmount=4*time;
        }
        else
        {
            insuranceAmount=0;
        }
        finalReferenceNumber=randomRegNum.concat("_"+selectedCar.substring(0,3)+"_"+selectedCar.substring(selectedCar.length()-2)).toUpperCase();
        newCustomer=new ArrayList<>();
        bkdetails=new ArrayList<>();
        addrDetails=new ArrayList<>();

        custDB=new CustomerInfoDB();
        addr=new Address();
        booking=new Booking();

        custDB.name=fullName;
        custDB.cardNumber=cardNumber;
        custDB.eMail=email;
        custDB.cardType=payMethod;
        custDB.uniqueId=randomRegNum;
        custDB.licenseNumber=licenseNum;
        custDB.phoneNumber=phone;

        booking.carColor=selectedColor;
        booking.uniqueID=randomRegNum;
        booking.name=fullName;
        booking.carName=selectedCar;
        booking.uniqueBookingID=finalReferenceNumber;
        booking.insuranceAmt=insuranceAmount;
        booking.pickupDateOrTime=startTimeDate;
        booking.dropOffDateOrTime=endTimeDate;
        booking.taxes=(time*price+insuranceAmount)*0.15;
        booking.totalAmount=time*price+insuranceAmount*1.15;
        booking.rentalPrice=price*time;
        if(dailyOrHourly.equals("Daily")) {
            booking.time1 = time + " day/days";
        }
        else
        {
            booking.time1=time+" hour/hours";
        }
        bkdetails.add(booking);
        addr.city=cityName;
        addr.state=state;
        addr.street=streetName;
        addr.suite=suiteNum;
        addr.zipcode=zipcode;
        addrDetails.add(addr);

        newCustomer.add(custDB);
        for(int i=0;i<newCustomer.size();i++)
        {
            custInfo=new HashMap<>();
            custInfo.put("UID",newCustomer.get(i).uniqueId);
            custInfo.put("Name",newCustomer.get(i).name);
            custInfo.put("Phone",newCustomer.get(i).phoneNumber);
            custInfo.put("EMail",newCustomer.get(i).eMail);
            custInfo.put("License",newCustomer.get(i).licenseNumber);
            custInfo.put("Card Type",newCustomer.get(i).cardType);
            custInfo.put("Card Number",newCustomer.get(i).cardNumber);
        }
        for(int i=0;i<addrDetails.size();i++)
        {
            addressInfo=new HashMap<>();
            addressInfo.put("UID",newCustomer.get(i).uniqueId);
            addressInfo.put("Suite",addrDetails.get(i).suite);
            addressInfo.put("Street",addrDetails.get(i).street);
            addressInfo.put("City",addrDetails.get(i).city);
            addressInfo.put("State",addrDetails.get(i).state);
            addressInfo.put("Zip Code",addrDetails.get(i).zipcode);
        }
        for(int i=0;i<bkdetails.size();i++)
        {
            rentalInfo=new HashMap<>();
            rentalInfo.put("UID",newCustomer.get(i).uniqueId);
            rentalInfo.put("Unique Booking ID",bkdetails.get(i).uniqueBookingID);
            rentalInfo.put("Selected Car",bkdetails.get(i).carName);
            rentalInfo.put("Selected Color",bkdetails.get(i).carColor);
            rentalInfo.put("Total Time",bkdetails.get(i).time1);
            rentalInfo.put("Car Price",bkdetails.get(i).rentalPrice);
            rentalInfo.put("Tax(15%)",bkdetails.get(i).taxes);
            rentalInfo.put("Insurance",bkdetails.get(i).insuranceAmt);
            rentalInfo.put("Total Price",bkdetails.get(i).totalAmount);
            rentalInfo.put("Name",bkdetails.get(i).name);
            rentalInfo.put("Pickup",bkdetails.get(i).pickupDateOrTime);
            rentalInfo.put("DropOff",bkdetails.get(i).dropOffDateOrTime);
            rentalInfo.put("Status","Booked");

        }

        db.collection(COLLECTION_NAME).document(newCustomer.get(0).uniqueId)
                .set(custInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        db.collection("VehicleRentalInfo").document(bkdetails.get(0).uniqueBookingID)
                .set(rentalInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        db.collection(COLLECTION_NAME).document(newCustomer.get(0).uniqueId).collection("Address").document(newCustomer.get(0).uniqueId)
                .set(addressInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        Toast.makeText(RentalFinalizeActivity.this,"Database Updated",Toast.LENGTH_SHORT).show();


//        db.collection(COLLECTION_NAME).document(newCustomer.get(0).uniqueId).collection("Bookings Info").document(newCustomer.get(0).uniqueId)
//                .set(rentalInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//            }
//        });

        referenceNumber=(TextView)findViewById(R.id.tvReferenceNumber);
        referenceNumber.setTextSize(28);
        referenceNumber.setTextColor(Color.RED);
        referenceNumber.setGravity(Gravity.CENTER);
        referenceNumber.setText(randomRegNum);
        scrollView=(ScrollView)findViewById(R.id.theScroller);

        details=(Button)findViewById(R.id.btnViewDetails);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.removeAllViews();
                TableLayout tableLayout_1=new TableLayout(RentalFinalizeActivity.this);
                LinearLayout l = new LinearLayout(RentalFinalizeActivity.this);
                l.setOrientation(LinearLayout.VERTICAL);
                TextView heading = new TextView(RentalFinalizeActivity.this);

                TextView label_city = new TextView(RentalFinalizeActivity.this);
                label_city.setTextSize(22);
                TextView label_suite = new TextView(RentalFinalizeActivity.this);
                label_suite.setTextSize(22);
                TextView label_zipcode = new TextView(RentalFinalizeActivity.this);
                label_zipcode.setTextSize(22);
                TextView label_street = new TextView(RentalFinalizeActivity.this);
                label_street.setTextSize(22);
                TextView label_name = new TextView(RentalFinalizeActivity.this);
                label_name.setTextSize(22);
                TextView label_username = new TextView(RentalFinalizeActivity.this);
                label_username.setTextSize(22);
                TextView label_email = new TextView(RentalFinalizeActivity.this);
                label_email.setTextSize(22);
                TextView label_address = new TextView(RentalFinalizeActivity.this);
                label_address.setTextSize(22);
                TextView label_phone=new TextView(RentalFinalizeActivity.this);
                TextView label_Reference=new TextView(RentalFinalizeActivity.this);
                TextView label_license=new TextView(RentalFinalizeActivity.this);
                TextView label_car=new TextView(RentalFinalizeActivity.this);
                TextView label_color=new TextView(RentalFinalizeActivity.this);
                TextView label_price=new TextView(RentalFinalizeActivity.this);
                TextView label_insurance=new TextView(RentalFinalizeActivity.this);
                TextView label_tax=new TextView(RentalFinalizeActivity.this);
                TextView label_RentalPrice=new TextView(RentalFinalizeActivity.this);
                TextView rentalPrice=new TextView(RentalFinalizeActivity.this);
                TextView insuranceAmt=new TextView(RentalFinalizeActivity.this);
                TextView taxAmt=new TextView(RentalFinalizeActivity.this);

                TextView city = new TextView(RentalFinalizeActivity.this);
                city.setTextSize(20);
                TextView suite = new TextView(RentalFinalizeActivity.this);
                suite.setTextSize(20);
                TextView zipcode = new TextView(RentalFinalizeActivity.this);
                zipcode.setTextSize(20);
                TextView street = new TextView(RentalFinalizeActivity.this);
                street.setTextSize(20);
                TextView name = new TextView(RentalFinalizeActivity.this);
                name.setTextSize(20);
                TextView eMail = new TextView(RentalFinalizeActivity.this);
                eMail.setTextSize(20);
                TextView phoneNum = new TextView(RentalFinalizeActivity.this);
                TextView reference = new TextView(RentalFinalizeActivity.this);
                TextView license = new TextView(RentalFinalizeActivity.this);
                TextView heading1 = new TextView(RentalFinalizeActivity.this);
                TextView heading2 = new TextView(RentalFinalizeActivity.this);
                TextView heading3 = new TextView(RentalFinalizeActivity.this);
                TextView heading4 = new TextView(RentalFinalizeActivity.this);


                TextView car = new TextView(RentalFinalizeActivity.this);
                TextView color = new TextView(RentalFinalizeActivity.this);
                TextView totalprice = new TextView(RentalFinalizeActivity.this);
                eMail.setTextSize(20);
                tableLayout_1.setLayoutParams(tLayoutParams);
                tr_1 = new TableRow(RentalFinalizeActivity.this);
                HeadingsGenerator(heading1, "Personal Info", tr_1);
                tableLayout_1.addView(tr_1);

                tr_1=new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_name, "Full Name", tr_1);
                tableDataGenerator(name, fullName, tr_1);
                tableLayout_1.addView(tr_1);

                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_email, "E-Mail", tr_1);
                tableDataGenerator(eMail, email, tr_1);
                tableLayout_1.addView(tr_1);

                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_phone, "Phone", tr_1);
                tableDataGenerator(phoneNum, phone, tr_1);
                tableLayout_1.addView(tr_1);

                tr_1=new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_Reference, "Reference", tr_1);
                tableDataGenerator(reference, randomRegNum, tr_1);
                tableLayout_1.addView(tr_1);
                tr_1=new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_license, "License #", tr_1);
                tableDataGenerator(license, licenseNum, tr_1);
                tableLayout_1.addView(tr_1);
                tr_1 = new TableRow(RentalFinalizeActivity.this);
                HeadingsGenerator(heading2, "Address", tr_1);
                tableLayout_1.addView(tr_1);

                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_street, "Street", tr_1);
                tableDataGenerator(street, streetName, tr_1);
                tableLayout_1.addView(tr_1);
                tr_1 = new TableRow(RentalFinalizeActivity.this);
                HeadingsGenerator(heading4, "Vehicle Info", tr_1);
                tableLayout_1.addView(tr_1);

                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_car, "Selected Car", tr_1);
                tableDataGenerator(car, selectedCar, tr_1);
                tableLayout_1.addView(tr_1);

                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_color, "Selected Color", tr_1);
                tableDataGenerator(color, selectedColor, tr_1);
                tableLayout_1.addView(tr_1);
                tr_1 = new TableRow(RentalFinalizeActivity.this);
                HeadingsGenerator(heading3, "Price Breakdown", tr_1);
                tableLayout_1.addView(tr_1);
                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_RentalPrice, "Rental Price", tr_1);
                tableDataGenerator(rentalPrice, nf.format((time*price)), tr_1);
                tableLayout_1.addView(tr_1);

                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_tax, "Tax (15%)", tr_1);
                tableDataGenerator(taxAmt, nf.format((time*price+insuranceAmount)*0.15), tr_1);
                tableLayout_1.addView(tr_1);
                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_insurance, "Insurance", tr_1);
                tableDataGenerator(insuranceAmt, nf.format(insuranceAmount), tr_1);
                tableLayout_1.addView(tr_1);
                tr_1 = new TableRow(RentalFinalizeActivity.this);
                tableDataGenerator(label_price, "Total Price", tr_1);
                tableDataGenerator(totalprice, nf.format((time*price+insuranceAmount)*1.15), tr_1);
                tableLayout_1.addView(tr_1);

                l.addView(tableLayout_1);

                Button btn=new Button(RentalFinalizeActivity.this);
                btn.setLayoutParams(p);
                btn.setText("Go Back to Main Page");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(RentalFinalizeActivity.this, MainActivity_1.class);
                        startActivity(i);
                    }
                });
                l.addView(btn);

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

    private void tableDataGenerator(TextView name, String label, TableRow tr)
    {
        tr.removeView(name);
        tr.setLayoutParams(tRowParams);
        tr.setPadding(5,2,5,2);
        name.setTextColor(Color.WHITE);
        name.setText(label);
        name.setTextSize(22);
        name.setPadding(5, 3, 5, 3);
        tr.addView(name);
    }
    private void HeadingsGenerator(TextView name, String label, TableRow tr)
    {
        tr.removeView(name);
        tr.setLayoutParams(tRowParams);
        tr.setPadding(5,2,5,2);
        tr.setBackgroundColor(Color.GRAY);
        name.setTextColor(Color.WHITE);
        name.setText(label);
        name.setTextSize(22);
        name.setPadding(5, 3, 5, 3);
        tr.addView(name);
    }
}
