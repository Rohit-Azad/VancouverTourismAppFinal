package com.example.vancouvertourismappfinal.CarRentalClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ClientInfoActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";

    private FirebaseFirestore db;
    private final String TAG = "ReadAllData";
    private String COLLECTION_NAME="CarCustomerInfo";
    private String UID;
    Button btnSubmit;
    Button btnSearch;
    Button fetch;

    EditText fName;
    EditText lName;
    EditText suite;
    EditText street;
    EditText city;
    EditText zipCode;
    EditText eMail;
    EditText licenseNumber;
    EditText phoneNumber;
    EditText stateProvince;
    EditText search;
    EditText found;


    String name;
    String suiteNum,streetName,cityName,zipcode,state;
    String phone;
    String email;
    String licenseNum;
    String randomRegNum;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        initialize();

        fetch=(Button)findViewById(R.id.btnFetch);
        fetch.setEnabled(false);
        search=(EditText)findViewById(R.id.etSearchName) ;
        found=(EditText)findViewById(R.id.etFound);
        btnSearch=(Button)findViewById(R.id.btnSearchDB);
        fName=(EditText)findViewById(R.id.etFName);
        lName=(EditText)findViewById(R.id.etLName);
        licenseNumber=(EditText)findViewById(R.id.etLicense);
        street=(EditText)findViewById(R.id.etStreet);
        city=(EditText)findViewById(R.id.etCity);
        suite=(EditText)findViewById(R.id.etSuite);
        eMail=(EditText)findViewById(R.id.etEmail);
        zipCode=(EditText)findViewById(R.id.etZipCode);
        phoneNumber=(EditText)findViewById(R.id.etPhone);
        phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        stateProvince=(EditText)findViewById(R.id.etState);

        btnSubmit=(Button)findViewById(R.id.btnSubmitDetails);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                licenseNum=licenseNumber.getText().toString();
                name=fName.getText().toString()+" "+lName.getText().toString();
                suiteNum=suite.getText().toString();
                streetName=street.getText().toString();
                cityName=city.getText().toString();
                zipcode=zipCode.getText().toString();
                phone=phoneNumber.getText().toString();
                state=stateProvince.getText().toString();

                email=eMail.getText().toString();


                if(licenseNum.isEmpty()||phone.isEmpty()||email.isEmpty()||
                suiteNum.isEmpty()||streetName.isEmpty()||cityName.isEmpty()||zipcode.isEmpty()||name.isEmpty()||state.isEmpty())
                {
                    Toast.makeText(ClientInfoActivity.this,"Please fill in all the details",Toast.LENGTH_SHORT).show();
                }
                else {

                    randomRegNum=("A"+fName.getText().toString().substring(0,2)+phone.substring(phone.length()-3)+licenseNum.substring(0,2)).toUpperCase();
                    //+(int)(Math.random() * 50 + 1)
                    Log.d("onClick: ",randomRegNum);
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("fullName", name);
                    editor.putString("suiteNum", suiteNum);
                    editor.putString("streetName", streetName);
                    editor.putString("cityName", cityName);
                    editor.putString("zipcode", zipcode);
                    editor.putString("phone", phone);
                    editor.putString("email", email);
                    editor.putString("randomRegNum", randomRegNum);
                    editor.putString("licenseNum", licenseNum);
                    editor.putString("state",state);
                    editor.apply();

                    Intent i = new Intent(ClientInfoActivity.this, SelectionCriteriaActivity.class);
                    startActivity(i);
                }
                }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchDatabase();
                fetch.setEnabled(true);
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchByName();
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
    private void SearchDatabase()
    {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("Name").toString().contains(search.getText().toString()))
                                {
                                    found.setText("Found!! "+ document.getData().get("Name"));
                                    Log.d(TAG, "onComplete: "+document.getData().get("Name").toString());

                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    private void SearchByName() {

        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("Name").toString().contains(search.getText().toString()))
                                {
                                    String[] test=document.getData().get("Name").toString().split(" ");
                                    fName.setText(test[0]);
                                    for(int i=1;i<test.length;i++) {
                                        lName.append(test[i]+" ");
                                    }
                                 //   uniqueID.setText(document.getData().get("UID")+"");
                                    phoneNumber.setText(document.getData().get("Phone").toString());
                                    eMail.setText(document.getData().get("EMail").toString());
                                    licenseNumber.setText(document.getData().get("License")+"");
                                    UID=document.getId();
                                    CollectionReference ref2=db.collection(COLLECTION_NAME)
                                            .document(UID)
                                            .collection("Address");
                                    Query query2 = ref2.whereEqualTo("UID", UID);
                                    query2.get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {


                                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                                            // getting the last document

                                                            suite.setText(document.getData().get("Suite")+"");
                                                            street.setText(document.getData().get("Street")+"");
                                                            city.setText(document.getData().get("City")+"");
                                                            stateProvince.setText(document.getData().get("State").toString());
                                                            zipCode.setText(document.getData().get("Zip Code")+"");
                                                        }

                                                    } else {
                                                        Log.w(TAG, "Error getting documents.", task.getException());
                                                    }
                                                }
                                            });
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }
}
