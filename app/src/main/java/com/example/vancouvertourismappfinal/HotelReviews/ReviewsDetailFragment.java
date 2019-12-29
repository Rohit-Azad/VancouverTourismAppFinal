package com.example.vancouvertourismappfinal.HotelReviews;


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

import com.example.vancouvertourismappfinal.MainActivity_1;
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
public class ReviewsDetailFragment extends Fragment {

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
    String COLLECTION_NAME="HotelReviews";

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

    public ReviewsDetailFragment() {
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
        // String s = "Some data that you want to display";
        // textV.setText("Welcome " + recvData + "\n" + s);

        sv = v.findViewById(R.id.theScroller);
        sv.removeAllViews();
        final LinearLayout l = new LinearLayout(getContext());
        final TableLayout tableLayout_1=new TableLayout(getContext());
        l.setLayoutParams(p);
        tableLayout_1.setLayoutParams(tLayoutParams);
        l.setOrientation(LinearLayout.VERTICAL);




        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                if(recvData.equals(document.getData().get("UID").toString())) {
                                    tv=new TextView(getContext());
                                    tr_1=new TableRow(getContext());
                                    HeadingsGenerator(tv,"Hotel Name: "+document.getData().get("Hotel Name").toString(),tr_1);
                                    tableLayout_1.addView(tr_1);
                                    l.addView(tableLayout_1);
                                    TextView tv1=new TextView(getContext());
                                    tv1.setTextSize(30);
                                    tv1.setTextColor(Color.WHITE);

                                    tv1.append("Guest Name:" +document.getData().get("Name").toString()+"\n");
                                    tv1.append("Guest Name:" +document.getData().get("Comment").toString()+"\n");
                                    l.addView(tv1);
                                    TextView tv2=new TextView(getContext());
                                    tv2.setTextSize(30);
                                    tv2.setTextColor(Color.WHITE);
                                    tv2.setLayoutParams(p);
                                    tv2.setBackgroundColor(Color.GRAY);
                                    tv2.setText("GUEST RATING:");
                                    l.addView(tv2);
                                    RatingBar rb=new RatingBar(getContext());
                                   // rb.setMax(5);
                                    rb.setLayoutParams(p1);

                                    rb.setRating((float) Double.parseDouble(document.getData().get("Rating").toString()));
                                    Log.d(TAG, "onComplete: "+(float) Double.parseDouble(document.getData().get("Rating").toString())+"");
                                    l.addView(rb);
                                    Button btn=new Button(getContext());
                                    btn.setLayoutParams(p);
                                    btn.setText("Go Back to Main Page");
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i=new Intent(getActivity(), MainActivity_1.class);
                                            startActivity(i);
                                        }
                                    });

                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
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
        name.setTextSize(36);
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
