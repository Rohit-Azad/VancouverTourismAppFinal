package com.example.vancouvertourismappfinal.TaxiServiceClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vancouvertourismappfinal.MainActivity_1;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristResultTestActivity;
import com.example.vancouvertourismappfinal.TouristAttractionsActivity.TouristSpotsDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class DBConnectivity_Taxi extends BroadcastReceiver {

    ArrayList<TaxiDB> taxiDBList;
    TaxiDB taxiDB;

    String insertData;
    public static final String MyPREFERENCES = "com.example.myrentalapp.MyPrefs";
    private TableLayout tbl;
    private Context context;
    Context c;
    public static final String STATUS_DONE = "ALL_DONE";

    public DBConnectivity_Taxi( Context context) {

        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        insertData=prefs.getString("justDone", "No fullName defined");
//        password1=prefs.getString("pass", "No fullName defined");
//        Log.d("Biggest test", "onReceive: "+role1 + " === "+password1);
        if (intent.getAction().equals(STATUS_DONE)) {

            String text = intent.getStringExtra("output_data");
//            String columns[] = {"Password","Role"};
            Log.d("DB - onReceive", text);

            if(insertData.equals("TaxiService"))
            {
            try {
                Log.d("data", text);
                JSONArray ar = new JSONArray(text);
                JSONObject jobj;
                taxiDBList = new ArrayList<>();

                for (int x = 0; x < ar.length(); x++) {
                    jobj = ar.getJSONObject(x);
                    taxiDB = new TaxiDB();
                    taxiDB.id = Integer.parseInt(jobj.getString("TaxiID"));
                    taxiDB.seating = Integer.parseInt(jobj.getString("SeatingCapacity"));
                    taxiDB.quantity = Integer.parseInt(jobj.getString("Quantity"));
                    taxiDB.name = jobj.getString("VehicleName");
                    taxiDB.type = jobj.getString("VehicleType");
                    taxiDB.price = Double.parseDouble(jobj.getString("Price"));
                    taxiDBList.add(taxiDB);
                }
                Log.d("TaxiNamefromMySql", "onReceive: " + taxiDBList.get(0).name);


            } catch (Exception e) {
                e.printStackTrace();
            }
                Bundle extra=new Bundle();
                extra.putSerializable("list",taxiDBList);
                Intent i=new Intent(context, TaxiServiceResultActivity.class);
                i.putExtra("listSpots",extra);
                context.startActivity(i);
        }
            else if(insertData.equals("EnterReservation"))
            {
                Toast.makeText(context,"Data inserted into mysql database with message - "+text.trim(),Toast.LENGTH_LONG).show();
                Intent i=new Intent(context, MainActivity_1.class);
                context.startActivity(i);
            }

        }
    }
}
