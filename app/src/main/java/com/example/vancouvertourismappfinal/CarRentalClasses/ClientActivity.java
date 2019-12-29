package com.example.vancouvertourismappfinal.CarRentalClasses;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

public class ClientActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    public static ArrayList<AutomobileDB> myRentalList;

    Spinner mySpinner;
    //Calendar date;
    private String TAG="REEEEEEEEEEEEEE";
    private FirebaseFirestore db;
    String COLLECTION_NAME2="VehicleInventory";
    Date value = new Date();
    EditText etStart;
    EditText etEnd;
    EditText cardNum;
    TextView tvStart;
    Button submit;

    RadioGroup rGrpCardType;
    RadioGroup rGrpDailyOrHourly;
    RadioGroup rGrpInsurance;
    RadioButton rbPayDaily;
    RadioButton rbPayHourly;
    RadioButton rbCCType;
    RadioButton rbDailyHourly;
    RadioButton rbYesNo;
    CheckBox terms;

    String location;
    String dailyOrHourly;
    String insuranceReq;
    String payMethod;
    String cardNumber;
    String startTimeDate;
    String endTimeDate;
    int hoursOrDays;

    Calendar mcurrentTime1;
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    Calendar mcurrentTime2;
    DatePickerDialog.OnDateSetListener date2;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        initialize();

        mySpinner=(Spinner)findViewById(R.id.spinner);
        String[]locations={"Burnaby","New Westminster","Downtown Vancouver","Surrey","Langley"};

        etStart = (EditText) findViewById(R.id.etStartDateTime);
        etEnd = (EditText) findViewById(R.id.etEndDateTime);
        tvStart=(TextView)findViewById(R.id.tvSelections);
        rbPayDaily =(RadioButton)findViewById(R.id.rdbDaily);
        rbPayHourly =(RadioButton)findViewById(R.id.rdbHourly);
        terms=(CheckBox)findViewById(R.id.cbTerms);
        submit=(Button)findViewById(R.id.btnSubmit);

        rGrpCardType =(RadioGroup)findViewById(R.id.rdgCardType);
        rGrpDailyOrHourly=(RadioGroup)findViewById(R.id.rdgPay);
        rGrpInsurance=(RadioGroup)findViewById(R.id.rdgYN);
        cardNum=(EditText)findViewById(R.id.etCardNumber);

        Button add=(Button)findViewById(R.id.btnAdd);

        myRentalList=processData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionReference carRentalDB = db.collection(COLLECTION_NAME2);
                carRentalDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null ){
                            Log.d("ERROR", e.getMessage());
                            return;
                        }
                        if (queryDocumentSnapshots != null && queryDocumentSnapshots.size() >0){

                            List<DocumentChange> ldoc = queryDocumentSnapshots.getDocumentChanges();
                            Log.d("OnEvent", ldoc.size() + "");

                            // index 0 is used. We are assuming only 1 change was made
                            Log.d("OnEvent", ldoc.get(0).getDocument().getId() + "");

                            Log.d("OnEvent", ldoc.get(0).getDocument().getData() + "");
                            Map<String,Object> m = ldoc.get(0).getDocument().getData();

                            Toast.makeText(ClientActivity.this, "Current data:" + ldoc.size(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                Map<String, Object> data;
                Map<String,String> data2;

                for(int i=0;i<myRentalList.size();i++) {
                    data = new HashMap<>();
                    data2=new HashMap<>(0);
                    Log.d("onClick: ",myRentalList.get(i).model);
                    data.put("ID",i);
                    data.put("Type", myRentalList.get(i).type);
                    data.put("Make", myRentalList.get(i).make);
                    data.put("Model", myRentalList.get(i).model);
                    data.put("Seats", Integer.parseInt(myRentalList.get(i).seats));
                    data.put("Quantity", Integer.parseInt(myRentalList.get(i).quantity));
                    data.put("picture", myRentalList.get(i).picture);
                    data.put("Hourly Price", Integer.parseInt(myRentalList.get(i).pricePerHour));
                    data.put("Daily Price", Integer.parseInt(myRentalList.get(i).pricePerDay));
                    data.put("Color", Arrays.asList(myRentalList.get(i).colors));
                    carRentalDB.document(String.valueOf(i)+" - " +myRentalList.get(i).make).set(data);
                }
            }
        });




        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, locations);
        mySpinner.setAdapter(spinnerArrayAdapter);

        etStart=(EditText)findViewById(R.id.etStartDateTime);


        cardNum.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

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

                updateLabel2();
            }

        };

        etStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(rbPayDaily.isChecked()) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(ClientActivity.this, date, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

                }
                else if(rbPayHourly.isChecked()) {
                    // TODO Auto-generated method stub
                  //  Calendar mcurrentTime = Calendar.getInstance();
                    mcurrentTime2 = Calendar.getInstance();
                    mcurrentTime2.add(Calendar.HOUR_OF_DAY,1);
                    int hour = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
                    int minute = 0;

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(ClientActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            etStart.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            etStart.setFocusable(false);
                            etStart.setEnabled(false);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Pickup Time");
                    mTimePicker.show();
                }
            }
        });


        etEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbPayDaily.isChecked()) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(ClientActivity.this, date2, myCalendar2
                            .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                            myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
                }
                else {
                    // TODO Auto-generated method stub
                    mcurrentTime1 = Calendar.getInstance();
                    mcurrentTime1.add(Calendar.HOUR_OF_DAY,3);
                    int hour = mcurrentTime1.get(Calendar.HOUR_OF_DAY);
                    int minute = 0;

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(ClientActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            etEnd.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            etEnd.setFocusable(false);
                            etEnd.setEnabled(false);
                            int total=Integer.parseInt(etEnd.getText().toString().substring(0,2))-Integer.parseInt(etStart.getText().toString().substring(0,2));
                           // tvStart.setText("You Selected: "+etStart.getText()+" to "+etEnd.getText()+"\n"+"Total Hours: "+total + " hours");
                            tvStart.setText("Total Hours: "+total + " hours");
                            hoursOrDays=total;
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Return Time");
                    mTimePicker.show();
                }


            }
        });

        rbPayHourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etStart.getText().clear();
                etEnd.getText().clear();
                etStart.setFocusable(true);
                etStart.setEnabled(true);
                etEnd.setFocusable(true);
                etEnd.setEnabled(true);
            }
        });
        rbPayDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etStart.getText().clear();
                etStart.setFocusable(true);
                etStart.setEnabled(true);
                etEnd.setFocusable(true);
                etEnd.setEnabled(true);
                etEnd.getText().clear();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(terms.isChecked())||
                        cardNum.getText().toString().isEmpty()||
                        mySpinner.getSelectedItem().toString().isEmpty()||
                        etStart.getText().toString().isEmpty()||
                        etEnd.getText().toString().isEmpty()
                )
                {
                    Toast.makeText(ClientActivity.this,"Pleasefill in all the details and Accept Terms and Conditions before Continuing",Toast.LENGTH_LONG).show();

                }
                else
                {
                    int selectedId = rGrpCardType.getCheckedRadioButtonId();
                    rbCCType=(RadioButton)findViewById(selectedId);
                    rbDailyHourly=(RadioButton)findViewById(rGrpDailyOrHourly.getCheckedRadioButtonId());
                    rbYesNo=(RadioButton)findViewById(rGrpInsurance.getCheckedRadioButtonId());
                    payMethod=rbCCType.getText().toString();
                    cardNumber=cardNum.getText().toString();
                    insuranceReq=rbYesNo.getText().toString();
                    dailyOrHourly=rbDailyHourly.getText().toString();
                    location=mySpinner.getSelectedItem().toString();
                    startTimeDate=etStart.getText().toString();
                    endTimeDate=etEnd.getText().toString();
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("CCType", payMethod);
                    editor.putString("location", location);
                    editor.putString("dOrH", dailyOrHourly);
                    Log.d(TAG, "onClickREEEEEEEE: "+ dailyOrHourly);
                    editor.putString("sTD", startTimeDate);
                    editor.putString("eTD", endTimeDate);
                    editor.putString("insurance", insuranceReq);
                    editor.putString("ccNumber", cardNumber);
                    editor.putInt("time",hoursOrDays);
                    editor.apply();

                    Intent i = new Intent(ClientActivity.this, ClientInfoActivity.class);
                    startActivity(i);
                    }
            }
        });





    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etStart.setText(sdf.format(myCalendar1.getTime()));
        etStart.setFocusable(false);
        etStart.setEnabled(false);
    }

    private void updateLabel2() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etEnd.setText(sdf.format(myCalendar2.getTime()));
        etEnd.setFocusable(false);
        etEnd.setEnabled(false);
        long diff = myCalendar2.getTimeInMillis() - myCalendar1.getTimeInMillis();
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        hoursOrDays= (int) dayCount;
       // tvStart.setText("You Selected: "+etStart.getText()+" to "+etEnd.getText()+"\n"+(int)dayCount+" days");
        tvStart.setText("Total Days: "+(int)dayCount+" days");


    }

    private ArrayList<AutomobileDB> processData() {
        String json = null;
        try {
            InputStream is = getAssets().open("auto_3.json");


            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        ArrayList<AutomobileDB> temp = new ArrayList<>();
        // ArrayList<String> color;
        String[] colors;
        try {

            JSONArray ar = new JSONArray(json);
            JSONObject element;
            AutomobileDB autoDB;

            for (int i=0 ; i < ar.length(); i++) {
                element = ar.getJSONObject(i);
                autoDB = new AutomobileDB();
                colors=new String[4];
                // color=new ArrayList<>();
                // autoDB.id = element.getString("id");
                autoDB.type = element.getString("vehicle_type");
                autoDB.make = element.getString("car_make");
                autoDB.model = element.getString("car_model");
                autoDB.quantity = element.getString("quantity");
                autoDB.picture = element.getString("picture");
                autoDB.seats = element.getString("seats");
                autoDB.pricePerHour = element.getString("price_hour");
                autoDB.pricePerDay= element.getString("price_day");
                for(int j=0;j<4;j++) {
                    colors[j]=element.getJSONArray("color").getString(j);
                }
                autoDB.colors=colors;
                temp.add(autoDB);
            }
            return temp;
        } catch (JSONException e) {
            Log.d("MainActivity", e.getMessage());
        }

        return null;
    }
    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

}
