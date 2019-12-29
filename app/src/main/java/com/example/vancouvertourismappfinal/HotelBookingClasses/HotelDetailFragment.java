package com.example.vancouvertourismappfinal.HotelBookingClasses;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotelDetailFragment extends Fragment {

    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    NumberFormat nf=NumberFormat.getCurrencyInstance();
    ScrollView sv;

    TextView tv;
    TableRow tr_1;
    ImageView img;
    int rooms;

    int dailyPrice1;
    int dailyPrice2;
    int dailyPrice3;
    String roomType1;
    String roomType2;
    String roomType3;
    private FirebaseFirestore db;
    private final String TAG = "ReadAllData";
    String COLLECTION_NAME="HotelsDB";

    TableRow.LayoutParams tRowParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    TableLayout.LayoutParams tLayoutParams=new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

    LinearLayout.LayoutParams p1 =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

    LinearLayout.LayoutParams imgParam =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

    public HotelDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","DetailFragment");
        View v = inflater.inflate(R.layout.fragment_hotel_detail, null);
        final String recvData = getArguments().getString("data");

        initialize();

        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        rooms=prefs.getInt("numRooms", 1);


        TextView textV = v.findViewById(R.id.txtSomething); // this is referring to the item inside fragment_hotel_detail
        String s = "Some data that you want to display";
        // textV.setText("Welcome " + recvData + "\n" + s);

        sv = v.findViewById(R.id.theScroller);
        sv.removeAllViews();
        final LinearLayout l = new LinearLayout(getContext());
        final TableLayout tableLayout_1=new TableLayout(getContext());
        final TableLayout tableLayout_2=new TableLayout(getContext());
        final TableLayout tableLayout_3=new TableLayout(getContext());
        tableLayout_1.setLayoutParams(tLayoutParams);
        tableLayout_2.setLayoutParams(tLayoutParams);
        tableLayout_3.setLayoutParams(tLayoutParams);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setLayoutParams(p);

        final TextView label_Name = new TextView(getContext());
        final TextView label_desc = new TextView(getContext());
        final TextView label_sel = new TextView(getContext());


        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                if(recvData.equals(document.getData().get("Name").toString())) {
                                    tr_1 = new TableRow(getContext());
                                    HeadingsGenerator(label_Name, document.getData().get("Name").toString(), tr_1);
                                    tableLayout_1.addView(tr_1);
                                    l.addView(tableLayout_1);

                                    RatingBar rb=new RatingBar(getContext());
                                    rb.setLayoutParams(p1);
                                    rb.setNumStars(5);
                                    rb.setRating(Integer.parseInt(document.getData().get("Star Rating").toString()));
                                    l.addView(rb);

                                    img=new ImageView(getContext());
                                    img.setLayoutParams(imgParam);

                                    Picasso.get()
                                            .load(document.getData().get("picture").toString())
                                            // .placeholder(R.drawable.wait)
                                            .error(R.drawable.error)
                                            .into(img);

                                    l.addView(img);

                                    //  tableLayout_1.removeView(tr_1);
                                    tr_1 = new TableRow(getContext());
                                    HeadingsGenerator(label_desc, "Description", tr_1);
                                    tableLayout_2.addView(tr_1);
                                    l.addView(tableLayout_2);

                                    tv=new TextView(getContext());
                                    tv.setLayoutParams(p);
                                    tv.setTextColor(Color.WHITE);
                                    tv.setTextSize(20);
                                    tv.append(document.getData().get("Description").toString());
                                    l.addView(tv);

                                    tr_1 = new TableRow(getContext());
                                    HeadingsGenerator(label_sel, "Select Your Room type:", tr_1);
                                    tableLayout_3.addView(tr_1);
                                    l.addView(tableLayout_3);
                                    if(rooms==1) {
                                        int i=1000;
                                        RadioGroup rgrp = new RadioGroup(getContext());
                                        rgrp.setLayoutParams(p);
                                        rgrp.setBackgroundColor(Color.LTGRAY);

                                        RadioButton rb1 = new RadioButton(getContext());
                                        RadioButton rb2 = new RadioButton(getContext());
                                        RadioButton rb3 = new RadioButton(getContext());
                                        RadioButton rb4 = new RadioButton(getContext());
                                        rb1.setId(i);
                                        rb2.setId(i+1);
                                        rb3.setId(i+2);
                                        rb4.setId(i+3);
                                        rb1.setLayoutParams(p);
                                        rb2.setLayoutParams(p);
                                        rb3.setLayoutParams(p);
                                        rb4.setLayoutParams(p);
                                        rb1.setText("Standard Room ( " + nf.format(Integer.parseInt(document.getData().get("Standard Price").toString())) + " ) - max. 2 guests");
                                        rb2.setText("Double Room   ( " + nf.format(Integer.parseInt(document.getData().get("Double Price").toString())) + " ) - max. 4 guests");
                                        rb3.setText("Luxury Room   ( " + nf.format(Integer.parseInt(document.getData().get("Luxury Price").toString())) + " ) - max. 4 guests");
                                        rb4.setText("Suite Room    ( " + nf.format(Integer.parseInt(document.getData().get("Suite Price").toString())) + " ) - max. 6 guests");
                                        rgrp.addView(rb1);
                                        rgrp.addView(rb2);
                                        rgrp.addView(rb3);
                                        rgrp.addView(rb4);
                                        l.addView(rgrp);
                                        rgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                        {
                                            public void onCheckedChanged(RadioGroup group, int checkedId)
                                            {
                                                // This will get the radiobutton that has changed in its check state
                                                RadioButton checkedRadioButton = group.findViewById(checkedId);
                                                // This puts the value (true/false) into the variable
                                                boolean isChecked = checkedRadioButton.isChecked();
                                                // If the radiobutton that has changed in check state is now checked...
                                                if (isChecked)
                                                {
                                                    if(checkedRadioButton.getText().toString().contains("Standard"))
                                                    {
                                                        roomType1="Standard Room";
                                                        dailyPrice1=Integer.parseInt(document.getData().get("Standard Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Double"))
                                                    {
                                                        roomType1="Double Room";
                                                        dailyPrice1=Integer.parseInt(document.getData().get("Double Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Luxury"))
                                                    {
                                                        roomType1="Luxury Room";
                                                        dailyPrice1=Integer.parseInt(document.getData().get("Luxury Price").toString());

                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Suite"))
                                                    {
                                                        roomType1="Suite";
                                                        dailyPrice1=Integer.parseInt(document.getData().get("Suite Price").toString());
                                                    }
                                                }
                                            }
                                        });
                                    }
                                    else if(rooms==2)
                                    {
                                        int i=1;
                                        TextView tv1=new TextView(getContext());
                                        tv1.setLayoutParams(p);
                                        tv1.setTextColor(Color.WHITE);
                                        tv1.setText("Room 1:");
                                        tv1.setTextSize(24);
                                        l.addView(tv1);
                                        final RadioGroup rgrp = new RadioGroup(getContext());
                                        rgrp.setLayoutParams(p);
                                        rgrp.setBackgroundColor(Color.LTGRAY);
                                        RadioButton rb1 = new RadioButton(getContext());
                                        RadioButton rb2 = new RadioButton(getContext());
                                        RadioButton rb3 = new RadioButton(getContext());
                                        RadioButton rb4 = new RadioButton(getContext());
                                        rb1.setId(i);
                                        rb2.setId(i+1);
                                        rb3.setId(i+2);
                                        rb4.setId(i+3);
                                        rb1.setLayoutParams(p);
                                        rb2.setLayoutParams(p);
                                        rb3.setLayoutParams(p);
                                        rb4.setLayoutParams(p);
                                        rb1.setText("Standard Room ( " + nf.format(Integer.parseInt(document.getData().get("Standard Price").toString())) + " ) - max. 2 guests");
                                        rb2.setText("Double Room   ( " + nf.format(Integer.parseInt(document.getData().get("Double Price").toString())) + " ) - max. 4 guests");
                                        rb3.setText("Luxury Room   ( " + nf.format(Integer.parseInt(document.getData().get("Luxury Price").toString())) + " ) - max. 4 guests");
                                        rb4.setText("Suite Room    ( " + nf.format(Integer.parseInt(document.getData().get("Suite Price").toString())) + " ) - max. 6 guests");
                                        rgrp.addView(rb1);
                                        rgrp.addView(rb2);
                                        rgrp.addView(rb3);
                                        rgrp.addView(rb4);
                                        rgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                        {
                                            public void onCheckedChanged(RadioGroup group, int checkedId)
                                            {
                                                // This will get the radiobutton that has changed in its check state
                                                RadioButton checkedRadioButton = group.findViewById(rgrp.getCheckedRadioButtonId());
                                                // This puts the value (true/false) into the variable
                                                boolean isChecked = checkedRadioButton.isChecked();
                                                // If the radiobutton that has changed in check state is now checked...
                                                if(checkedRadioButton.getText().toString().contains("Standard"))
                                                {
                                                    roomType1="Standard Room";
                                                    dailyPrice1=Integer.parseInt(document.getData().get("Standard Price").toString());
                                                    Log.d(TAG, "onCheckedChanged: "+dailyPrice1);
                                                }
                                                else if(checkedRadioButton.getText().toString().contains("Double"))
                                                {
                                                    roomType1="Double Room";
                                                    dailyPrice1=Integer.parseInt(document.getData().get("Double Price").toString());
                                                    Log.d(TAG, "onCheckedChanged: "+dailyPrice1);
                                                }
                                                else if(checkedRadioButton.getText().toString().contains("Luxury"))
                                                {
                                                    roomType1="Luxury Room";
                                                    dailyPrice1=Integer.parseInt(document.getData().get("Luxury Price").toString());
                                                    Log.d(TAG, "onCheckedChanged: "+dailyPrice1);

                                                }
                                                else if(checkedRadioButton.getText().toString().contains("Suite"))
                                                {
                                                    roomType1="Suite";
                                                    dailyPrice1=Integer.parseInt(document.getData().get("Suite Price").toString());
                                                    Log.d(TAG, "onCheckedChanged: "+dailyPrice1);
                                                }

                                            }
                                        });
                                        l.addView(rgrp);


                                        TextView tv2=new TextView(getContext());
                                        tv2.setLayoutParams(p);
                                        tv2.setTextColor(Color.WHITE);
                                        tv2.setTextSize(24);
                                        tv2.setText("Room 2:");
                                        l.addView(tv2);
                                        RadioGroup rgrp1 = new RadioGroup(getContext());
                                        rgrp1.setLayoutParams(p);
                                        rgrp1.setBackgroundColor(Color.LTGRAY);
                                        RadioButton rb11 = new RadioButton(getContext());
                                        RadioButton rb21 = new RadioButton(getContext());
                                        RadioButton rb31 = new RadioButton(getContext());
                                        RadioButton rb41 = new RadioButton(getContext());
                                        rb11.setId(i);
                                        rb21.setId(i+1);
                                        rb31.setId(i+2);
                                        rb41.setId(i+3);
                                        rb11.setLayoutParams(p);
                                        rb21.setLayoutParams(p);
                                        rb31.setLayoutParams(p);
                                        rb41.setLayoutParams(p);
                                        rb11.setText("Standard Room ( " + nf.format(Integer.parseInt(document.getData().get("Standard Price").toString())) + " ) - max. 2 guests");
                                        rb21.setText("Double Room   ( " + nf.format(Integer.parseInt(document.getData().get("Double Price").toString())) + " ) - max. 4 guests");
                                        rb31.setText("Luxury Room   ( " + nf.format(Integer.parseInt(document.getData().get("Luxury Price").toString())) + " ) - max. 4 guests");
                                        rb41.setText("Suite Room    ( " + nf.format(Integer.parseInt(document.getData().get("Suite Price").toString())) + " ) - max. 6 guests");
                                        rgrp1.addView(rb11);
                                        rgrp1.addView(rb21);
                                        rgrp1.addView(rb31);
                                        rgrp1.addView(rb41);
                                        l.addView(rgrp1);
                                        rgrp1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                        {
                                            public void onCheckedChanged(RadioGroup group, int checkedId)
                                            {
                                                // This will get the radiobutton that has changed in its check state
                                                RadioButton checkedRadioButton = group.findViewById(checkedId);
                                                // This puts the value (true/false) into the variable
                                                boolean isChecked = checkedRadioButton.isChecked();
                                                // If the radiobutton that has changed in check state is now checked...
                                                if (isChecked)
                                                {
                                                    if(checkedRadioButton.getText().toString().contains("Standard"))
                                                    {
                                                        roomType2="Standard Room";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Standard Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Double"))
                                                    {
                                                        roomType2="Double Room";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Double Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Luxury"))
                                                    {
                                                        roomType2="Luxury Room";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Luxury Price").toString());

                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Suite"))
                                                    {
                                                        roomType2="Suite";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Suite Price").toString());
                                                    }
                                                }
                                            }
                                        });
                                    }

                                    else if(rooms==3)
                                    {
                                        int i=10000;
                                        TextView tv1=new TextView(getContext());
                                        tv1.setLayoutParams(p);
                                        tv1.setTextColor(Color.WHITE);
                                        tv1.setText("Room 1:");
                                        tv1.setTextSize(24);
                                        l.addView(tv1);
                                        RadioGroup rgrp = new RadioGroup(getContext());
                                        rgrp.setLayoutParams(p);
                                        rgrp.setBackgroundColor(Color.LTGRAY);
                                        RadioButton rb1 = new RadioButton(getContext());
                                        RadioButton rb2 = new RadioButton(getContext());
                                        RadioButton rb3 = new RadioButton(getContext());
                                        RadioButton rb4 = new RadioButton(getContext());
                                        rb1.setId(i);
                                        rb2.setId(i+1);
                                        rb3.setId(i+2);
                                        rb4.setId(i+3);
                                        rb1.setLayoutParams(p);
                                        rb2.setLayoutParams(p);
                                        rb3.setLayoutParams(p);
                                        rb4.setLayoutParams(p);
                                        rb1.setText("Standard Room ( " + nf.format(Integer.parseInt(document.getData().get("Standard Price").toString())) + " ) - max. 2 guests");
                                        rb2.setText("Double Room   ( " + nf.format(Integer.parseInt(document.getData().get("Double Price").toString())) + " ) - max. 4 guests");
                                        rb3.setText("Luxury Room   ( " + nf.format(Integer.parseInt(document.getData().get("Luxury Price").toString())) + " ) - max. 4 guests");
                                        rb4.setText("Suite Room    ( " + nf.format(Integer.parseInt(document.getData().get("Suite Price").toString())) + " ) - max. 6 guests");
                                        rgrp.addView(rb1);
                                        rgrp.addView(rb2);
                                        rgrp.addView(rb3);
                                        rgrp.addView(rb4);
                                        l.addView(rgrp);
                                        rgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                        {
                                            public void onCheckedChanged(RadioGroup group, int checkedId)
                                            {
                                                // This will get the radiobutton that has changed in its check state
                                                RadioButton checkedRadioButton = group.findViewById(checkedId);
                                                // This puts the value (true/false) into the variable
                                                boolean isChecked = checkedRadioButton.isChecked();
                                                // If the radiobutton that has changed in check state is now checked...
                                                if (isChecked)
                                                {
                                                    if(checkedRadioButton.getText().toString().contains("Standard"))
                                                    {
                                                        roomType1="Standard Room";
                                                        dailyPrice1=Integer.parseInt(document.getData().get("Standard Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Double"))
                                                    {
                                                        roomType1="Double Room";
                                                        dailyPrice1=Integer.parseInt(document.getData().get("Double Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Luxury"))
                                                    {
                                                        roomType1="Luxury Room";
                                                        dailyPrice1=Integer.parseInt(document.getData().get("Luxury Price").toString());

                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Suite"))
                                                    {
                                                        roomType1="Suite";
                                                        dailyPrice1=Integer.parseInt(document.getData().get("Suite Price").toString());
                                                    }
                                                }
                                            }
                                        });


                                        TextView tv2=new TextView(getContext());
                                        tv2.setLayoutParams(p);
                                        tv2.setTextColor(Color.WHITE);
                                        tv2.setTextSize(24);
                                        tv2.setText("Room 2:");
                                        l.addView(tv2);
                                        RadioGroup rgrp1 = new RadioGroup(getContext());
                                        rgrp1.setLayoutParams(p);
                                        rgrp1.setBackgroundColor(Color.LTGRAY);
                                        RadioButton rb11 = new RadioButton(getContext());
                                        RadioButton rb21 = new RadioButton(getContext());
                                        RadioButton rb31 = new RadioButton(getContext());
                                        RadioButton rb41 = new RadioButton(getContext());
                                        rb11.setId(i);
                                        rb21.setId(i+1);
                                        rb31.setId(i+2);
                                        rb41.setId(i+3);
                                        rb11.setLayoutParams(p);
                                        rb21.setLayoutParams(p);
                                        rb31.setLayoutParams(p);
                                        rb41.setLayoutParams(p);
                                        rb11.setText("Standard Room ( " + nf.format(Integer.parseInt(document.getData().get("Standard Price").toString())) + " ) - max. 2 guests");
                                        rb21.setText("Double Room   ( " + nf.format(Integer.parseInt(document.getData().get("Double Price").toString())) + " ) - max. 4 guests");
                                        rb31.setText("Luxury Room   ( " + nf.format(Integer.parseInt(document.getData().get("Luxury Price").toString())) + " ) - max. 4 guests");
                                        rb41.setText("Suite Room    ( " + nf.format(Integer.parseInt(document.getData().get("Suite Price").toString())) + " ) - max. 6 guests");
                                        rgrp1.addView(rb11);
                                        rgrp1.addView(rb21);
                                        rgrp1.addView(rb31);
                                        rgrp1.addView(rb41);
                                        l.addView(rgrp1);

                                        rgrp1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                        {
                                            public void onCheckedChanged(RadioGroup group, int checkedId)
                                            {
                                                // This will get the radiobutton that has changed in its check state
                                                RadioButton checkedRadioButton = group.findViewById(checkedId);
                                                // This puts the value (true/false) into the variable
                                                boolean isChecked = checkedRadioButton.isChecked();
                                                // If the radiobutton that has changed in check state is now checked...
                                                if (isChecked)
                                                {
                                                    if(checkedRadioButton.getText().toString().contains("Standard"))
                                                    {
                                                        roomType2="Standard Room";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Standard Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Double"))
                                                    {
                                                        roomType2="Double Room";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Double Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Luxury"))
                                                    {
                                                        roomType2="Luxury Room";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Luxury Price").toString());

                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Suite"))
                                                    {
                                                        roomType2="Suite";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Suite Price").toString());
                                                    }
                                                }
                                            }
                                        });


                                        TextView tv3=new TextView(getContext());
                                        tv3.setLayoutParams(p);
                                        tv3.setTextColor(Color.WHITE);
                                        tv3.setText("Room 3:");
                                        tv3.setTextSize(24);
                                        l.addView(tv3);
                                        RadioGroup rgrp2 = new RadioGroup(getContext());
                                        rgrp2.setLayoutParams(p);
                                        rgrp2.setBackgroundColor(Color.LTGRAY);
                                        RadioButton rb111 = new RadioButton(getContext());
                                        RadioButton rb211 = new RadioButton(getContext());
                                        RadioButton rb311 = new RadioButton(getContext());
                                        RadioButton rb411 = new RadioButton(getContext());
                                        rb111.setId(i);
                                        rb211.setId(i+1);
                                        rb311.setId(i+2);
                                        rb411.setId(i+3);
                                        rb111.setLayoutParams(p);
                                        rb211.setLayoutParams(p);
                                        rb311.setLayoutParams(p);
                                        rb411.setLayoutParams(p);
                                        rb111.setText("Standard Room ( " + nf.format(Integer.parseInt(document.getData().get("Standard Price").toString())) + " ) - max. 2 guests");
                                        rb211.setText("Double Room   ( " + nf.format(Integer.parseInt(document.getData().get("Double Price").toString())) + " ) - max. 4 guests");
                                        rb311.setText("Luxury Room   ( " + nf.format(Integer.parseInt(document.getData().get("Luxury Price").toString())) + " ) - max. 4 guests");
                                        rb411.setText("Suite Room    ( " + nf.format(Integer.parseInt(document.getData().get("Suite Price").toString())) + " ) - max. 6 guests");
                                        rgrp2.addView(rb111);
                                        rgrp2.addView(rb211);
                                        rgrp2.addView(rb311);
                                        rgrp2.addView(rb411);
                                        l.addView(rgrp2);

                                        rgrp2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                        {
                                            public void onCheckedChanged(RadioGroup group, int checkedId)
                                            {
                                                // This will get the radiobutton that has changed in its check state
                                                RadioButton checkedRadioButton = group.findViewById(checkedId);
                                                // This puts the value (true/false) into the variable
                                                boolean isChecked = checkedRadioButton.isChecked();
                                                // If the radiobutton that has changed in check state is now checked...
                                                if (isChecked)
                                                {
                                                    if(checkedRadioButton.getText().toString().contains("Standard"))
                                                    {
                                                        roomType3="Standard Room";
                                                        dailyPrice3=Integer.parseInt(document.getData().get("Standard Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Double"))
                                                    {
                                                        roomType3="Double Room";
                                                        dailyPrice2=Integer.parseInt(document.getData().get("Double Price").toString());
                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Luxury"))
                                                    {
                                                        roomType3="Luxury Room";
                                                        dailyPrice3=Integer.parseInt(document.getData().get("Luxury Price").toString());

                                                    }
                                                    else if(checkedRadioButton.getText().toString().contains("Suite"))
                                                    {
                                                        roomType3="Suite";
                                                        dailyPrice3=Integer.parseInt(document.getData().get("Suite Price").toString());
                                                    }
                                                }
                                            }
                                        });
                                    }

                                    Button btnSubmit=new Button(getContext());
                                    btnSubmit.setLayoutParams(p);
                                    btnSubmit.setText("Submit");
                                    l.addView(btnSubmit);
                                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            SharedPreferences.Editor editor = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                                            if(rooms==1) {
                                                editor.putInt("dailyPrice1", dailyPrice1);
                                                editor.putString("roomType1", roomType1);
                                            }
                                            else if(rooms==2)
                                            {
                                                editor.putInt("dailyPrice1", dailyPrice1);
                                                editor.putString("roomType1", roomType1);
                                                editor.putInt("dailyPrice2", dailyPrice2);
                                                editor.putString("roomType2", roomType2);
                                            }
                                            else if(rooms==3)
                                            {
                                                editor.putInt("dailyPrice1", dailyPrice1);
                                                editor.putString("roomType1", roomType1);
                                                editor.putInt("dailyPrice2", dailyPrice2);
                                                editor.putString("roomType2", roomType2);
                                                editor.putInt("dailyPrice3", dailyPrice3);
                                                editor.putString("roomType3", roomType3);
                                            }
                                            editor.putString("hotelName", recvData);
                                            editor.apply();
                                            Intent i=new Intent(getActivity(),HotelPersonalDetailsActivity.class);
                                            startActivity(i);
                                        }
                                    });




                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        // Log.d(TAG, "onClick:Reeeeeeeeeeee "+finalProduct.size());

                    }
                });
        sv.addView(l);



        return v;

    }

    private void HeadingsGenerator(TextView name, String label, TableRow tr)
    {
        tr.removeView(name);
        // tr.setLayoutParams(tRowParams);
        tr.setPadding(5,2,5,2);
        //  tr.setGravity(Gravity.CENTER);
        tr.setBackgroundColor(Color.GRAY);
        name.setTextColor(Color.WHITE);
        name.setText(label);
        name.setTextSize(24);
        name.setPadding(5, 3, 5, 3);
        tr.addView(name);
    }

    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }



}
