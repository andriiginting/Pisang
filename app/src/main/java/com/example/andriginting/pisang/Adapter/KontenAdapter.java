package com.example.andriginting.pisang.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andriginting.pisang.Model.MenuModel;
import com.example.andriginting.pisang.R;

import java.util.List;

/**
 * Created by Andri Ginting on 10/3/2017.
 */

public class KontenAdapter extends RecyclerView.Adapter<KontenAdapter.MyViewHolder> {
    private Context context;
    private List<MenuModel.MenuModel> menuModelList;

    public KontenAdapter(Context context, List<MenuModel.MenuModel> modelList){
        this.context=context;
        menuModelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_mainmenu,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenuModel.MenuModel model = menuModelList.get(position);
        holder.judul.setText(model.getKonten());

        Glide.with(context)
                .load(model.getThumbnail())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return menuModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView judul;
        public ImageView thumbnail;


        public MyViewHolder(View itemView) {
            super(itemView);
            judul = (TextView)itemView.findViewById(R.id.nama_kontent_mainMenu);
            thumbnail =(ImageView)itemView.findViewById(R.id.image_card_mainMenu);

        }
    }
}
