package com.example.vancouvertourismappfinal.CarRentalClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SelectionCriteriaActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    String COLLECTION_NAME="VehicleInventory";

    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    String TAG="A test";
    Spinner spinnerTypes;
    Spinner spinnerSeats;
    Spinner spinnerPriceRange;
    Spinner spinnerMake;
    ArrayList<AutomobileDB> test;

    CheckBox cbSelectAllTypes;
    CheckBox cbSelectAllSeats;
    CheckBox cbSelectAllPrices;
    CheckBox cbSelectedMake;

    CheckBox cbSelectAll;
    ArrayList<String> types=new ArrayList<>();
    ArrayList<String> typesTruncated=new ArrayList<>();
    ArrayList<String> makes=new ArrayList<>();
    ArrayList<String> makesTruncated=new ArrayList<>();

    Button btnSearchCustomized;
    Button btnShowAll;
    String[] seats={"2","3","4","5","6","7","8"};
    String selectedType;
    String selectedMake;
    String selectedSeats;
    String selectedPriceRange;
    String hourlyOrDaily;

    ArrayList<Integer> priceRangerDaily;
    ArrayList<Integer> priceRangerHourly;
    ArrayList<Integer> numberOfSeats;
    ArrayList<Integer> priceRangerTruncated;
    ArrayList<Integer> newPrice;
    ArrayList<Integer> newPriceHourly;
    ArrayList<Integer> newSeats;
    String[] priceRange;

    AutomobileDB testRee;

    ArrayList<String> finalProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_criteria);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        hourlyOrDaily=prefs.getString("dOrH", "No fullName defined");


        initialize();
        //spinnerPriceRange=(Spinner)findViewById(R.id.spinnerPriceRange);
        spinnerSeats=(Spinner)findViewById(R.id.spinnerSeats);
        spinnerTypes=(Spinner)findViewById(R.id.spinnerVehicleType);
        spinnerMake=(Spinner)findViewById(R.id.spinnerMake);

        cbSelectAll=(CheckBox)findViewById(R.id.cbSelectAll);
       // cbSelectAllPrices=(CheckBox)findViewById(R.id.cbSelectAllPrice);
        cbSelectAllSeats=(CheckBox)findViewById(R.id.cbSelectAllSeats);
        cbSelectAllTypes=(CheckBox)findViewById(R.id.cbSelectAllVehicles);
        cbSelectedMake=(CheckBox)findViewById(R.id.cbSelectMake);

        btnSearchCustomized=(Button)findViewById(R.id.btnSearchCustomized);
        btnShowAll=(Button)findViewById(R.id.btnShowAll);
        btnShowAll.setVisibility(View.INVISIBLE);
        test = new ArrayList<>();
        testRee=new AutomobileDB();
        final String[] colors=new String[4];

        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                      //  priceRangerTruncated = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //  temp.add(document.getData().get(key[6]).toString());
                                testRee.pricePerDay=(document.getData().get("Daily Price").toString());
                                for (int i=0;i<colors.length;i++) {
                                    colors[i] = (document.getData().get("Color")).toString();
                                }
                                testRee.colors=colors;
                                testRee.make=(document.getData().get("Make").toString());
                                testRee.model=(document.getData().get("Model").toString());
                                testRee.pricePerHour=(document.getData().get("Hourly Price").toString());
                                testRee.quantity=(document.getData().get("Quantity").toString());
                                testRee.seats=(document.getData().get("Seats").toString());
                                testRee.type=(document.getData().get("Type").toString());
                                test.add(testRee);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                     //   priceRangerTruncated = (removeDuplicates(priceRangerDaily));
                    }
                });

        if(hourlyOrDaily.equals("Daily")) {
            db.collection(COLLECTION_NAME)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            priceRangerDaily = new ArrayList<>();
                            priceRangerTruncated = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //  temp.add(document.getData().get(key[6]).toString());
                                    priceRangerDaily.add(Integer.parseInt(document.getData().get("Daily Price").toString()));

                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                            priceRangerTruncated = (removeDuplicates(priceRangerDaily));
                        }
                    });
        }
        else if(hourlyOrDaily.equals("Hourly")) {
            db.collection(COLLECTION_NAME)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            priceRangerHourly = new ArrayList<>();
                        //    priceRangerTruncated = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //  temp.add(document.getData().get(key[6]).toString());
                                    priceRangerHourly.add(Integer.parseInt(document.getData().get("Hourly Price").toString()));

                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                          //  priceRangerTruncated = (removeDuplicates(priceRangerDaily));
                        }
                    });
        }
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //  temp.add(document.getData().get(key[6]).toString());
                                types.add(document.getData().get("Type").toString());

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        typesTruncated=(removeDuplicates(types));
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                SelectionCriteriaActivity.this, android.R.layout.simple_spinner_item, typesTruncated);
                        spinnerTypes.setSelection(0);
                        spinnerTypes.setAdapter(spinnerArrayAdapter);
                    }
                });
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        numberOfSeats = new ArrayList<>();
                        //    priceRangerTruncated = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //  temp.add(document.getData().get(key[6]).toString());
                                numberOfSeats.add(Integer.parseInt(document.getData().get("Seats").toString()));

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //  temp.add(document.getData().get(key[6]).toString());
                                makes.add(document.getData().get("Make").toString());

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        makesTruncated=(removeDuplicates(makes));
                        ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(
                                SelectionCriteriaActivity.this, android.R.layout.simple_spinner_item, makesTruncated);
                        spinnerMake.setSelection(0);
                        spinnerMake.setAdapter(spinnerArrayAdapter4);

