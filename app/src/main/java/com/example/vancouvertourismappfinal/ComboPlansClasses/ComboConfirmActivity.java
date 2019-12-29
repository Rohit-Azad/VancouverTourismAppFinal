package com.example.vancouvertourismappfinal.ComboPlansClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.HotelBookingClasses.BookingConfirmationActivity;
import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelPersonalDetailsActivity;
import com.example.vancouvertourismappfinal.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.NumberFormat;

public class ComboConfirmActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    String COLLECTION_NAME="ComboBookings";
    NumberFormat nf=NumberFormat.getNumberInstance();


    int numGuests;
    int numRooms;
    int numOfDays;
    String checkIn;
    String checkOut;
    String room1,room2,room3,selectedCar,selectedHotel;

    TextView tvComboPrice,tvComboTax,tvComboTotalPrice;
    EditText etName,etExpiry,etCardNumber;
    Spinner spinnerType;

    Button submit;

    int price;
    double tax;
    double totalPrice;
    String cardType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_confirm);



        tvComboPrice=findViewById(R.id.tvComboPrice);
        tvComboTax=findViewById(R.id.tvComboTax);
        tvComboTotalPrice=findViewById(R.id.tvComboTotalPrice);

        etCardNumber=findViewById(R.id.etCardNumber);
        etExpiry=findViewById(R.id.etExpiry);
        etName=findViewById(R.id.etComboName);
        spinnerType=findViewById(R.id.spinnerCardType);
        submit=findViewById(R.id.btnComboConfirm);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             cardType=spinnerType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int len=s.toString().length();

                if (before == 0 && len == 2)
                    etExpiry.append("/");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCardNumber.addTextChangedListener(new TextWatcher() {

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

       if(numRooms==1)
       {
           price=(149+139)*numOfDays;
           tax=price*0.15;
           totalPrice=price*1.15;
           tvComboPrice.setText((price)+"");
           tvComboTax.setText((tax)+"");
           tvComboTotalPrice.setText((totalPrice)+"");
       }else if(numRooms==2)
       {
           price=(139+139)*numOfDays*2;
           tax=price*0.15;
           totalPrice=price*1.15;
           tvComboPrice.setText((price)+"");
           tvComboTax.setText((tax)+"");
           tvComboTotalPrice.setText((totalPrice)+"");
       }else if(numRooms==3)
       {
           price=(139+119)*numOfDays*3;
           tax=price*0.15;
           totalPrice=price*1.15;
           tvComboPrice.setText(price+"");
           tvComboTax.setText((tax)+"");
           tvComboTotalPrice.setText((totalPrice)+"");
       }
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(etName.getText().toString().isEmpty()||etExpiry.getText().toString().isEmpty()
               ||etCardNumber.getText().toString().isEmpty())
               {
                   Toast.makeText(ComboConfirmActivity.this,"Please fill up all the info",Toast.LENGTH_LONG).show();
               }
               else
               {
                   SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                   editor.putString("name_combo", etName.getText().toString());
                   editor.putString("cardnum_combo", etCardNumber.getText().toString());
                   editor.putString("expiry_combo", etExpiry.getText().toString());
                   editor.putString("price_combo", tvComboPrice.getText().toString());
                   editor.putString("tax_combo", tvComboTax.getText().toString());
                   editor.putString("totalPrice_combo", tvComboTotalPrice.getText().toString());
                   editor.putString("cardType_combo", cardType);
                   editor.apply();
                   Intent i=new Intent(ComboConfirmActivity.this, ComboDoneActivity.class);
                   startActivity(i);
               }
           }
       });


    }
}
