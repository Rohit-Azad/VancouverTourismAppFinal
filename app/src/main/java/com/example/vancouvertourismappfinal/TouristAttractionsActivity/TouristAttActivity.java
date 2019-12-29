package com.example.vancouvertourismappfinal.TouristAttractionsActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.CarRentalClasses.ClientActivity;
import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelsDB;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class TouristAttActivity extends AppCompatActivity {
    private DBConnectivity_Tourist receiver;

    Button btnNewWest;
    Button btnBurnaby;
    Button btnNorthVan;
    Button btndowntown;
    Button btnRichmond;
    Button btnSurrey;
    Button btnShowAll;

    String[] pics;

    private String TAG="REEEEEEEEEEEEEE";
    private FirebaseFirestore db;
    String COLLECTION_NAME2="TouristAttractions";
    ArrayList<TouristDB> touristDBS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_att);

        initialize();
        btnNewWest= findViewById(R.id.btnNewWest);
        btnBurnaby= findViewById(R.id.btnBurnaby);
        btnNorthVan= findViewById(R.id.btnNorthVan);
        btndowntown= findViewById(R.id.btnDowntown);
        btnRichmond= findViewById(R.id.btnRichmond);
        btnSurrey= findViewById(R.id.btnSurrey);
        btnShowAll= findViewById(R.id.btnShowAll);
        receiver = new DBConnectivity_Tourist(this);
        Button add=findViewById(R.id.btnAdd);
        touristDBS=processData();

        btnNewWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TouristAttActivity.this, DownloadService_Tourist.class);
                i.putExtra("Location","New Westminster");
                i.putExtra("loginOrRegister","tourist");
                startService(i);

            }
        });
        btnSurrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TouristAttActivity.this,DownloadService_Tourist.class);
                i.putExtra("Location","Surrey");
                i.putExtra("loginOrRegister","tourist");
                startService(i);

            }
        });
        btnRichmond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TouristAttActivity.this,DownloadService_Tourist.class);
                i.putExtra("Location","Richmond");
                i.putExtra("loginOrRegister","tourist");
                startService(i);

            }
        });
        btndowntown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TouristAttActivity.this,DownloadService_Tourist.class);
                i.putExtra("Location","Downtown Vancouver");
                i.putExtra("loginOrRegister","tourist");
                startService(i);

            }
        });
        btnNorthVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TouristAttActivity.this,DownloadService_Tourist.class);
                i.putExtra("Location","North Vancouver");
                i.putExtra("loginOrRegister","tourist");
                startService(i);

            }
        });
        btnBurnaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TouristAttActivity.this,DownloadService_Tourist.class);
                i.putExtra("Location","Burnaby");
                i.putExtra("loginOrRegister","tourist");
                startService(i);

            }
        });
        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TouristAttActivity.this,DownloadService_Tourist.class);
                i.putExtra("Location","All");
                i.putExtra("loginOrRegister","tourist_all");
                startService(i);

            }
        });

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

                            Toast.makeText(TouristAttActivity.this, "Current data:" + ldoc.size(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                Map<String, Object> data;
                Map<String,String> data2;

                for(int i=0;i<touristDBS.size();i++) {
                    data = new HashMap<>();
                    data2=new HashMap<>(0);
                    Log.d("onClick: ",touristDBS.get(i).name);
                    data.put("ID",touristDBS.get(i).id);
                    data.put("Name", touristDBS.get(i).name);
                    data.put("Description", touristDBS.get(i).desc);
                    data.put("Pictures", Arrays.asList(touristDBS.get(i).pics));

                    carRentalDB.document(touristDBS.get(i).name).set(data);
                }
            }
        });
    }
    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(DBConnectivity_Tourist.STATUS_DONE);
          /*
        Intent registerReceiver (BroadcastReceiver receiver, IntentFilter filter)
        Register a BroadcastReceiver to be run in the main activity thread.
        The receiver will be called with any broadcast Intent that matches filter,
        in the main application thread.
         */

        registerReceiver(receiver,filter);
    }

    private ArrayList<TouristDB> processData() {
        String json = null;
        try {
            InputStream is = getAssets().open("TouristAttractions.json");


            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        ArrayList<TouristDB> temp = new ArrayList<>();
        try {

            JSONArray ar = new JSONArray(json);
            JSONObject element;
            TouristDB touristDB;
            //String[] pics=new String[4];

            for (int i=0 ; i < ar.length(); i++) {
                element = ar.getJSONObject(i);
                touristDB = new TouristDB();
                pics=new String[4];
                touristDB.id = Integer.parseInt(element.getString("ID"));
                touristDB.name = element.getString("Name");
                touristDB.desc = element.getString("Desc");
                for(int j=0;j<4;j++) {
                    pics[j] = element.getJSONArray("Pictures").getString(j);
                }
              touristDB.pics=pics;
                temp.add(touristDB);
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
