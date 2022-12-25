package com.example.ecommerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.MyCartModel;
import com.example.ecommerce.models.TodaysDealsModel;
import com.example.ecommerce.models.commonModel;
import com.example.ecommerce.models.showAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCartModel>list;
    int totalAmount;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    MyCartModel myCartModel;
    ImageView incr,decr;
    TodaysDealsModel todaysDealsModel =null;
    List<MyCartModel> cartModelList;
    commonModel commonModels =null;
    com.example.ecommerce.models.showAllModel showAllModel =null;
    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice()+"$");
        holder.name.setText(list.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice())+"$");
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());
        holder.incr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant=Integer.valueOf(list.get(position).getTotalQuantity());
                if(quant<10){
                int counter=Integer.valueOf(list.get(position).getTotalQuantity());
                counter++;
                int productprice=Integer.valueOf(list.get(position).getProductPrice());

                int totalpriceofproducts=counter*productprice;
                String s=String.valueOf(counter);
                list.get(position).setTotalQuantity(s);
                list.get(position).setTotalPrice(totalpriceofproducts);

                notifyDataSetChanged();}
            }
        });
        holder.decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant=Integer.valueOf(list.get(position).getTotalQuantity());
                if(quant>1){
                int counter=Integer.valueOf(list.get(position).getTotalQuantity());
                counter--;
                int productprice=Integer.valueOf(list.get(position).getProductPrice());
                int totalproductprice=list.get(position).getTotalPrice();
                int totalpriceofproducts=totalproductprice-productprice;
                String s=String.valueOf(counter);
                list.get(position).setTotalQuantity(s);
                list.get(position).setTotalPrice(totalpriceofproducts);
                notifyDataSetChanged();}

            }
        });


        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(list.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    list.remove(list.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });





    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,totalQuantity,totalPrice;
        ImageView deleteItem,incr,decr;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.product_name);
            price =itemView.findViewById(R.id.product_price);
            date =itemView.findViewById(R.id.current_date);
            time =itemView.findViewById(R.id.current_time);
            totalQuantity =itemView.findViewById(R.id.total_quantity);
            totalPrice =itemView.findViewById(R.id.total_price);
            deleteItem=itemView.findViewById(R.id.delete);
            incr =itemView.findViewById(R.id.plusbtn);
            decr =  itemView.findViewById(R.id.minusbtn);

        }
    }
}
