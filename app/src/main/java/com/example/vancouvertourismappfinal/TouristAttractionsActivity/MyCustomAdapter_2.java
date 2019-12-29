package com.example.vancouvertourismappfinal.TouristAttractionsActivity;


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

public class MyCustomAdapter_2 extends RecyclerView.Adapter<MyCustomAdapter_2.ViewHolder> {

    List<String> itemList;
    List<String> picList;
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
    public MyCustomAdapter_2(List<String> anyList, List<String> anyPicList, List<String> anyDirList)
    {
        itemList=anyList;
        picList=anyPicList;
        dirList=anyDirList;

    }

    public void changeItemList(List<String> anyList, List<String> anyPicList, List<String> anyDirList)
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
        Picasso.get()
                .load(picList.get(i))
                // .placeholder(R.drawable.wait)
//                .resize(500, 500)
                .error(R.drawable.error)
                .into(viewHolder.imgView);

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
