package com.example.vancouvertourismappfinal.TouristAttractionsActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBConnectivity_Tourist extends BroadcastReceiver {

    ArrayList<TouristSpotsDB> tsDB;
    TouristSpotsDB touristSpotsDB;
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    private TableLayout tbl;
    private Context context;
    Context c;
    public static final String STATUS_DONE = "ALL_DONE";

    public DBConnectivity_Tourist( Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATUS_DONE)) {

            String text = intent.getStringExtra("output_data");
            String columns[] = {"Name","Location","AvgReview"};
            Log.d("DB - onReceive",text);


            try {
                Log.d("data",text);
                JSONArray ar = new JSONArray(text);
                JSONObject jobj;
                tsDB=new ArrayList<>();

                for (int x=0; x < ar.length(); x++) {
                    jobj = ar.getJSONObject(x);
                    touristSpotsDB=new TouristSpotsDB();
                    touristSpotsDB.name=jobj.getString("Name");
                    touristSpotsDB.location=jobj.getString("Location");
                    touristSpotsDB.rating= (float) Double.parseDouble(jobj.getString("AvgReview"));
                    tsDB.add(touristSpotsDB);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            Bundle extra=new Bundle();
            extra.putSerializable("list",tsDB);
            Intent i=new Intent(context, TouristResultTestActivity.class);
            i.putExtra("listSpots",extra);
            context.startActivity(i);
        }
    }
}
