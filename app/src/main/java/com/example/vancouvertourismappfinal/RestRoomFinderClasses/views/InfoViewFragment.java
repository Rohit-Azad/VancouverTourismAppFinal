package com.example.vancouvertourismappfinal.RestRoomFinderClasses.views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.R;
import com.example.vancouvertourismappfinal.RestRoomFinderClasses.models.Bathroom;


public class InfoViewFragment extends android.support.v4.app.Fragment {

    public static final String EXTRA_BATHROOM = "bathroom";
    protected static final String TAG = InfoViewFragment.class.getSimpleName();
    private Bathroom mBathroom;
    public View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle args = getArguments();
        if (args != null) {
            mBathroom = Bathroom.fromJson(args.getString(EXTRA_BATHROOM));
        }
        // Creates a close button for the fragment
        final Button button = (Button) view.findViewById(R.id.close_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ((ImageView) view.findViewById(R.id.button_maps)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInGoogleMaps();
            }
        });

        updateView();
        return view;

    }

    // Sets the TextView to display bathroom info within the fragment
    private void updateView() {
        if (mBathroom != null) {
            TextView tv = (TextView) view.findViewById(R.id.text_title);
            tv.setText(mBathroom.getNameFormatted());
            TextView tv2 = (TextView) view.findViewById(R.id.text_address);
            tv2.setText(mBathroom.getAddressFormatted() + "\n");
            TextView tv3 = (TextView) view.findViewById(R.id.text_comments);
            tv3.setText(Html.fromHtml(mBathroom.getCommentsFormatted()));
            // Gets specs such as bathroom rating, accessibility, and unisex properties
            View specsView = view.findViewById(R.id.specs);
            BathroomSpecsViewUpdater.update(specsView, mBathroom, getActivity());
        }
    }

    private void openInGoogleMaps() {
        double lat = mBathroom.getmLatitude();
        double lon = mBathroom.getmLongitude();
        // Names need to be escaped, so a space should be replaced by either a + or by %20
        String addressEscaped = mBathroom.getAddress().replace(' ', '+');
        String uri = "geo:" + lat + "," + lon + "?q=" + addressEscaped;
        Uri intentUri = Uri.parse(uri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
