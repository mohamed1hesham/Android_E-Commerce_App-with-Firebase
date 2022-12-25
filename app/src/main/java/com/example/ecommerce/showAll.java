package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.ecommerce.adapters.showAllAdapter;
import com.example.ecommerce.models.showAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class showAll extends AppCompatActivity {
    RecyclerView recyclerView;
    showAllAdapter showAllAdapter;
    List<showAllModel> showAllModelList;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        toolbar=findViewById(R.id.toolbarShowAll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firestore=FirebaseFirestore.getInstance();
        String type=getIntent().getStringExtra("type");

        recyclerView=findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        showAllModelList=new ArrayList<>();
        showAllAdapter=new showAllAdapter(this,showAllModelList);
        recyclerView.setAdapter(showAllAdapter);

        if(type==null || type.isEmpty()){
            firestore.collection("ShowAll")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(com.example.ecommerce.models.showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }
        if(type!=null&&type.equalsIgnoreCase("men")){
            firestore.collection("ShowAll").whereEqualTo("type","men")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(com.example.ecommerce.models.showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });

        }
        if(type!=null&&type.equalsIgnoreCase("woman")){
            firestore.collection("ShowAll").whereEqualTo("type","woman")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(com.example.ecommerce.models.showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });

        }
        if(type!=null&&type.equalsIgnoreCase("watch")){
            firestore.collection("ShowAll").whereEqualTo("type","watch")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(com.example.ecommerce.models.showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });

        }
        if(type!=null&&type.equalsIgnoreCase("camera")){
            firestore.collection("ShowAll").whereEqualTo("type","camera")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(com.example.ecommerce.models.showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });

        }
        if(type!=null&&type.equalsIgnoreCase("kids")){
            firestore.collection("ShowAll").whereEqualTo("type","kids")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(com.example.ecommerce.models.showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });

        }
        if(type!=null&&type.equalsIgnoreCase("shoes")){
            firestore.collection("ShowAll").whereEqualTo("type","shoes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(com.example.ecommerce.models.showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });


        }
        if(type!=null&&type.equalsIgnoreCase("labtop")){
            firestore.collection("ShowAll").whereEqualTo("type","labtop")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(com.example.ecommerce.models.showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });


        }


    }




}