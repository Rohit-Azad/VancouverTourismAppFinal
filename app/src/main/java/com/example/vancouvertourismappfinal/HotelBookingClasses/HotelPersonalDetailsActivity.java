package com.example.vancouvertourismappfinal.HotelBookingClasses;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.R;

public class HotelPersonalDetailsActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    RadioGroup rGrpCardType;
    RadioButton rbCCType;
    RadioGroup rbYesNo;

    RadioButton rbSelected;

    EditText etName;
    EditText etPhone;
    EditText etEMail;
    EditText etCardNum;
    EditText etExpiry;
    EditText etCvv;

    String name;
    String phoneNumber;
    String eMail;
    String ccType;
    String ccNum;
    String ccExpiry;
    String ccCVV;
    String payWhen;

    CheckBox terms;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_personal_details);

        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        etEMail= (EditText) findViewById(R.id.etEmail);
        etExpiry=(EditText)  findViewById(R.id.etExpiry);
        etEMail= (EditText) findViewById(R.id.etEmail);
        etCvv =(EditText)  findViewById(R.id.etCvv);
        terms=(CheckBox) findViewById(R.id.cbTerms);
        submit=(Button) findViewById(R.id.btnSubmit);

        rGrpCardType =(RadioGroup) findViewById(R.id.rdgCardType);
        rbYesNo= (RadioGroup) findViewById(R.id.rdgYN);
        etCardNum= (EditText) findViewById(R.id.etCardNum);

        rGrpCardType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                rbCCType = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = rbCCType.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    ccType=rbCCType.getText().toString();
                    Log.d("TAG", "onCheckedChanged: "+ ccType);
                }
            }
        });

        rbYesNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                rbSelected = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = rbSelected.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    payWhen=rbSelected.getText().toString();
                    Log.d("TAG", "onCheckedChanged: "+ payWhen);
                }
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

        etCardNum.addTextChangedListener(new TextWatcher() {

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etName.getText().toString().isEmpty()||etPhone.getText().toString().isEmpty()||
                        etEMail.getText().toString().isEmpty()||etCardNum.getText().toString().isEmpty()||
                        etExpiry.getText().toString().isEmpty()||etCvv.getText().toString().isEmpty()||terms.isChecked()==false)
                {
                    Toast.makeText(HotelPersonalDetailsActivity.this,"Please fill in all the details and accept terms and conditions",Toast.LENGTH_LONG).show();
                }
                else {
                    name=etName.getText().toString();
                    phoneNumber=etPhone.getText().toString();
                    eMail=etEMail.getText().toString();
                  //  rbCCType=(RadioButton) findViewById(rGrpCardType.getCheckedRadioButtonId());
                  //  ccType=rbCCType.getText().toString();
                    ccNum=etCardNum.getText().toString();
                    ccCVV=etCvv.getText().toString();
                    ccExpiry=etExpiry.getText().toString();
                   // rbSelected= (RadioButton)findViewById(rGrpCardType.getCheckedRadioButtonId());
                 //   payWhen=rbSelected.getText().toString();

                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("name", name);
                    editor.putString("guestPhoneNum", phoneNumber);
                    editor.putString("guestEmail", eMail);
                    editor.putString("ccType", ccType);
                    editor.putString("cardNumber", ccNum);
                    editor.putString("CVV", ccCVV);
                    editor.putString("expiry", ccExpiry);
                    editor.putString("payWhen", payWhen);
                    editor.apply();
                    Intent i=new Intent(HotelPersonalDetailsActivity.this,BookingConfirmationActivity.class);
                    startActivity(i);
                }

            }
        });
    }

}
