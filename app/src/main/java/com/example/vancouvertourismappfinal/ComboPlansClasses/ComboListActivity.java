package com.example.vancouvertourismappfinal.ComboPlansClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.vancouvertourismappfinal.MyCustomAdapter;
import com.example.vancouvertourismappfinal.R;
import com.example.vancouvertourismappfinal.RecyclerTouchListener;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristResultTestActivity;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristSpotDetailActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ComboListActivity extends AppCompatActivity {

    ArrayList<String> data=new ArrayList<>();
    ArrayList<String> data2=new ArrayList<>();
    ArrayList<String> data3=new ArrayList<>();

    ArrayList<String> dataCar;
    ArrayList<String> data2Car;
    ArrayList<String> data3Car;
    ArrayList<Integer> data4Car;

    String COLLECTION_NAME1="VehicleInventory";
    String COLLECTION_NAME3="ComboBookings";
    private FirebaseFirestore db;

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
        setContentView(R.layout.activity_combo_list);

        initialize();
        final RecyclerView myRecyclerView=(RecyclerView) findViewById(R.id.recyclerViewTabs);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);
        db.collection(COLLECTION_NAME1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // numberOfSeats = new ArrayList<>();
                        dataCar=new ArrayList<>();
                        data2Car=new ArrayList<>();
                        data3Car=new ArrayList<>();
                        data4Car=new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    dataCar.add(document.getData().get("Make").toString()+"-"+document.getData().get("Model").toString());
                                    data3Car.add(document.getData().get("Type").toString());
                                    data2Car.add(document.getData().get("picture").toString());
                                    data4Car.add(Integer.parseInt(document.getData().get("Daily Price").toString()));
                            }
                        } else {
                        }
                        Bundle extras = getIntent().getExtras();
                        if (extras != null) {
                            data = extras.getStringArrayList("mfData");
                            data2 = extras.getStringArrayList("mfData2");
                            data3 = extras.getStringArrayList("mfData3");

                            Log.d("Tag", "onCreate: " + data.size());
                        }
                        final MyCustomAdapter_combo myAdapter=new MyCustomAdapter_combo(data, data3, data2,dataCar,data2Car,data3Car);

                        //set adapter to recycler view
                        myRecyclerView.setAdapter(myAdapter);

                    }
                });


        String[] carArray={"Audi S4","Dodge Charger","Mercedes-Benz E-Class","GMC Yukon XL 2500","Mahindra Scorpio"};
        String[] typeArray={"Sedan","Sedan","Luxury","Truck","SUV"};
        String[] picArray={"https://i.ytimg.com/vi/cHwqPd6G8ik/maxresdefault.jpg",
                "https://www.drivefivestar.com/static/dealer-12376/828046.jpg",
                "https://www.mercedes-benz.com/en/mercedes-benz/vehicles/passenger-cars/e-class/e-class-the-exterior-design/_jcr_content/image/MQ6-8-image-20190114123213/02-mercedes-benz-e-class-2018-exterior-design-1280x720-1280x720.jpeg",
                 "http://momentcar.com/images/gmc-yukon-xl-slt-2500-1.jpg",
        "https://img.etimg.com/thumb/msid-66601271,width-640,resizemode-4,imgsize-73744/mahindra-scorpio-new-variant-launched.jpg"};
        Integer[] priceArray={262,182,212,211,238};




        //create adapter instance


        myRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent i = new Intent(ComboListActivity.this, ComboSelectionsActivity.class);
                switch (position) {
                    case 0:

                        i.putExtra("nameList", data.get(0));
                        i.putExtra("carNameList",dataCar.get(0));
                       // i.putExtra("picList", imgList.get(0));
                        startActivity(i);
                        break;
                    case 1:
                        // Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(1));
                        i.putExtra("carNameList",dataCar.get(1));
                     //   i.putExtra("picList", imgList.get(1));
                        startActivity(i);
                        break;
                    case 2:
                        //     Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(2));
                        i.putExtra("carNameList",dataCar.get(2));
                     //   i.putExtra("picList", imgList.get(2));
                        startActivity(i);
                        break;
                    case 3:
                        //      Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(3));
                        i.putExtra("carNameList",dataCar.get(3));
                      //  i.putExtra("picList", imgList.get(3));
                        startActivity(i);
                        break;
                    case 4:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(4));
                        i.putExtra("carNameList",dataCar.get(4));
                     //   i.putExtra("picList", imgList.get(4));
                        startActivity(i);
                        break;
                    case 5:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(5));
                        i.putExtra("carNameList",dataCar.get(5));
                     //   i.putExtra("picList", imgList.get(5));
                        startActivity(i);
                        break;
                    case 6:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(6));
                        i.putExtra("carNameList",dataCar.get(6));
                    //    i.putExtra("picList", imgList.get(6));
                        startActivity(i);
                        break;
                    case 7:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(7));
                        i.putExtra("carNameList",dataCar.get(7));
                    //    i.putExtra("picList", imgList.get(7));
                        startActivity(i);
                        break;
                    case 8:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(8));
                        i.putExtra("carNameList",dataCar.get(8));
                    //    i.putExtra("picList", imgList.get(8));
                        startActivity(i);
                        break;
                    case 9:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(9));
                        i.putExtra("carNameList",dataCar.get(9));
                    //    i.putExtra("picList", imgList.get(9));
                        startActivity(i);
                        break;
                    case 10:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(10));
                        i.putExtra("carNameList",dataCar.get(10));
                    //    i.putExtra("picList", imgList.get(10));
                        startActivity(i);
                        break;
                    case 11:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(11));
                        i.putExtra("carNameList",dataCar.get(11));
                    //    i.putExtra("picList", imgList.get(11));
                        startActivity(i);
                        break;
                    case 12:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(12));
                        i.putExtra("carNameList",dataCar.get(2));
                    //    i.putExtra("picList", imgList.get(12));
                        startActivity(i);
                        break;
                    case 13:
                        //  Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(13));
                        i.putExtra("carNameList",dataCar.get(13));
                     //   i.putExtra("picList", imgList.get(13));
                        startActivity(i);
                        break;
                    case 14:
                        //      Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(14));
                        i.putExtra("carNameList",dataCar.get(14));
                     //   i.putExtra("picList", imgList.get(14));
                        startActivity(i);
                        break;

                    case 15:
                        //  Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(15));
                        i.putExtra("carNameList",dataCar.get(15));
                    //    i.putExtra("picList", imgList.get(15));
                        startActivity(i);
                        break;
                    case 16:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(16));
                        i.putExtra("carNameList",dataCar.get(16));
                    //    i.putExtra("picList", imgList.get(16));
                        startActivity(i);
                        break;
                    case 17:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", data.get(17));
                        i.putExtra("carNameList",dataCar.get(17));
                    //    i.putExtra("picList", imgList.get(17));
                        startActivity(i);
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
}
