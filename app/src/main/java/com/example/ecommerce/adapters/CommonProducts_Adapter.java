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
import com.example.ecommerce.models.commonModel;

import java.util.List;

public class CommonProducts_Adapter extends RecyclerView.Adapter<CommonProducts_Adapter.ViewHolder> {
    private Context context;
    private List<commonModel> commonModelList;

    public CommonProducts_Adapter(Context context, List<commonModel> commonModelList) {
        this.context = context;
        this.commonModelList = commonModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.common_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(commonModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(commonModelList.get(position).getName());
        holder.price.setText(String.valueOf(commonModelList.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsProducts.class);
                intent.putExtra("details",commonModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return commonModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.all_img);
            name=itemView.findViewById(R.id.all_product_name);
            price=itemView.findViewById(R.id.all_price);
        }
    }
}
