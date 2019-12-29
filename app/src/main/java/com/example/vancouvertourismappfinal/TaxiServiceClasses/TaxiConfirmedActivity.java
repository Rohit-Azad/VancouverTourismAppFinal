package com.example.vancouvertourismappfinal.TaxiServiceClasses;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.MainActivity_1;
import com.example.vancouvertourismappfinal.R;

public class TaxiConfirmedActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    TextView tvName;
    TextView tvBookingID;
    TextView tvVehicle;
    TextView  tvPassengers;
    TextView tvAddress1;
    TextView tvAddress2;
    TextView tvPPH;
    TextView tvDateTime;
    Button btnGoBack;

    private DBConnectivity_Taxi receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_confirmed);

        receiver = new DBConnectivity_Taxi(this);
        Log.d("IntentValues", "onCreate: "+getIntent().getStringExtra("nameList")+"=>"+getIntent().getStringExtra("picList"));

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tvName=findViewById(R.id.tvName);
        tvBookingID=findViewById(R.id.tvBookingID);
        tvVehicle=findViewById(R.id.tvVehicle);
        tvPassengers=findViewById(R.id.tvPassengers);
        tvAddress1=findViewById(R.id.tvAddress1);
        tvAddress2=findViewById(R.id.tvAddress2);
        tvPPH=findViewById(R.id.tvPrice);
        tvDateTime=findViewById(R.id.tvdateTime);
        btnGoBack=findViewById(R.id.btnGoBack);

        int random = (int)(Math.random() * 50 + 1);

        tvName.setText(prefs.getString("tName", "No fullName defined"));
        tvBookingID.setText((prefs.getString("tName", "No fullName defined").substring(0,2).concat("_").concat(getIntent().getStringExtra("nameList").substring(0,2).concat(String.valueOf(random)))).toUpperCase());
        tvVehicle.setText(getIntent().getStringExtra("nameList"));
        tvPassengers.setText(prefs.getString("tPassengers", "No fullName defined"));
        tvAddress1.setText(prefs.getString("tAddress1", "No fullName defined"));
        tvAddress2.setText(prefs.getString("tAddress2", "No fullName defined"));
        tvPPH.setText("$ "+getIntent().getStringExtra("picList"));
        tvDateTime.setText(prefs.getString("tDateTime", "No fullName defined"));

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                editor.putString("justDone", "EnterReservation");
                editor.apply();

                Intent i=new Intent(TaxiConfirmedActivity.this, DownloadService_Taxi.class);
                i.putExtra("passengers",tvPassengers.getText().toString());
                i.putExtra("address1",tvAddress1.getText().toString());
                i.putExtra("address2",tvAddress2.getText().toString());
                i.putExtra("ID",tvBookingID.getText().toString());
                i.putExtra("priceperhour",tvPPH.getText().toString().substring(2));
                i.putExtra("taxi",tvVehicle.getText().toString());
                i.putExtra("name",tvName.getText().toString());
                i.putExtra("pickup",tvDateTime.getText().toString());
                i.putExtra("loginOrRegister","EnterReservation");
                startService(i);
            }
        });

    }

    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(DBConnectivity_Taxi.STATUS_DONE);
          /*
        Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        Register a BroadcastReceiver to be run in the main activity thread.
        The receiver will be called with any broadcast Intent that matches filter,
        in the main application thread.
         */

        registerReceiver(receiver,filter);
    }
}
