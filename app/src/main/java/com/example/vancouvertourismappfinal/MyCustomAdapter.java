package com.example.vancouvertourismappfinal;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder> {

    List<String> itemList;
    List<Integer> picList;
    List<String> dirList;
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtViewName;
        TextView txtViewDir;
        ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewName=(TextView)itemView.findViewById(R.id.txtViewItemName);
            imgView=(ImageView)itemView.findViewById(R.id.imageView);
            txtViewDir=(TextView)itemView.findViewById(R.id.textView2);
        }
    }
    public MyCustomAdapter(List<String> anyList, List<Integer> anyPicList, List<String> anyDirList)
    {
        itemList=anyList;
        picList=anyPicList;
        dirList=anyDirList;

    }

    public void changeItemList(List<String> anyList, List<Integer> anyPicList, List<String> anyDirList)
    {
        itemList=anyList;
        picList=anyPicList;
        dirList=anyDirList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //create new view
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.layout_item,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtViewName.setText(itemList.get(i));
        viewHolder.txtViewDir.setText(dirList.get(i));
        viewHolder.imgView.setImageResource(picList.get(i));

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
