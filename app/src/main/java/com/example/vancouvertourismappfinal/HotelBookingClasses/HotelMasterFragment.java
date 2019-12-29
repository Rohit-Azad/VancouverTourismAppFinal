package com.example.vancouvertourismappfinal.HotelBookingClasses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vancouvertourismappfinal.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotelMasterFragment extends Fragment {

    private ListView lv ;

    public HotelMasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("onCreateView","MasterFragment");
        return inflater.inflate(R.layout.fragment_hotel_master, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("onViewCreated","MasterFragment");
        lv = view.findViewById(R.id.theList);  // it is in fragment_hotel_mastermaster.xml

    }

    public void setTheData(ArrayList<String> p) {
        // called from the MainActivity
        Log.d("setTheData","MasterFragment");
        ArrayAdapter<String> adapterNames;

        adapterNames = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, p);
        lv.setAdapter(adapterNames);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onItemClick","MasterFragment");

                // DetailFragment class should be created first
                HotelDetailFragment df = new HotelDetailFragment();
                Bundle b = new Bundle();
                b.putString("data",lv.getItemAtPosition(position).toString());
                // b.putSerializable("data", someArrayList);

                df.setArguments(b);
                getFragmentManager().beginTransaction()
                        .replace(R.id.theDetail,df)   // R.id.theDetail refers to the <FrameLayout>
                        .commit();

            }
        });


    }

}
