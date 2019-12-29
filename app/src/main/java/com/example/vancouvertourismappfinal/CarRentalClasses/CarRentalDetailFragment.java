package com.example.vancouvertourismappfinal.CarRentalClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CarRentalDetailFragment extends Fragment {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    NumberFormat nf=NumberFormat.getCurrencyInstance();
    ScrollView sv;
    TextView tv;
    ImageView img;
    private FirebaseFirestore db;
    RadioGroup rdg;
    RadioGroup.LayoutParams rprms;
    private final String TAG = "ReadAllData";
    String COLLECTION_NAME="VehicleInventory";
    String selectedCar;
    String selectedColor;
    int price;
    String hourlyOrDaily;

    int quantity;
    String link;

    String test;
    String[]test2;
    TableRow tr_1;
    TableRow.LayoutParams tRowParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    TableLayout.LayoutParams tLayoutParams=new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams imgParam =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);



    public CarRentalDetailFragment() {

        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView", "DetailFragment");
        View v = inflater.inflate(R.layout.fragment_car_detail, null);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        initialize();

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        hourlyOrDaily=prefs.getString("dOrH", "No fullName defined");


        final String recvData = getArguments().getString("data");
        final String[]test=recvData.split(" ");


        sv = (ScrollView) v.findViewById(R.id.theScroller);
        sv.removeAllViews();

        final TableLayout tableLayout_1=new TableLayout(getContext());
        final LinearLayout l = new LinearLayout(getContext());
        l.setOrientation(LinearLayout.VERTICAL);
        final TextView heading = new TextView(getContext());

        final TextView label_model = new TextView(getContext());
    //    label_model.setTextSize(22);
        final TextView label_type = new TextView(getContext());
     //   label_type.setTextSize(22);
        final TextView label_priceHourly = new TextView(getContext());
    //    label_priceHourly.setTextSize(22);
        final TextView label_street = new TextView(getContext());
    //    label_street.setTextSize(22);
        final TextView label_name = new TextView(getContext());
    //    label_name.setTextSize(22);
        final TextView label_prices = new TextView(getContext());
     //   label_prices.setTextSize(22);

        final TextView city = new TextView(getContext());
    //    city.setTextSize(20);
        final TextView type = new TextView(getContext());
       // type.setTextSize(20);
        final TextView priceHour = new TextView(getContext());
     //   priceHour.setTextSize(20);
        final TextView priceDaily = new TextView(getContext());
     //   priceDaily.setTextSize(20);
        final TextView name = new TextView(getContext());
    //    name.setTextSize(20);
        final TextView model = new TextView(getContext());
        final TextView heading1=new TextView(getContext());

        tableLayout_1.setLayoutParams(tLayoutParams);

       // final LinearLayout l = new LinearLayout(getContext());
        tv=new TextView(getContext());
        rdg=new RadioGroup(getContext());
        rprms= new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.setOrientation(LinearLayout.VERTICAL);


        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            TextView tv,tv2;
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(test[0].equals(document.getData().get("ID").toString()))
                                {
                                    tr_1 = new TableRow(getContext());
                                    HeadingsGenerator(heading, "Vehicle Info", tr_1);
                                    tableLayout_1.addView(tr_1);
                                    tr_1=new TableRow(getContext());
                                    tableDataGenerator(label_name, "Make", tr_1);
                                    tableDataGenerator(name, document.getData().get("Make").toString(), tr_1);
                                    //   tableLayout_1.removeView(tr_1);
                                    tableLayout_1.addView(tr_1);

                                    tr_1 = new TableRow(getContext());
                                    tableDataGenerator(label_model, "model", tr_1);
                                    tableDataGenerator(model, document.getData().get("Model").toString(), tr_1);
                                    //    tableLayout_1.removeView(tr_1);
                                    tableLayout_1.addView(tr_1);

                                    tr_1 = new TableRow(getContext());
                                    tableDataGenerator(label_type, "Type", tr_1);
                                    tableDataGenerator(type, document.getData().get("Type").toString(), tr_1);
                                    //   tableLayout_1.removeView(tr_1);
                                    tableLayout_1.addView(tr_1);
                                    tr_1 = new TableRow(getContext());
                                    HeadingsGenerator(label_prices, "Prices", tr_1);
                                    tableLayout_1.addView(tr_1);

                                    tr_1=new TableRow(getContext());
                                    tableDataGenerator(label_priceHourly, "Hourly ", tr_1);
                                    tableDataGenerator(priceHour, nf.format(Integer.parseInt(document.getData().get("Hourly Price").toString())), tr_1);
                                    //  tableLayout_1.removeView(tr_1);
                                    tableLayout_1.addView(tr_1);
                                    tr_1=new TableRow(getContext());
                                    tableDataGenerator(label_street, "Daily", tr_1);
                                    tableDataGenerator(priceDaily, nf.format(Integer.parseInt(document.getData().get("Daily Price").toString())), tr_1);
                                    //  tableLayout_1.removeView(tr_1);
                                    tableLayout_1.addView(tr_1);
                                    l.addView(tableLayout_1);
                                    city.append("Picture");
                                    city.setTextSize(22);
                                    city.setBackgroundColor(Color.GRAY);
                                    city.setTextColor(Color.WHITE);
                                    l.addView(city);
                                    if(hourlyOrDaily.equals("Daily"))
                                    {
                                        price=Integer.parseInt(document.getData().get("Daily Price").toString());
                                    }
                                    else
                                    {
                                        price=Integer.parseInt(document.getData().get("Hourly Price").toString());
                                    }
                                    selectedCar= document.getData().get("Make")+" "+document.getData().get("Model").toString();
                                    String test=document.getData().get("Color").toString();
                                    test2=new String[4];
                                    test2=test.substring(1,test.length()-1).split(",");
                                    img=new ImageView(getContext());
                                    link=document.getData().get("picture").toString();
                                    Log.d(TAG, "onComplete: "+link);
                                    img.setLayoutParams(imgParam);

                                    Picasso.get()
                                            .load(link)
                                           // .placeholder(R.drawable.wait)
                                            .error(R.drawable.error)
                                            .into(img);

                                    l.addView(img);

                                    tv2=new TextView(getContext());
                                    tv2.setText("Please choose the colour:");
                                    tv2.setTextSize(22);
                                    tv2.setBackgroundColor(Color.GRAY);
                                    tv2.setTextColor(Color.WHITE);
                                    l.addView(tv2);
                                    final Spinner s=new Spinner(getContext());
                                    s.setLayoutParams(tLayoutParams);
                                    s.setBackgroundColor(Color.LTGRAY);
                                    ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                                            getContext(), android.R.layout.simple_spinner_item, test2);
                                    s.setSelection(0);
                                    s.setAdapter(spinnerArrayAdapter3);
                                    s.setPadding(8,8,8,8);
                                    l.addView(s);
                                    heading1.append("\n\n");
                                    l.addView(heading1);

                                    quantity=Integer.parseInt(document.getData().get("Quantity").toString());




                                    Button btnConfirm=new Button(getContext());
                                    btnConfirm.setText("Confirm");
                                    btnConfirm.setLayoutParams(tLayoutParams);
                                    if(quantity>0) {
                                      //  selectedColor=s.getSelectedItem().toString();
                                        final Map<String, Object> updateQuantity = new HashMap<>();

                                        final String UID = document.getId();

                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                selectedColor=s.getSelectedItem().toString();
                                                SharedPreferences.Editor editor = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                                                editor.putString("selectedColor", selectedColor);
                                                editor.putString("selectedCar", selectedCar);
                                                editor.putInt("price", price);
                                                editor.apply();
                                                Log.d(TAG, "onClick: "+selectedColor);
                                                int updatedQuant=quantity -1;
                                                updateQuantity.put("Quantity", updatedQuant);
                                                db.collection(COLLECTION_NAME).document(UID).update(updateQuantity);


                                                Intent i = new Intent(getActivity(), RentalFinalizeActivity.class);
                                                startActivity(i);

                                            }

                                        });
                                        l.addView(btnConfirm);
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(),"Sorry. Out of stock",Toast.LENGTH_LONG).show();
                                    }

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
    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }
    private void tableDataGenerator(TextView name, String label, TableRow tr)
    {
        tr.removeView(name);
        tr.setLayoutParams(tRowParams);
        tr.setPadding(5,2,5,2);
        name.setTextColor(Color.WHITE);
        name.setText(label);
        name.setTextSize(16);
        name.setPadding(5, 3, 5, 3);
        tr.addView(name);
    }
    private void HeadingsGenerator(TextView name, String label, TableRow tr)
    {
        tr.removeView(name);
        tr.setLayoutParams(tRowParams);
        tr.setPadding(5,2,5,2);
      //  tr.setGravity(Gravity.CENTER);
        tr.setBackgroundColor(Color.GRAY);
        name.setTextColor(Color.WHITE);
        name.setText(label);
        name.setTextSize(22);
        name.setPadding(5, 3, 5, 3);
        tr.addView(name);
    }


}


