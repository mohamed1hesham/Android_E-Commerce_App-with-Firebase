package com.example.ecommerce;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.adapters.MyCartAdapter;
import com.example.ecommerce.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    int overAllTotalAmount;
    TextView overAllAmount;
    ImageView plusb,minusb;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        plusb=findViewById(R.id.plusbtn);
        minusb=findViewById(R.id.minusbtn);
        toolbar=findViewById(R.id.myCart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        overAllAmount=findViewById(R.id.overallamount);
        recyclerView=findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList =new ArrayList<>();
        cartAdapter=new MyCartAdapter(this,cartModelList);
        recyclerView.setAdapter(cartAdapter);
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc :task.getResult().getDocuments()){
                        String documentId=doc.getId();

                        MyCartModel myCartModel=doc.toObject(MyCartModel.class);
                        myCartModel.setDocumentId(documentId);
                        cartModelList.add(myCartModel);
                        cartAdapter.notifyDataSetChanged();

                    }
                    calculateTotalAmount(cartModelList);
                }
            }
        });

    }


    private void calculateTotalAmount(List<MyCartModel> cartModelList) {
        double totalAmount =0.0;
                for(MyCartModel myCartModel : cartModelList){
                    totalAmount+= myCartModel.getTotalPrice();
                }
                overAllAmount.setText("Total Amount :"+totalAmount);
    }


}