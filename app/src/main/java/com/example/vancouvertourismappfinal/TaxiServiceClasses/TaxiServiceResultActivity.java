package com.example.vancouvertourismappfinal.TaxiServiceClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vancouvertourismappfinal.MyCustomAdapter;
import com.example.vancouvertourismappfinal.R;
import com.example.vancouvertourismappfinal.RecyclerTouchListener;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristResultTestActivity;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristSpotDetailActivity;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristSpotsDB;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TaxiServiceResultActivity extends AppCompatActivity {
    List<String> allItemsList = new ArrayList<>();
    List<String> allPicList = new ArrayList<>();
    List<TaxiDB> taxiDBList;
    NumberFormat nf = NumberFormat.getCurrencyInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_service_result);

        Bundle extra = getIntent().getBundleExtra("listSpots");
        taxiDBList = (ArrayList<TaxiDB>) extra.getSerializable("list");

        for (int i = 0; i < taxiDBList.size(); i++) {
            allItemsList.add(taxiDBList.get(i).type + " - " + taxiDBList.get(i).name);
            allPicList.add(String.valueOf((nf.format(taxiDBList.get(i).price))));
        }

        final RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewTabs);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);

        final MyCustomAdapter_1 myAdapter = new MyCustomAdapter_1(allItemsList, allPicList);

        //set adapter to recycler view
        myRecyclerView.setAdapter(myAdapter);

        myRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(TaxiServiceResultActivity.this, TaxiConfirmedActivity.class);
                switch (position) {
                    case 0:

                        i.putExtra("nameList", allItemsList.get(0));
                        i.putExtra("picList", allPicList.get(0).substring(1));
                        startActivity(i);
                        break;
                    case 1:
                        // Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(1));
                        i.putExtra("picList", allPicList.get(1).substring(1));
                        startActivity(i);
                        break;
                    case 2:
                        //     Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(2));
                        i.putExtra("picList", allPicList.get(2).substring(1));
                        startActivity(i);
                        break;
                    case 3:
                        //      Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(3));
                        i.putExtra("picList", allPicList.get(3).substring(1));
                        startActivity(i);
                        break;
                    case 4:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(4));
                        i.putExtra("picList", allPicList.get(4).substring(1));
                        startActivity(i);
                        break;
                    case 5:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(5));
                        i.putExtra("picList", allPicList.get(5).substring(1));
                        startActivity(i);
                        break;
                    case 6:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(6));
                        i.putExtra("picList", allPicList.get(6).substring(1));
                        startActivity(i);
                        break;
                    case 7:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(7));
                        i.putExtra("picList", allPicList.get(7).substring(1));
                        startActivity(i);
                        break;
                    case 8:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(8));
                        i.putExtra("picList", allPicList.get(8).substring(1));
                        startActivity(i);
                        break;
                    case 9:
                        //   Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(9));
                        i.putExtra("picList", allPicList.get(9).substring(1));
                        startActivity(i);
                        break;
                    case 10:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(10));
                        i.putExtra("picList", allPicList.get(10).substring(1));
                        startActivity(i);
                        break;
                    case 11:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(11));
                        i.putExtra("picList", allPicList.get(11).substring(1));
                        startActivity(i);
                        break;
                    case 12:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(12));
                        i.putExtra("picList", allPicList.get(12).substring(1));
                        startActivity(i);
                        break;
                    case 13:
                        //  Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(13));
                        i.putExtra("picList", allPicList.get(13).substring(1));
                        startActivity(i);
                        break;
                    case 14:
                        //      Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(14));
                        i.putExtra("picList", allPicList.get(14).substring(1));
                        startActivity(i);
                        break;

                    case 15:
                        //  Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(15));
                        i.putExtra("picList", allPicList.get(15).substring(1));
                        startActivity(i);
                        break;
                    case 16:
                        //    Intent i = new Intent(TouristResultTestActivity.this, TouristSpotDetailActivity.class);
                        i.putExtra("nameList", allItemsList.get(16));
                        i.putExtra("picList", allPicList.get(16).substring(1));
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
