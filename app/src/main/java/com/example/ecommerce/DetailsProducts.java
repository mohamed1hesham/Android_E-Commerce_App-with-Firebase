package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ecommerce.models.TodaysDealsModel;
import com.example.ecommerce.models.commonModel;
import com.example.ecommerce.models.showAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class DetailsProducts extends AppCompatActivity {
    ImageView imageDetails;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow,addItem,removeItem;
    int totalQuantity =1;
    int totalPrice=0;


    TodaysDealsModel todaysDealsModel =null;

    commonModel commonModels =null;

    showAllModel showAllModel =null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        final Object obj=getIntent().getSerializableExtra("details");
        if(obj instanceof TodaysDealsModel){
            todaysDealsModel=(TodaysDealsModel) obj;
        }else if(obj instanceof commonModel){
            commonModels=(commonModel) obj;
        }else if(obj instanceof showAllModel){
            showAllModel=(showAllModel) obj;
        }
        setContentView(R.layout.activity_details_products);
        imageDetails=findViewById(R.id.imgDetails);
        name=findViewById(R.id.p_name);
        quantity=findViewById(R.id.qyt);
        rating=findViewById(R.id.rating);
        description=findViewById(R.id.details);
        price=findViewById(R.id.details_price);
        addToCart=findViewById(R.id.addcartbtn);
        buyNow=findViewById(R.id.buy_now);
        addItem=findViewById(R.id.addbtn);
        removeItem=findViewById(R.id.subbtn);

        if(todaysDealsModel !=null){
            Glide.with(getApplicationContext()).load(todaysDealsModel.getImg_url()).into(imageDetails);
            name.setText(todaysDealsModel.getName());
            rating.setText(todaysDealsModel.getRating());
            description.setText(todaysDealsModel.getDescription());
            price.setText(String.valueOf(todaysDealsModel.getPrice()));
            name.setText(todaysDealsModel.getName());
            totalPrice=todaysDealsModel.getPrice()*totalQuantity;

        }
        if(commonModels !=null){
            Glide.with(getApplicationContext()).load(commonModels.getImg_url()).into(imageDetails);
            name.setText(commonModels.getName());
            rating.setText(commonModels.getRating());
            description.setText(commonModels.getDescription());
            price.setText(String.valueOf(commonModels.getPrice()));
            name.setText(commonModels.getName());
            totalPrice=commonModels.getPrice()*totalQuantity;

        }
        if(showAllModel !=null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(imageDetails);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());
            totalPrice=showAllModel.getPrice()*totalQuantity;

        }
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoCart();
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(totalQuantity<10){
            totalQuantity++;
            quantity.setText(String.valueOf(totalQuantity));
            if(todaysDealsModel !=null){
                totalPrice=todaysDealsModel.getPrice()*totalQuantity;
            }
            if(commonModels != null){
                totalPrice=commonModels.getPrice()*totalQuantity;
            }if(showAllModel!=null){
                totalPrice=showAllModel.getPrice()*totalQuantity;
            }
        }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }

    private void addtoCart() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailsProducts.this,"Added To Cart",Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }

    public void act_1 (View view){
        Intent i = new Intent(DetailsProducts.this, HomePage.class);
        startActivity(i);
    }


}