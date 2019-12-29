package com.example.vancouvertourismappfinal.CarRentalClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RentalSelectionActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private final String TAG = "ReadAllData";
    ArrayList<String> data=new ArrayList<>();
    ArrayList<String> typesTruncated=new ArrayList<>();
    String COLLECTION_NAME="VehicleInventory";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_selection);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.getStringArrayList("mfData");

            Log.d(TAG, "onCreate: " + data.size());
        }
        initialize();
       // readAllData();

        CarrentalMasterFragment m = (CarrentalMasterFragment) getSupportFragmentManager().findFragmentById(R.id.theNames);
        //Log.d("onCreate","MainActivity");
        m.setTheData(removeDuplicates(data));
     //   removeDuplicates(types);

    }
    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }


    private void readAllData() {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                            TextView tv = (TextView) findViewById(R.id.resultTxt);
//                            tv.setText("");
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                tv.append(document.getId() + " => " + document.getData() +"\n" );
//                                tv.append("\t\t" + key[0] + " is " + document.getData().get(key[0]) +"\n" );
                              //  .add(document.getData().get("Type").toString());
                                Log.d(TAG, "onComplete: "+document.getData().get("Type").toString());


                             //   Log.d(TAG, types.size()+"");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                   //     typesTruncated=removeDuplicates(types);
//                        Intent x=new Intent(RentalSelectionActivity.this,AddNewInventoryActivity.class);
//                        x.putStringArrayListExtra("data",typesTruncated);



                    }
                });
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
