package com.example.vancouvertourismappfinal.TouristAttractionsActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.R;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class TouristDetailFragment extends Fragment {


    public TouristDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","DetailFragment");
        View v = inflater.inflate(R.layout.fragment_tourist_detail, null);
        String recvData = getArguments().getString("data");


        TextView textV = (TextView) v.findViewById(R.id.txtSomething); // this is referring to the item inside fragment_tourist_detail
        String s = "Some data that you want to display";
        textV.setText("Welcome " + recvData + "\n" + s);
        return v;

       // String text2 = intent.getStringExtra("output_site");


//        try {
//            JSONObject jo = new JSONObject(text2);
//            // go to the site and analyze the given output to determine the field names
//            String[]fields = {"name","callingCodes","capital","region","population","timezones","flag"} ;
//            for (int i=0 ;i < fields.length; i++) {
//                tv.append(fields[i] + " = " + jo.getString(fields[i]) + "\n");
//                if (fields[i].equalsIgnoreCase("flag")) {
//                    // need to add  compile 'com.squareup.picasso:picasso:2.3.3'
////                            Picasso.with(c)
////                                    .load("https://media1.britannica.com/eb-media/54/69554-004-3E298C44.jpg")
////                                    .placeholder(R.drawable.ic_android_temp)
////                                    .error(R.drawable.ic_error)
////                                    .into(img);
//                }
//            }

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