//                        if(hourlyOrDaily.equals("Hourly"))
//                        {
//                            priceRange= new String[]{"<20", "20 - 25", "> 25"};
//                        }
//                        else if(hourlyOrDaily.equals("Daily"))
//                        {
//                            priceRange=new String[]{"< 160", "160 - 220", "> 220"};
//                        }
                        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                                SelectionCriteriaActivity.this, android.R.layout.simple_spinner_item, seats);
                        spinnerSeats.setSelection(0);
                        spinnerSeats.setAdapter(spinnerArrayAdapter2);


                    }
                });



        cbSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbSelectAll.isChecked())
                {
                    btnShowAll.setVisibility(View.VISIBLE);
                    btnSearchCustomized.setVisibility(View.INVISIBLE);
                }
                else
                {
                    btnShowAll.setVisibility(View.INVISIBLE);
                    btnSearchCustomized.setVisibility(View.VISIBLE);
                }

            }
        });

      btnSearchCustomized.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

//              if(!cbSelectAllPrices.isChecked())
//              {
//                  spinnerPriceRange.setVisibility(View.VISIBLE);
//                  selectedPriceRange=spinnerPriceRange.getSelectedItem().toString();
//                  switch (selectedPriceRange) {
//                      case "< 160": {
//                          newPrice=new ArrayList<>();
//                          db.collection(COLLECTION_NAME)
//                                  .get()
//                                  .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                      @Override
//                                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                          if (task.isSuccessful()) {
//                                              for (QueryDocumentSnapshot document : task.getResult()) {
//                                                  if((Integer.parseInt(document.getData().get("Daily Price").toString())<160))
//                                                  {
//                                                      //  temp.add(document.getData().get(key[6]).toString());
//                                                      newPrice.add(Integer.parseInt(document.getData().get("Daily Price").toString()));
//                                                  }
//                                              }
//                                          } else {
//                                              Log.w(TAG, "Error getting documents.", task.getException());
//                                          }
//                                         // priceRangerTruncated = (removeDuplicates(priceRangerDaily));
//                                      }
//                                  });
////                          newPrice=new ArrayList<>();
////                          for(int i=0;i<priceRangerTruncated.size();i++) {
////                              if (priceRangerTruncated.get(i) < 160) {
////                                  newPrice.add(priceRangerTruncated.get(i));
////                              }
////                          }
//                          break;
//                      }
//                      case "160 - 220":{
//                          newPrice=new ArrayList<>();
//                          db.collection(COLLECTION_NAME)
//                                  .get()
//                                  .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                      @Override
//                                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                          if (task.isSuccessful()) {
//                                              for (QueryDocumentSnapshot document : task.getResult()) {
//                                                  if((Integer.parseInt(document.getData().get("Daily Price").toString())>=160&&
//                                                          (Integer.parseInt(document.getData().get("Daily Price").toString()))<=220  ))
//                                                  {
//                                                      //  temp.add(document.getData().get(key[6]).toString());
//                                                      newPrice.add(Integer.parseInt(document.getData().get("Daily Price").toString()));
//                                                  }
//                                              }
//                                          } else {
//                                              Log.w(TAG, "Error getting documents.", task.getException());
//                                          }
//                                          // priceRangerTruncated = (removeDuplicates(priceRangerDaily));
//                                      }
//                                  });
////                          newPrice=new ArrayList<>();
////                          for(int i=0;i<priceRangerTruncated.size();i++) {
////                              if (priceRangerTruncated.get(i) >= 160 && priceRangerTruncated.get(i) <= 220) {
////                                  newPrice.add(priceRangerTruncated.get(i));
////                              }
//                          break;
//                          }
//                      case "> 220":{
//                          newPrice=new ArrayList<>();
//                          db.collection(COLLECTION_NAME)
//                                  .get()
//                                  .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                      @Override
//                                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                          if (task.isSuccessful()) {
//                                              for (QueryDocumentSnapshot document : task.getResult()) {
//                                                  if((Integer.parseInt(document.getData().get("Daily Price").toString())>=220))
//                                                  {
//                                                      //  temp.add(document.getData().get(key[6]).toString());
//                                                      newPrice.add(Integer.parseInt(document.getData().get("Daily Price").toString()));
//                                                  }
//                                              }
//                                          } else {
//                                              Log.w(TAG, "Error getting documents.", task.getException());
//                                          }
//                                          // priceRangerTruncated = (removeDuplicates(priceRangerDaily));
//                                      }
//                                  });
////                          newPrice=new ArrayList<>();
////                          for(int i=0;i<priceRangerTruncated.size();i++) {
////                              if (priceRangerTruncated.get(i) > 220) {
////                                  newPrice.add(priceRangerTruncated.get(i));
////                              }
////                          }
//                          break;
//                      }
//                      case "<20":{
//                          newPriceHourly=new ArrayList<>();
//                          db.collection(COLLECTION_NAME)
//                                  .get()
//                                  .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                      @Override
//                                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                          if (task.isSuccessful()) {
//                                              for (QueryDocumentSnapshot document : task.getResult()) {
//                                                  if((Integer.parseInt(document.getData().get("Hourly Price").toString())<20 ))
//                                                  {
//                                                      //  temp.add(document.getData().get(key[6]).toString());
//                                                      newPriceHourly.add(Integer.parseInt(document.getData().get("Hourly Price").toString()));
//                                                  }
//                                              }
//                                          } else {
//                                              Log.w(TAG, "Error getting documents.", task.getException());
//                                          }
//                                          // priceRangerTruncated = (removeDuplicates(priceRangerDaily));
//                                      }
//                                  });
//                          break;
//                      }
//                      case "20 - 25":{
//                          newPriceHourly=new ArrayList<>();
//                          db.collection(COLLECTION_NAME)
//                                  .get()
//                                  .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                      @Override
//                                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                          if (task.isSuccessful()) {
//                                              for (QueryDocumentSnapshot document : task.getResult()) {
//                                                  if((Integer.parseInt(document.getData().get("Hourly Price").toString())>=20 )&&
//                                                          (Integer.parseInt(document.getData().get("Hourly Price").toString())<=25 ))
//                                                  {
//                                                      //  temp.add(document.getData().get(key[6]).toString());
//                                                      newPriceHourly.add(Integer.parseInt(document.getData().get("Hourly Price").toString()));
//                                                  }
//                                              }
//                                          } else {
//                                              Log.w(TAG, "Error getting documents.", task.getException());
//                                          }
//                                          // priceRangerTruncated = (removeDuplicates(priceRangerDaily));
//                                      }
//                                  });
//                          break;
//                      }
//                      case "> 25":{
//                          newPriceHourly=new ArrayList<>();
//                          db.collection(COLLECTION_NAME)
//                                  .get()
//                                  .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                      @Override
//                                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                          if (task.isSuccessful()) {
//                                              for (QueryDocumentSnapshot document : task.getResult()) {
//                                                  if((Integer.parseInt(document.getData().get("Hourly Price").toString())>25 ))
//                                                  {
//                                                      //  temp.add(document.getData().get(key[6]).toString());
//                                                      newPriceHourly.add(Integer.parseInt(document.getData().get("Hourly Price").toString()));
//                                                  }
//                                              }
//                                          } else {
//                                              Log.w(TAG, "Error getting documents.", task.getException());
//                                          }
//                                          // priceRangerTruncated = (removeDuplicates(priceRangerDaily));
//                                      }
//                                  });
//                          break;
//                      }
//
//                      }
//              }
//              else
//              {
//                  selectedPriceRange="All";
//                  newPrice=new ArrayList<>();
//                  spinnerPriceRange.setVisibility(View.INVISIBLE);
//              }
//              spinnerPriceRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                  @Override
//                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                      Log.d(TAG, "onItemSelected: "+ newPrice.size()+"");
//
//                  }
//
//                  @Override
//                  public void onNothingSelected(AdapterView<?> adapterView) {
//
//                  }
//              });

              if(!cbSelectAllSeats.isChecked())
              {
                  selectedSeats=spinnerSeats.getSelectedItem().toString();
              }
              else
              {
                  selectedSeats="All";
                  newSeats=numberOfSeats;
              }
              if(!cbSelectAllTypes.isChecked())
              {
                  spinnerTypes.setVisibility(View.VISIBLE);
               selectedType=spinnerTypes.getSelectedItem().toString();

              }
              else
              {
                  selectedType="All";
                  spinnerTypes.setVisibility(View.INVISIBLE);
              }
              if(!cbSelectedMake.isChecked())
              {
                  selectedMake=spinnerMake.getSelectedItem().toString();
                  spinnerTypes.setVisibility(View.VISIBLE);
              }
              else
              {
                  selectedMake="All";
                  spinnerTypes.setVisibility(View.INVISIBLE);
              }
          //    finalProduct=new ArrayList<>();

