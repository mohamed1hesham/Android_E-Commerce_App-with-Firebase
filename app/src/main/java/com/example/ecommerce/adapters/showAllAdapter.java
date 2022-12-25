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
import com.example.ecommerce.models.showAllModel;

import java.util.List;

public class showAllAdapter extends RecyclerView.Adapter<showAllAdapter.ViewHolder> {
    private Context context;
    private List<showAllModel> list;

    public showAllAdapter(Context context, List<showAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.mItemImage);
        holder.mCost.setText("$"+list.get(position).getPrice()) ;
        holder.mName.setText(list.get(position).getName());

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
         private ImageView mItemImage;
         private TextView mCost;
         private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage=itemView.findViewById(R.id.item_image);
            mCost=itemView.findViewById(R.id.item_cost);
            mName=itemView.findViewById(R.id.item_nam);
        }
    }
}
