package com.example.vancouvertourismappfinal.ComboPlansClasses;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCustomAdapter_combo extends RecyclerView.Adapter<MyCustomAdapter_combo.ViewHolder> {

    List<String> itemList1;
    List<String> picList1;
    List<String> dirList1;

    List<String> carList;
    List<String> carPicList;
    List<String> carTypeList;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvHotelName;
        TextView tvHotelCity;
        TextView tvCarName;
        TextView tvCarType;
        ImageView imgView1;
        ImageView imgView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName=(TextView)itemView.findViewById(R.id.tvHotelName);
            imgView1=(ImageView)itemView.findViewById(R.id.imgHotel);
            tvHotelCity=(TextView)itemView.findViewById(R.id.tvCity);

            tvCarName=(TextView)itemView.findViewById(R.id.tvCarName);
            imgView2=(ImageView)itemView.findViewById(R.id.imgCar);
            tvCarType=(TextView)itemView.findViewById(R.id.tvType);
        }
    }
    public MyCustomAdapter_combo(List<String> anyList, List<String> anyPicList, List<String> anyDirList,List<String> anycarArray,List<String> anyCarPicArray,List<String> anyCarTypeArray)
    {
        itemList1=anyList;
        picList1=anyPicList;
        dirList1=anyDirList;
        carList=anycarArray;
        carPicList=anyCarPicArray;
        carTypeList=anyCarTypeArray;

    }

    public void changeItemList(List<String> anyList, List<String> anyPicList, List<String> anyDirList)
    {
        itemList1=anyList;
        picList1=anyPicList;
        dirList1=anyDirList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //create new view
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.layout_item_combo,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvHotelName.setText(itemList1.get(i));
        viewHolder.tvHotelCity.setText(dirList1.get(i));
        Picasso.get()
                .load(picList1.get(i))
                // .placeholder(R.drawable.wait)
//                .resize(500, 500)
                .error(R.drawable.error)
                .into(viewHolder.imgView1);

        viewHolder.tvCarName.setText(carList.get(i));
        viewHolder.tvCarType.setText(carTypeList.get(i));
        Picasso.get()
                .load(carPicList.get(i))
                // .placeholder(R.drawable.wait)
//                .resize(500, 500)
                .error(R.drawable.error)
                .into(viewHolder.imgView2);
    }


    @Override
    public int getItemCount() {
        return itemList1.size();
    }
}
