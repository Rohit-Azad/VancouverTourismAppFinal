package com.example.vancouvertourismappfinal.ComboPlansClasses;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelBookingMainActivity;
import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelSelectionActivity;
import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelsDB;
import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ComboMainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    Button btnNewWest;
    Button btnBurnaby;
    Button btnNorthVan;
    Button btndowntown;
    Button btnRichmond;
    Button btnSurrey;
    Button btnShowAll;
    Button btnPlusGuests;
    Button btnMinusGuests;
    Button btnPlusRooms;
    Button btnMinusRooms;

    EditText etCheckIn;
    EditText etCheckOut;
    EditText etRooms;
    EditText etGuests;

    TextView tvDays;

    ArrayList<String> finalProduct;
    ArrayList<String> finalProduct2;
    ArrayList<String> finalProduct3;

    int numGuests=1;
    int numRooms=1;
    int numOfDays;
    String checkIn;
    String checkOut;

    String COLLECTION_NAME1="HotelsDB";
    String COLLECTION_NAME2="VehicleInventory";
    String COLLECTION_NAME3="ComboBookings";
    private FirebaseFirestore db;
    ArrayList<HotelsDB> hotelList;
    String TAG="A test";


    DatePickerDialog.OnDateSetListener date2;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_main);

        initialize();

        btnNewWest= findViewById(R.id.btnNewWest);
        btnBurnaby= findViewById(R.id.btnBurnaby);
        btnNorthVan= findViewById(R.id.btnNorthVan);
        btndowntown= findViewById(R.id.btnDowntown);
        btnRichmond= findViewById(R.id.btnRichmond);
        btnSurrey= findViewById(R.id.btnSurrey);
        btnShowAll= findViewById(R.id.btnShowAll);
        btnMinusGuests= findViewById(R.id.btnMinusGuest);
        btnMinusRooms= findViewById(R.id.btnMinusRoom);
        btnPlusGuests= findViewById(R.id.btnPlusGuest);
        btnPlusRooms= findViewById(R.id.btnPlusRoom);

        etCheckIn= findViewById(R.id.etCheckIn);
        etCheckOut= findViewById(R.id.etCheckOut);
        etGuests= findViewById(R.id.etGuests);
        etRooms= findViewById(R.id.etRooms);

        tvDays= findViewById(R.id.tvTotalDays);


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

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //  myCalendar2.add(Calendar.DAY_OF_MONTH,1);

                updateLabel2();
            }

        };

        btnNewWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHotelsByCityName("New Westminster");
            }
        });
        btnSurrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHotelsByCityName("Surrey");
            }
        });
        btnRichmond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHotelsByCityName("Richmond");
            }
        });
        btndowntown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHotelsByCityName("Downtown Vancouver");
            }
        });
        btnNorthVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHotelsByCityName("North Vancouver");
            }
        });
        btnBurnaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHotelsByCityName("Burnaby");
            }
        });
        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCheckIn.getText().toString().isEmpty()||etCheckOut.getText().toString().isEmpty()
                        ||etGuests.getText().toString().equals("0")||etGuests.getText().toString().equals("0")||numOfDays==0)
                {
                    Toast.makeText(ComboMainActivity.this,"Please fill in the required fields",Toast.LENGTH_LONG).show();
                }
                else {
                    db.collection(COLLECTION_NAME1)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // numberOfSeats = new ArrayList<>();
                                    finalProduct = new ArrayList<>();
                                    finalProduct2=new ArrayList<>();
                                    finalProduct3=new ArrayList<>();
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            finalProduct.add(document.getData().get("Name").toString());
                                            finalProduct2.add(document.getData().get("Location").toString());
                                            finalProduct3.add(document.getData().get("picture").toString());
                                        }
                                    } else {
                                        Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                    checkIn = etCheckIn.getText().toString();
                                    checkOut = etCheckOut.getText().toString();
                                    numGuests = Integer.parseInt(etGuests.getText().toString());
                                    numRooms = Integer.parseInt(etRooms.getText().toString());
                                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                                    editor.putInt("numRooms", numRooms);
                                    editor.putInt("numGuests", numGuests);
                                    editor.putString("checkin", checkIn);
                                    editor.putString("checkout", checkOut);
                                    editor.putInt("numdays", numOfDays);
                                    editor.apply();


                                    Intent z = new Intent(ComboMainActivity.this, ComboListActivity.class);
                                    z.putStringArrayListExtra("mfData", finalProduct);
                                    z.putStringArrayListExtra("mfData2",finalProduct2);
                                    z.putStringArrayListExtra("mfData3",finalProduct3);
                                    startActivity(z);
                                }
                            });
                }
            }
        });


        etCheckIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ComboMainActivity.this, date, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        etCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ComboMainActivity.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnPlusGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(view);

            }
        });

        btnMinusGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement(view);

            }
        });
        btnPlusRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment1(view);

            }
        });
        btnMinusRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement1(view);

            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etCheckIn.setText(sdf.format(myCalendar1.getTime()));
        etCheckIn.setFocusable(false);
        etCheckIn.setEnabled(false);
    }

    private void updateLabel2() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etCheckOut.setText(sdf.format(myCalendar2.getTime()));
        etCheckOut.setFocusable(false);
        etCheckOut.setEnabled(false);
        long diff = myCalendar2.getTimeInMillis() - myCalendar1.getTimeInMillis();
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        numOfDays= (int) dayCount;
        // tvStart.setText("You Selected: "+etStart.getText()+" to "+etEnd.getText()+"\n"+(int)dayCount+" days");
        tvDays.setText("Total Days: "+(int)dayCount+" days");


    }

    private void getHotelsByCityName(final String cityName)
    {
        db.collection(COLLECTION_NAME1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // numberOfSeats = new ArrayList<>();
                        finalProduct=new ArrayList<>();
                        finalProduct2=new ArrayList<>();
                        finalProduct3=new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("Location").toString().equals(cityName)) {
                                    finalProduct.add(document.getData().get("Name").toString());
                                    finalProduct2.add(document.getData().get("Location").toString());
                                    finalProduct3.add(document.getData().get("picture").toString());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        Log.d(TAG, "onClick:Reeeeeeeeeeee "+finalProduct.size());

                        checkIn=etCheckIn.getText().toString();
                        checkOut=etCheckOut.getText().toString();
                        numGuests=Integer.parseInt(etGuests.getText().toString());
                        numRooms=Integer.parseInt(etRooms.getText().toString());
                        SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt("numRooms_c", numRooms);
                        editor.putInt("numGuests", numGuests);
                        editor.putString("checkin", checkIn);
                        editor.putString("checkout", checkOut);
                        editor.putInt("numdays", numOfDays);
                        editor.apply();

                        Intent z=new Intent(ComboMainActivity.this,ComboListActivity.class);
                        z.putStringArrayListExtra("mfData",finalProduct);
                        z.putStringArrayListExtra("mfData2",finalProduct2);
                        z.putStringArrayListExtra("mfData3",finalProduct3);
                        startActivity(z);
                    }
                });
    }

    private void display(int number) {
        etGuests.setText("" + number);
    }
    private void display1(int number) {
        etRooms.setText("" + number);
    }

    public void increment (View view) {
        numGuests = numGuests + 1;
        display(numGuests);
    }

    public void decrement (View view) {
        if (numGuests >0){
            numGuests = numGuests - 1;
            display(numGuests);
        }

    }
    public void increment1 (View view) {
        if(numRooms<3) {
            numRooms = numRooms + 1;
        }
        else
        {
            Toast.makeText(ComboMainActivity.this,"You can book a maximum of 3 rooms per booking",Toast.LENGTH_SHORT).show();
        }
        display1(numRooms);
    }

    public void decrement1 (View view) {
        if (numRooms >0){
            numRooms = numRooms - 1;
            display1(numRooms);
        }

    }

    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }
}