//              for(int i=0;i<test.size();i++)
//              {
//                  for(int j=0;j<newSeats.size();j++)
//                  {
//                      for(int k=0;k<newPrice.size();k++)
//                      {
//                          if(test.get(i).type.equals(selectedType)&&
//                         // test.get(i).seats.equals(newSeats.get(j).toString())&&
//                          test.get(i).make.equals(selectedMake)
//                                 // (test.get(i).pricePerHour.equals(newPrice.get(k).toString())||
//                                         // test.get(i).pricePerDay.equals(newPrice.get(k).toString()
//                                          )
//                          {
//
//                            finalProduct.add(test.get(i).type+" - "+test.get(i).model);
//                              Log.d(TAG, "onClickERRRRRRRRR: "+finalProduct.size());
//                          }
//
//                      }
//                  }
//
//              }
//
//              Intent z=new Intent(SelectionCriteriaActivity.this,RentalSelectionActivity.class);
//              z.putStringArrayListExtra("mfData",finalProduct);
//              startActivity(z);
              final ArrayList<String> finalProduct1=new ArrayList<>();
              db.collection(COLLECTION_NAME)
                      .get()
                      .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                          @Override
                          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                              // numberOfSeats = new ArrayList<>();

                              //    priceRangerTruncated = new ArrayList<>();
                              if (task.isSuccessful()) {
                                  for (QueryDocumentSnapshot document : task.getResult()) {
                                      //  temp.add(document.getData().get(key[6]).toString());
                                      if(!(cbSelectedMake.isChecked())&&
                                              !(cbSelectAllSeats.isChecked())&&
                                              !(cbSelectAllTypes.isChecked())&&
                                              document.getData().get("Make").toString().equals(selectedMake)&&
                                              document.getData().get("Type").toString().equals(selectedType)&&
                                                      document.getData().get("Seats").toString().equals(selectedSeats)) {
                                          finalProduct1.add(document.getData().get("ID").toString()+" - "+document.getData().get("Model").toString());
                                          Log.d(TAG, "onClick:Reeeeeeeeeeee " + finalProduct1.size());
                                      }
                                      else if(!(cbSelectedMake.isChecked())&&
                                              !(cbSelectAllSeats.isChecked())&&
                                              (cbSelectAllTypes.isChecked())&&
                                              document.getData().get("Make").toString().equals(selectedMake)&&
                                              document.getData().get("Seats").toString().equals(selectedSeats)){
                                          finalProduct1.add(document.getData().get("ID").toString()+" - "+document.getData().get("Model").toString());
                                          Log.d(TAG, "onClick:Reeeeeeeeeeee " + finalProduct1.size());
                                      }
                                      else if(!(cbSelectedMake.isChecked())&&
                                              (cbSelectAllSeats.isChecked())&&
                                              !(cbSelectAllTypes.isChecked())&&
                                              document.getData().get("Make").toString().equals(selectedMake)&&
                                              document.getData().get("Type").toString().equals(selectedType)){
                                          finalProduct1.add(document.getData().get("ID").toString()+" - "+document.getData().get("Model").toString());
                                          Log.d(TAG, "onClick:Reeeeeeeeeeee " + finalProduct1.size());
                                      }
                                      else if( (cbSelectedMake.isChecked())&&
                                              !(cbSelectAllSeats.isChecked())&&
                                              !(cbSelectAllTypes.isChecked())&&
                                              document.getData().get("Type").toString().equals(selectedType)&&
                                              document.getData().get("Seats").toString().equals(selectedSeats)) {
                                          finalProduct1.add(document.getData().get("ID").toString()+" - "+document.getData().get("Model").toString());
                                          Log.d(TAG, "onClick:Reeeeeeeeeeee " + finalProduct1.size());
                                      }
                                      else if((cbSelectedMake.isChecked())&&
                                              (cbSelectAllSeats.isChecked())&&
                                              !(cbSelectAllTypes.isChecked())&&
                                              document.getData().get("Type").toString().equals(selectedType)) {
                                          finalProduct1.add(document.getData().get("ID").toString()+" - "+document.getData().get("Model").toString());
                                          Log.d(TAG, "onClick:Reeeeeeeeeeee " + finalProduct1.size());
                                      }
                                      else  if(!(cbSelectedMake.isChecked())&&
                                              (cbSelectAllSeats.isChecked())&&
                                              (cbSelectAllTypes.isChecked())&&
                                              document.getData().get("Make").toString().equals(selectedMake)) {
                                          finalProduct1.add(document.getData().get("ID").toString()+" - "+document.getData().get("Model").toString());
                                          Log.d(TAG, "onClick:Reeeeeeeeeeee " + finalProduct1.size());
                                      }
                                      else  if((cbSelectedMake.isChecked())&&
                                              !(cbSelectAllSeats.isChecked())&&
                                              (cbSelectAllTypes.isChecked())&&
                                              document.getData().get("Seats").toString().equals(selectedSeats)) {
                                          finalProduct1.add(document.getData().get("ID").toString()+" - "+document.getData().get("Model").toString());
                                          Log.d(TAG, "onClick:Reeeeeeeeeeee " + finalProduct1.size());
                                      }
                                      else  if((cbSelectedMake.isChecked())&&
                                              (cbSelectAllSeats.isChecked())&&
                                              (cbSelectAllTypes.isChecked())) {
                                          finalProduct1.add(document.getData().get("ID").toString()+" - "+document.getData().get("Model").toString());
                                          Log.d(TAG, "onClick:Reeeeeeeeeeee " + finalProduct1.size());
                                      }

                                  }
                              } else {
                                  Log.w(TAG, "Error getting documents.", task.getException());
                              }
                              Intent z=new Intent(SelectionCriteriaActivity.this,RentalSelectionActivity.class);
                              z.putStringArrayListExtra("mfData",finalProduct1);
                              startActivity(z);
                          }
                      });



