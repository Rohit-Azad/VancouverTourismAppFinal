package com.example.vancouvertourismappfinal.TaxiServiceClasses;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.CarRentalClasses.ClientActivity;
import com.example.vancouvertourismappfinal.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TaxiServiceActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    private DBConnectivity_Taxi receiver;
    EditText etPassengers;
    EditText etName;
    EditText etAddress1;
    EditText etAddress2;
    EditText etDateTime;
    Button btnSelect;
    Button btnSelectAll;

    Calendar myCalendar1 = Calendar.getInstance();
    Calendar mcurrentTime2;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_service);

        etPassengers=findViewById(R.id.etPassengers);
        etName=findViewById(R.id.etName);
        etAddress1=findViewById(R.id.etAddress1);
        etAddress2=findViewById(R.id.etAddress2);
        etDateTime=findViewById(R.id.etDateTime);


        btnSelect=findViewById(R.id.btnSelect);
        btnSelectAll=findViewById(R.id.btnShowAll);

        receiver = new DBConnectivity_Taxi(this);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etDateTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(TaxiServiceActivity.this, date, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
                }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().isEmpty()||etAddress1.getText().toString().isEmpty()||
                        etAddress2.getText().toString().isEmpty()||etPassengers.getText().toString().isEmpty()||
                        etDateTime.getText().toString().isEmpty())
                {
                    Toast.makeText(TaxiServiceActivity.this, "Please fill the required fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("tName", etName.getText().toString());
                    editor.putString("tAddress1", etAddress1.getText().toString());
                    editor.putString("tAddress2", etAddress2.getText().toString());
                    editor.putString("tPassengers", etPassengers.getText().toString());
                    editor.putString("tDateTime", etDateTime.getText().toString());
                    editor.putString("justDone", "TaxiService");
                    editor.apply();
                    Intent i = new Intent(TaxiServiceActivity.this, DownloadService_Taxi.class);
                    i.putExtra("SeatingCapacity", etPassengers.getText().toString());
                    i.putExtra("loginOrRegister", "TaxiService");
                    startService(i);
                }
            }
        });
        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().isEmpty()||etAddress1.getText().toString().isEmpty()||
                        etAddress2.getText().toString().isEmpty()||etPassengers.getText().toString().isEmpty()||
                        etDateTime.getText().toString().isEmpty())
                {
                    Toast.makeText(TaxiServiceActivity.this, "Please fill the required fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("tName", etName.getText().toString());
                    editor.putString("tAddress1", etAddress1.getText().toString());
                    editor.putString("tAddress2", etAddress2.getText().toString());
                    editor.putString("tPassengers", etPassengers.getText().toString());
                    editor.putString("tDateTime", etDateTime.getText().toString());
                    editor.apply();
                    Intent i=new Intent(TaxiServiceActivity.this,DownloadService_Taxi.class);
                    i.putExtra("SeatingCapacity","1");
                    i.putExtra("loginOrRegister","TaxiService");
                    startService(i);
                }

            }
        });




    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mcurrentTime2 = Calendar.getInstance();
        mcurrentTime2.add(Calendar.HOUR_OF_DAY,1);
        int hour = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
        int minute = 0;
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(TaxiServiceActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                etDateTime.setText(sdf.format(myCalendar1.getTime())+" - "+String.format("%02d:%02d", selectedHour, selectedMinute));
//                etDateTime.setFocusable(false);
//                etDateTime.setEnabled(false);
            }
        }, hour, minute, true);//Yes 24 hour time
//        mTimePicker.setTitle("Select Pickup Time");
        mTimePicker.show();
//        etDateTime.setText(sdf.format(myCalendar1.getTime()));
        etDateTime.setFocusable(false);
        etDateTime.setEnabled(false);
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
