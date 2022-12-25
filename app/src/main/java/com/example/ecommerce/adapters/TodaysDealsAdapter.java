package com.example.ecommerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.DetailsProducts;
import com.example.ecommerce.R;
import com.example.ecommerce.models.TodaysDealsModel;

import java.util.List;


public class TodaysDealsAdapter extends RecyclerView.Adapter<TodaysDealsAdapter.ViewHolder> {

    private Context context;
    private List<TodaysDealsModel> list;

    public TodaysDealsAdapter(Context context, List<TodaysDealsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todays_deals,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.newImg);
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsProducts.class);
                intent.putExtra("details",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


         ImageView newImg;
         TextView newName,newPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newImg=itemView.findViewById(R.id.new_img);
            newName=itemView.findViewById(R.id.new_product_name);
            newPrice=itemView.findViewById(R.id.new_price);
        }
    }
}
