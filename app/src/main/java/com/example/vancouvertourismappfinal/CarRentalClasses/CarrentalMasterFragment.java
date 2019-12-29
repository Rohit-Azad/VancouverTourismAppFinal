package com.example.vancouvertourismappfinal.CarRentalClasses;


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
public class CarrentalMasterFragment extends Fragment {

    private ListView lv ;

    public CarrentalMasterFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("onCreateView","MasterFragment");
        return inflater.inflate(R.layout.fragment_car_master, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("onViewCreated","MasterFragment");
        lv = (ListView) view.findViewById(R.id.theList);

    }

    public void setTheData(ArrayList<String> p) {
        // called from the MainActivity
        Log.d("setTheData","MasterFragment");
        ArrayAdapter<String> adapterNames;
    //    final ArrayList<CountryInfo> countryInfo= (ArrayList<CountryInfo>) getArguments().getSerializable("pepe");

        adapterNames = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, p);
        lv.setAdapter(adapterNames);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onItemClick","MasterFragment");

                // DetailFragment class should be created first
                CarRentalDetailFragment df = new CarRentalDetailFragment();
                Bundle b = new Bundle();
                b.putString("data",lv.getItemAtPosition(position).toString());
               // b.putSerializable("pepe2",countryInfo);

                df.setArguments(b);
                getFragmentManager().beginTransaction()
                        .replace(R.id.theDetail,df)   // R.id.theDetail refers to the <FrameLayout>
                        .commit();

            }
        });
    }
}