//              MasterFragment m = (MasterFragment) getSupportFragmentManager().findFragmentById(R.id.theNames);
//              //Log.d("onCreate","MainActivity");
//              m.setTheData(finalProduct);

          }

      });
      btnShowAll.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
//              for(int i=0;i<test.size();i++)
//              {
//                  finalProduct.add(test.get(i).type);
//              }

              db.collection(COLLECTION_NAME)
                      .get()
                      .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                          @Override
                          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                             // numberOfSeats = new ArrayList<>();
                              finalProduct=new ArrayList<>();
                              //    priceRangerTruncated = new ArrayList<>();
                              if (task.isSuccessful()) {
                                  for (QueryDocumentSnapshot document : task.getResult()) {
                                      //  temp.add(document.getData().get(key[6]).toString());
                                      finalProduct.add(document.getData().get("ID").toString());

                                  }
                              } else {
                                  Log.w(TAG, "Error getting documents.", task.getException());
                              }
                              Log.d(TAG, "onClick:Reeeeeeeeeeee "+finalProduct.size());

                              Intent z=new Intent(SelectionCriteriaActivity.this,RentalSelectionActivity.class);
                              z.putStringArrayListExtra("mfData",finalProduct);
                              startActivity(z);
                          }
                      });

          }
      });
    }

    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();
        // Traverse through the first list
        for (T element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }
}
