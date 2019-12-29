package com.example.vancouvertourismappfinal.ComboPlansClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.CarRentalClasses.SelectionCriteriaActivity;
import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ComboSelectionsActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    int numRooms;
    ScrollView svCombo;
    RadioGroup rgRooms;
    RadioButton rbStd;
    RadioButton rbDouble;
    RadioButton rbSuite;
    RadioButton rbLuxury;
    TextView tvRoomNumber;
    TextView tvSelectedRoomTypes;
    TextView tvSelectedRoomPrices;
    int totalPrice;
    String roomSelected1;
    String roomSelected2;
    String roomSelected3;
    int roomPrice1;
    int roomPrice2;
    int roomPrice3;

    String TAG="A test";

    private FirebaseFirestore db;
    Button btnConfirm;
    String COLLECTION_NAME1="HotelsDB";
    String[] roomTypes={"Standard","Double","Luxury","Suite"};
    LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

    LinearLayout.LayoutParams p1 =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
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
        setContentView(R.layout.activity_combo_selections);

        initialize();

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        numRooms=prefs.getInt("numRooms_c", 1);
        Log.d("TAAAAAGGGG", "onCreate: "+getIntent().getStringExtra("nameList")+"=>"+getIntent().getStringExtra("carNameList"));

        svCombo=findViewById(R.id.svCombo);
        svCombo.removeAllViews();
        LinearLayout l=new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);

        l.setLayoutParams(p);

        switch(numRooms)
        {
            case 1:
            {
                tvRoomNumber=new TextView(this);
                tvRoomNumber.setLayoutParams(p);
                tvRoomNumber.setTextColor(Color.BLACK);
                tvRoomNumber.setText("Room - 1");
                tvRoomNumber.setTextSize(40);
                l.addView(tvRoomNumber);
                final Spinner s1=new Spinner(this);
                s1.setLayoutParams(p1);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                    ComboSelectionsActivity.this, android.R.layout.simple_spinner_item, roomTypes);
                s1.setAdapter(adapter);
                s1.setBackgroundColor(Color.LTGRAY);
                s1.setSelection(0);
                s1.setPadding(8,8,8,8);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        roomSelected1=s1.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                l.addView(s1);
                break;
            }
            case 2:
            {
                tvRoomNumber=new TextView(this);
                tvRoomNumber.setLayoutParams(p);
                tvRoomNumber.setTextColor(Color.BLACK);
                tvRoomNumber.setText("Room - 1");
                tvRoomNumber.setTextSize(40);
                l.addView(tvRoomNumber);
                final Spinner s1=new Spinner(this);
                s1.setLayoutParams(p1);
                s1.setBackgroundColor(Color.LTGRAY);
                s1.setPadding(8,8,8,8);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                        ComboSelectionsActivity.this, android.R.layout.simple_spinner_item, roomTypes);
                s1.setAdapter(adapter);
                s1.setSelection(0);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        roomSelected1=s1.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                l.addView(s1);

                tvRoomNumber=new TextView(this);
                tvRoomNumber.setLayoutParams(p);
                tvRoomNumber.setTextColor(Color.BLACK);
                tvRoomNumber.setText("Room - 2");
                tvRoomNumber.setTextSize(40);
                l.addView(tvRoomNumber);
                final Spinner s2=new Spinner(this);
                s2.setLayoutParams(p1);
                s2.setBackgroundColor(Color.LTGRAY);
                s2.setPadding(8,8,8,8);
                ArrayAdapter<String> adapter1=new ArrayAdapter<String>(
                        ComboSelectionsActivity.this, android.R.layout.simple_spinner_item, roomTypes);
                s2.setAdapter(adapter);
                s2.setSelection(0);
                s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        roomSelected2=s2.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                roomSelected2=s1.getSelectedItem().toString();
                l.addView(s2);
                break;
            }
            case 3:
            {
                tvRoomNumber=new TextView(this);
                tvRoomNumber.setLayoutParams(p);
                tvRoomNumber.setTextColor(Color.BLACK);
                tvRoomNumber.setText("Room - 1");
                tvRoomNumber.setTextSize(40);
                l.addView(tvRoomNumber);
                final Spinner s1=new Spinner(this);
                s1.setLayoutParams(p1);
                s1.setBackgroundColor(Color.LTGRAY);
                s1.setPadding(8,8,8,8);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                        ComboSelectionsActivity.this, android.R.layout.simple_spinner_item, roomTypes);
                s1.setAdapter(adapter);
                s1.setSelection(0);
                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        roomSelected1=s1.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                l.addView(s1);

                tvRoomNumber=new TextView(this);
                tvRoomNumber.setLayoutParams(p);
                tvRoomNumber.setTextColor(Color.BLACK);
                tvRoomNumber.setText("Room - 2");
                tvRoomNumber.setTextSize(40);
                l.addView(tvRoomNumber);
                final Spinner s2=new Spinner(this);
                s2.setLayoutParams(p1);
                s2.setBackgroundColor(Color.LTGRAY);
                s2.setPadding(8,8,8,8);
                ArrayAdapter<String> adapter1=new ArrayAdapter<String>(
                        ComboSelectionsActivity.this, android.R.layout.simple_spinner_item, roomTypes);
                s2.setAdapter(adapter);
                s2.setSelection(0);
                s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        roomSelected2=s2.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                roomSelected2=s1.getSelectedItem().toString();
                l.addView(s2);

                tvRoomNumber=new TextView(this);
                tvRoomNumber.setLayoutParams(p);
                tvRoomNumber.setTextColor(Color.BLACK);
                tvRoomNumber.setText("Room - 3");
                tvRoomNumber.setTextSize(40);
                l.addView(tvRoomNumber);

                final Spinner s3=new Spinner(this);
                s3.setLayoutParams(p1);
                s3.setBackgroundColor(Color.LTGRAY);
                s3.setPadding(8,8,8,8);
                ArrayAdapter<String> adapter3=new ArrayAdapter<String>(
                        ComboSelectionsActivity.this, android.R.layout.simple_spinner_item, roomTypes);
                s3.setAdapter(adapter);
                s3.setSelection(0);
                s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        roomSelected3=s3.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

//                roomSelected3=s3.getSelectedItem().toString();
                l.addView(s3);
                break;
            }
        }
            btnConfirm=new Button(this);
            btnConfirm.setText("Select Rooms");
            btnConfirm.setLayoutParams(p);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("room1_c", roomSelected1);
                    editor.putString("room2_c", roomSelected2);
                    editor.putString("room3_c", roomSelected3);
                    editor.putString("selectedCar_c",getIntent().getStringExtra("carNameList"));
                    editor.putString("selectedHotel_c",getIntent().getStringExtra("nameList"));

                    editor.apply();
                    Intent x=new Intent(ComboSelectionsActivity.this,ComboConfirmActivity.class);
                    startActivity(x);

                }
            });
            l.addView(btnConfirm);
        svCombo.addView(l);
    }
}
