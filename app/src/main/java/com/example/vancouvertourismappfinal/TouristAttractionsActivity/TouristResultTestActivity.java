package com.example.vancouvertourismappfinal.TouristAttractionsActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.HotelReviews.HotelReviewsActivity;
import com.example.vancouvertourismappfinal.MyCustomAdapter;
import com.example.vancouvertourismappfinal.R;
import com.example.vancouvertourismappfinal.RecyclerTouchListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TouristResultTestActivity extends AppCompatActivity {
    ArrayList<TouristSpotsDB> touristSpotsDB;
    ArrayList<String> names=new ArrayList<>();
    ListView lvList;

    List<String> allPicList =new ArrayList<>();
    List<String> allDesc =new ArrayList<>();
    List<String[]> imgList=new ArrayList<>();

    private FirebaseFirestore db;
    private final String TAG = "ReadAllData";
    String COLLECTION_NAME="TouristAttractions";
    String[] test2;


    private void initialize() {

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_result_test);

        final RecyclerView myRecyclerView=(RecyclerView) findViewById(R.id.recyclerViewTabs);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);

        Bundle extra = getIntent().getBundleExtra("listSpots");
        touristSpotsDB = (ArrayList<TouristSpotsDB>) extra.getSerializable("list");

//        for(int i=0;i<touristSpotsDB.size();i++) {
//            names.add(touristSpotsDB.get(i).name);
//            allDesc.add(touristSpotsDB.get(i).location);
//        }

        initialize();

        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                for(int i=0;i<touristSpotsDB.size();i++)
                                {
                                    if(document.getData().get("Name").toString().equals(touristSpotsDB.get(i).name)) {
                                        Log.d(TAG, "onComplete: "+"="+touristSpotsDB.get(i).name+"="+document.getData().get("Name").toString()+"=");

                                        String test=document.getData().get("Pictures").toString();
                                        names.add(touristSpotsDB.get(i).name);
                                        allDesc.add(touristSpotsDB.get(i).location);
                                        test2=test.substring(1,test.length()-1).split(", ");
                                        Log.d(TAG, "onComplete: "+test2[0]+"===="+test2[1]);
                                        imgList.add(test2);


//                                        assert test3 != null;
//                                        Log.d(TAG, "onComplete:test3 "+test3[0]);
                                        allPicList.add(test2[0]);
                                        Log.d(TAG, "onComplete: "+test2[0]);
                                    }


                                }
                                final MyCustomAdapter_2 myAdapter=new MyCustomAdapter_2(names, allPicList, allDesc);
                                myRecyclerView.setAdapter(myAdapter);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });

        myRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                switch (position)
                {
                    case 0:

                    i.putExtra("nameList",names.get(0));
                    i.putExtra("picList",imgList.get(0));
                    startActivity(i);
                    break;
                    case 1:
                   // Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                    i.putExtra("nameList",names.get(1));
                    i.putExtra("picList",imgList.get(1));
                    startActivity(i);
                    break;
                    case 2:
                   //     Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(2));
                        i.putExtra("picList",imgList.get(2));
                        startActivity(i);
                        break;
                    case 3:
                  //      Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(3));
                        i.putExtra("picList",imgList.get(3));
                        startActivity(i);
                        break;
                    case 4:
                    //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(4));
                        i.putExtra("picList",imgList.get(4));
                        startActivity(i);
                        break;
                    case 5:
                     //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(5));
                        i.putExtra("picList",imgList.get(5));
                        startActivity(i);
                        break;
                    case 6:
                     //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(6));
                        i.putExtra("picList",imgList.get(6));
                        startActivity(i);
                        break;
                    case 7:
                     //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(7));
                        i.putExtra("picList",imgList.get(7));
                        startActivity(i);
                        break;
                    case 8:
                     //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(8));
                        i.putExtra("picList",imgList.get(8));
                        startActivity(i);
                        break;
                    case 9:
                     //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(9));
                        i.putExtra("picList",imgList.get(9));
                        startActivity(i);
                        break;
                    case 10:
                    //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(10));
                        i.putExtra("picList",imgList.get(10));
                        startActivity(i);
                        break;
                    case 11:
                    //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(11));
                        i.putExtra("picList",imgList.get(11));
                        startActivity(i);
                        break;
                    case 12:
                    //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(12));
                        i.putExtra("picList",imgList.get(12));
                        startActivity(i);
                        break;
                    case 13:
                      //  Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(13));
                        i.putExtra("picList",imgList.get(13));
                        startActivity(i);
                        break;
                    case 14:
                  //      Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(14));
                        i.putExtra("picList",imgList.get(14));
                        startActivity(i);
                        break;

                    case 15:
                      //  Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(15));
                        i.putExtra("picList",imgList.get(15));
                        startActivity(i);
                        break;
                    case 16:
                    //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(16));
                        i.putExtra("picList",imgList.get(16));
                        startActivity(i);
                        break;
                    case 17:
                     //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(17));
                        i.putExtra("picList",imgList.get(17));
                        startActivity(i);
                        break;
                    case 18:
                     //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(18));
                        i.putExtra("picList",imgList.get(18));
                        startActivity(i);
                        break;
                    case 19:
                     //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList",names.get(19));
                        i.putExtra("picList",imgList.get(19));
                        startActivity(i);
                        break;


                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


//        final MyCustomAdapter_2 myAdapter=new MyCustomAdapter_2(names, allPicList, allDesc);
//        myRecyclerView.setAdapter(myAdapter);



//        ArrayAdapter<String> adapterNames;
//
//        adapterNames = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
//        lvList.setAdapter(adapterNames);
    }
}
