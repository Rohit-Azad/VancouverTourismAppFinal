package com.example.vancouvertourismappfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.CarRentalClasses.CarRentalReviewActivity;
import com.example.vancouvertourismappfinal.CarRentalClasses.ClientActivity;
import com.example.vancouvertourismappfinal.ComboPlansClasses.ComboMainActivity;
import com.example.vancouvertourismappfinal.CurrencyConverterClasses.CurrencySplashActivity;
import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelBookingMainActivity;
import com.example.vancouvertourismappfinal.HotelBookingClasses.HotelsDB;
import com.example.vancouvertourismappfinal.HotelReviews.HotelReviewsActivity;
import com.example.vancouvertourismappfinal.RestRoomFinderClasses.views.MainActivity;
import com.example.vancouvertourismappfinal.TaxiServiceClasses.TaxiServiceActivity;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristAttActivity;
import com.example.vancouvertourismappfinal.TranslatorClasses.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_1 extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    List<String> allItemsList =new ArrayList<>();
    List<Integer> allPicList =new ArrayList<>();
    List<String> allDesc =new ArrayList<>();

    List<String> hotelBook =new ArrayList<>();
    List<Integer> hotelPagePicList =new ArrayList<>();
    List<String> hotelDesc =new ArrayList<>();

    List<String> carRentalList =new ArrayList<>();
    List<Integer> carRentalPicList =new ArrayList<>();
    List<String> carRentalDesc =new ArrayList<>();

    List<String> miscServices =new ArrayList<>();
    List<Integer> miscPicList =new ArrayList<>();
    List<String> miscDesc =new ArrayList<>();

    ArrayList<HotelsDB> hotelsList;

    private void addAllItems()
    {
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        allItemsList.add(prefs.getString("FirstName_s", "Rohit")+" "+prefs.getString("LastName_s", "Azad"));
        allItemsList.add("Book Hotel");
        allItemsList.add("Tourist Places");
        allItemsList.add("Reviews");
        allItemsList.add("Car Rental");
        allItemsList.add("Review Rental");
        allItemsList.add("Combo Plans");
        allItemsList.add("Language Translator");
        allItemsList.add("Currency Converter");
        allItemsList.add("Restroom Finder");
        allItemsList.add("Taxi Booking");
        allItemsList.add("Emergency Services");

        if(prefs.getString("Role_s", "No fullName defined").equals("Administrator")) {
            allPicList.add(R.drawable.admin);
        }
        else
        {
            allPicList.add(R.drawable.client);
        }
        allPicList.add(R.drawable.hotelbooking);
        allPicList.add(R.drawable.touristspots);
        allPicList.add(R.drawable.custreview);
        allPicList.add(R.drawable.carrental);
        allPicList.add(R.drawable.rentalreview);
        allPicList.add(R.drawable.combo);
        allPicList.add(R.drawable.langtranslate);
        allPicList.add(R.drawable.currconvert);
        allPicList.add(R.drawable.restroomfinder);
        allPicList.add(R.drawable.taxi);
        allPicList.add(R.drawable.emergency);

        allDesc.add(prefs.getString("Role_s", "Client"));
        allDesc.add("Book a hotel in your preferred area");
        allDesc.add("Check out famous tourist spots");
        allDesc.add("Add a review or read other reviews");
        allDesc.add("Rent a Car you want");
        allDesc.add("Review your Rental");
        allDesc.add("choose a combo plan");
        allDesc.add("translate over 50 different languages");
        allDesc.add("Convert currency with up to date currency value");
        allDesc.add("Find restrooms near you");
        allDesc.add("Book a Taxi");
        allDesc.add("Information in case of an emergency");




        hotelBook.add("Book Hotel");
        hotelBook.add("Tourist Places");
        hotelBook.add("Reviews");

        hotelPagePicList.add(R.drawable.hotelbooking);
        hotelPagePicList.add(R.drawable.touristspots);
        hotelPagePicList.add(R.drawable.custreview);

        hotelDesc.add("Book a hotel in your preferred area");
        hotelDesc.add("Check out famous tourist spots");
        hotelDesc.add("Add a review or read other reviews");

        carRentalList.add("Car Rental");
        carRentalList.add("Review Rental");
        carRentalPicList.add(R.drawable.carrental);
        carRentalPicList.add(R.drawable.rentalreview);
        carRentalDesc.add("Rent a Car you want");
        carRentalDesc.add("Review your Rental");

        miscServices.add("Combo Plans");
        miscServices.add("Language Translator");
        miscServices.add("Currency Converter");
        miscServices.add("Restroom Finder");
        miscServices.add("Taxi Booking");
        miscServices.add("Emergency Services");
        miscPicList.add(R.drawable.combo);
        miscPicList.add(R.drawable.langtranslate);
        miscPicList.add(R.drawable.currconvert);
        miscPicList.add(R.drawable.restroomfinder);
        miscPicList.add(R.drawable.taxi);
        miscPicList.add(R.drawable.emergency);
        miscDesc.add("choose a combo plan");
        miscDesc.add("translate over 50 different languages");
        miscDesc.add("Convert currency with up to date currency value");
        miscDesc.add("Find restrooms near you");
        miscDesc.add("Book a Taxi");
        miscDesc.add("Information in case of an emergency");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);

        hotelsList=processData();
        Log.d("Reeeeeeeeeee", "onCreate: "+hotelsList.get(4).hotelName);
        final Bundle extra = new Bundle();
        extra.putSerializable("hotelDBList", hotelsList);


        final TabLayout myTableLayout=(TabLayout) findViewById(R.id.tabLayout);

        final RecyclerView myRecyclerView=(RecyclerView) findViewById(R.id.recyclerViewTabs);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);

        //create data
        addAllItems();

        //create adapter instance
        final MyCustomAdapter myAdapter=new MyCustomAdapter(allItemsList, allPicList, allDesc);

        //set adapter to recycler view
        myRecyclerView.setAdapter(myAdapter);
        myRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(myTableLayout.getSelectedTabPosition()==1)
                {
                    switch(position)
                    {
                        case 0:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, HotelBookingMainActivity.class);
                            i.putExtra("list",extra);
                            startActivity(i);
                            break;
                        }
                        case 1:
                        {
                            Intent i=new Intent(MainActivity_1.this, TouristAttActivity.class);
                            //   i.putExtra("list",extra);
                            startActivity(i);
                            break;
                        }
                        case 2:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, HotelReviewsActivity.class);
                            startActivity(i);
                        }
                    }
                } else if(myTableLayout.getSelectedTabPosition()==2)
                {
                    switch(position)
                    {
                        case 0:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, ClientActivity.class);
                            startActivity(i);
                            break;
                        }
                        case 1:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, CarRentalReviewActivity.class);
                            startActivity(i);
                            break;
                        }
                    }

                }else if(myTableLayout.getSelectedTabPosition()==3)
                {
                    switch(position)
                    {
                        case 0:
                        {
                             Intent i=new Intent(MainActivity_1.this, ComboMainActivity.class);
                            startActivity(i);
                            break;
                        }
                        case 1:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, HomeActivity.class);
                            i.putExtra("list",extra);
                            startActivity(i);
                            break;
                        }
                        case 2:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, CurrencySplashActivity.class);
                            //   i.putExtra("list",extra);
                            startActivity(i);
                            break;
                        }
                        case 3:
                        {
                            Intent i=new Intent(MainActivity_1.this, MainActivity.class);
                            startActivity(i);
                            break;
                        }
                        case 4:
                        {
                            Intent i=new Intent(MainActivity_1.this, TaxiServiceActivity.class);
                            startActivity(i);

                            break;
                        }
                        case 5:
                        {
                            Intent i=new Intent(MainActivity_1.this, EmergencyServicesInfoActivity.class);
                            startActivity(i);
                            break;
                        }
                    }

                }else if(myTableLayout.getSelectedTabPosition()==0)
                {
                    switch(position)
                    {
                        case 0:
                        {
                                Snackbar snackbar = Snackbar.make(view, "Welcome to Vancouver Tourism, "+allItemsList.get(0),Snackbar.LENGTH_LONG);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                                snackbar.show();
                            break;
                        }
                        case 1:
                        {
                           Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, HotelBookingMainActivity.class);
                            i.putExtra("list",extra);
                            startActivity(i);
                            break;
                        }
                        case 2:
                        {
                             Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, TouristAttActivity.class);
                            //   i.putExtra("list",extra);
                            startActivity(i);
                            break;
                        }
                        case 3:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, HotelReviewsActivity.class);
                            //   i.putExtra("list",extra);
                            startActivity(i);

                            break;
                        }
                        case 4:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, ClientActivity.class);
                            //    i.putExtra("list",extra);
                            startActivity(i);
                            break;
                        }
                        case 5:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, CarRentalReviewActivity.class);
                            startActivity(i);
                            break;
                        }
                        case 6:
                        {
                            Toast.makeText(MainActivity_1.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity_1.this, ComboMainActivity.class);
                            startActivity(i);
                            break;
                        }
                        case 7:
                        {
                            Intent i=new Intent(MainActivity_1.this, HomeActivity.class);
                            startActivity(i);
                            break;
                        }
                        case 8:
                        {
                            Intent i=new Intent(MainActivity_1.this, CurrencySplashActivity.class);
                            startActivity(i);
                            break;
                        }
                        case 9:
                        {
                            Intent i=new Intent(MainActivity_1.this, MainActivity.class);
                            startActivity(i);
                            break;
                        }
                        case 10:
                        {
                            Intent i=new Intent(MainActivity_1.this, TaxiServiceActivity.class);
                            startActivity(i);

                            break;
                        }
                        case 11:
                        {
                            Intent i=new Intent(MainActivity_1.this, EmergencyServicesInfoActivity.class);
                            startActivity(i);
                            break;
                        }
                    }

                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        myTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(myTableLayout.getSelectedTabPosition())
                {
                    case 0:
                        Toast.makeText(MainActivity_1.this,"Clicked on all", Toast.LENGTH_SHORT).show();
                        myAdapter.changeItemList(allItemsList, allPicList, allDesc);
                        break;
                    case 1:
                        Toast.makeText(MainActivity_1.this,"Clicked on all", Toast.LENGTH_SHORT).show();
                        myAdapter.changeItemList(hotelBook, hotelPagePicList, hotelDesc);
                        break;
                    case 2:
                        myAdapter.changeItemList(carRentalList, carRentalPicList, carRentalDesc);
                        break;
                    case 3:
                        myAdapter.changeItemList(miscServices, miscPicList, miscDesc);
                        break;
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private ArrayList<HotelsDB> processData() {
        String json = null;
        try {
            InputStream is = getAssets().open("HotelsDB.json");


            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        ArrayList<HotelsDB> temp = new ArrayList<>();
        try {

            JSONArray ar = new JSONArray(json);
            JSONObject element;
            HotelsDB hotelsDB;

            for (int i=0 ; i < ar.length(); i++) {
                element = ar.getJSONObject(i);
                hotelsDB = new HotelsDB();
                hotelsDB.hotelID = Integer.parseInt(element.getString("HotelID"));
                hotelsDB.starRating = Integer.parseInt(element.getString("StarRating"));
                hotelsDB.desc = element.getString("Description");
                hotelsDB.phoneNumber = element.getString("PhoneNumber");
                hotelsDB.picture = element.getString("Picture");
                hotelsDB.website = element.getString("Website");
                hotelsDB.stdQuantity = Integer.parseInt(element.getJSONObject("Standard").getString("Quantity"));
                hotelsDB.stQuantity = Integer.parseInt(element.getJSONObject("Suite").getString("Quantity"));
                hotelsDB.dbQuantity = Integer.parseInt(element.getJSONObject("Double").getString("Quantity"));
                hotelsDB.lxQuantity = Integer.parseInt(element.getJSONObject("Luxury").getString("Quantity"));
                hotelsDB.stdPrice = Integer.parseInt(element.getJSONObject("Standard").getString("Price"));
                hotelsDB.stPrice = Integer.parseInt(element.getJSONObject("Suite").getString("Price"));
                hotelsDB.lxPrice = Integer.parseInt(element.getJSONObject("Luxury").getString("Price"));
                hotelsDB.dbPrice = Integer.parseInt(element.getJSONObject("Double").getString("Price"));
                hotelsDB.location= element.getString("Location");
                hotelsDB.policies= element.getString("Policies");
                hotelsDB.hotelName=element.getString("HotelName");
                temp.add(hotelsDB);
            }
            return temp;
        } catch (JSONException e) {
            Log.d("MainActivity", e.getMessage());
        }

        return null;
    }
}
