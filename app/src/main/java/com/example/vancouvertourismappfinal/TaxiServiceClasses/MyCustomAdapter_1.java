package com.example.vancouvertourismappfinal.TaxiServiceClasses;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vancouvertourismappfinal.R;

import java.util.List;

public class MyCustomAdapter_1 extends RecyclerView.Adapter<MyCustomAdapter_1.ViewHolder> {

    List<String> itemList;
    List<String> dirList;
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtViewName;
        TextView txtViewDir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewName=(TextView)itemView.findViewById(R.id.txtViewItemName);
            txtViewDir=(TextView)itemView.findViewById(R.id.textView2);
        }
    }
    public MyCustomAdapter_1(List<String> anyList, List<String> anyDirList)
    {
        itemList=anyList;
        dirList=anyDirList;

    }

    public void changeItemList(List<String> anyList,List<String> anyDirList)
    {
        itemList=anyList;
        dirList=anyDirList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //create new view
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.layout_item_taxi,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtViewName.setText(itemList.get(i));
        viewHolder.txtViewDir.setText(dirList.get(i));
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
