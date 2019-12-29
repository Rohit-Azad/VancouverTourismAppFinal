package com.example.vancouvertourismappfinal.HotelReviews;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.R;

public class HotelsAddReview extends AppCompatActivity {

    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    Spinner spinner;
    EditText etName;
    CheckBox cbAnon;

    RadioGroup rgCheckin;
    RadioGroup rgLocation;
    RadioGroup rgStaff;
    RadioGroup rgRoom;

    RadioButton rbSelectedCheckin;
    RadioButton rbSelectedLocation;
    RadioButton rbSelectedStaff;
    RadioButton rbSelectedRoom;

    int checkinRating,staffRating,roomRating,locationRating;
    double avgrating;
    String name,hotelName,personalComment;

    EditText etPersonalComment;

    Button btnAddReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_add_review);

        etName=(EditText)findViewById(R.id.etName);
        etPersonalComment=(EditText)findViewById(R.id.etCustomReview);
        spinner=(Spinner)findViewById(R.id.spinner);

        rgCheckin=(RadioGroup)findViewById(R.id.rgCheckIn);
        rgLocation=(RadioGroup)findViewById(R.id.rgLocation);
        rgRoom=(RadioGroup)findViewById(R.id.rgRoom);
        rgStaff=(RadioGroup)findViewById(R.id.rgStaff);

        btnAddReview=(Button)findViewById(R.id.btnAdd);
        cbAnon=(CheckBox)findViewById(R.id.cbAnon);
        cbAnon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  if(cbAnon.isChecked())
                  {
                      etName.setText("Anonymous");
                  }
                  else
                  {
                      etName.getText().clear();
                  }

              }
          }
        );

        rgCheckin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                rbSelectedCheckin = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = rbSelectedCheckin.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    checkinRating=Integer.parseInt(rbSelectedCheckin.getText().toString());
                    Log.d("TAG", "onCheckedChanged: "+ checkinRating);
                }
            }
        });
        rgLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                rbSelectedLocation = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = rbSelectedLocation.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    locationRating=Integer.parseInt(rbSelectedLocation.getText().toString());
                }
            }
        });
        rgRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                rbSelectedRoom = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = rbSelectedRoom.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    roomRating=Integer.parseInt(rbSelectedRoom.getText().toString());
                    Log.d("TAG", "onCheckedChanged: "+ checkinRating);
                }
            }
        });
        rgStaff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                rbSelectedStaff = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = rbSelectedStaff.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    staffRating=Integer.parseInt(rbSelectedStaff.getText().toString());
                    Log.d("TAG", "onCheckedChanged: "+ checkinRating);
                }
            }
        });
        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().isEmpty())
                {
                    Toast.makeText(HotelsAddReview.this,"Please add a name or check Anonymous",Toast.LENGTH_LONG).show();
                }
                else
                {
                    name=etName.getText().toString();
                    hotelName=spinner.getSelectedItem().toString();
                    if(etPersonalComment.getText().toString().isEmpty())
                    {
                        personalComment="N/A";
                    }
                    else
                    {
                        personalComment=etPersonalComment.getText().toString();
                    }
                    avgrating=(staffRating+checkinRating+locationRating+roomRating)/4.0;
                    Log.d( "onClick: ",avgrating+"");
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("reviewName", name);
                    editor.putString("hotelNameReview", hotelName);
                    editor.putString("pCom", personalComment);
                    editor.putFloat("avgrating", (float) avgrating);
                    editor.apply();
                    Intent i=new Intent(HotelsAddReview.this,HotelReviewAddedActivity.class);
                    startActivity(i);

                }
            }
        });
    }
}
