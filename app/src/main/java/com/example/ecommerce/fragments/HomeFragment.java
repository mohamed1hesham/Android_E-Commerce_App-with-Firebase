package com.example.ecommerce.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.Category_Adapter;
import com.example.ecommerce.adapters.CommonProducts_Adapter;
import com.example.ecommerce.adapters.TodaysDealsAdapter;
import com.example.ecommerce.adapters.showAllAdapter;
import com.example.ecommerce.models.TodaysDealsModel;
import com.example.ecommerce.models.categoryModel;
import com.example.ecommerce.models.commonModel;
import com.example.ecommerce.models.showAllModel;
import com.example.ecommerce.showAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {
    TextView catShowAll,todaysShowAll,commonShowAll;
    ImageView imgg;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerview,todaysDealsRecyclerView,commonRecyclerView;
    Category_Adapter category_adapter;
    List<categoryModel> categoryModelList;



    TodaysDealsAdapter todaysDealsAdapter;
    List<TodaysDealsModel> todaysDealsModelList;

    CommonProducts_Adapter commonProducts_adapter;
    List<commonModel> commonModelList;
    FirebaseFirestore db ;

    EditText search_bar;
    private List<showAllModel> showAllModelList;
    private RecyclerView recyclerViewSearch;
    private com.example.ecommerce.adapters.showAllAdapter showAllAdapter;



    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);
        db=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getActivity());
        linearLayout=root.findViewById(R.id.home_layout);
        catRecyclerview =root.findViewById(R.id.rec_category);
        todaysDealsRecyclerView=root.findViewById(R.id.new_product_rec);
        commonRecyclerView=root.findViewById(R.id.popular_rec);




        commonShowAll=root.findViewById(R.id.popular_see_all);

        commonShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), showAll.class);
                startActivity(intent);
            }
        });


        ImageSlider imageSlider=root.findViewById(R.id.image_slider);
        linearLayout.setVisibility(View.GONE);
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banners3,"discount", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banners5,"discount", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banners6,"discount", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner1,"Discount", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banners1,"Discount ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Discount ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banners2,"Discount ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"discount", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banners4,"Discount ", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        progressDialog.setTitle("Welcome to your Shop Gate");
        progressDialog.setMessage("Waiting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));





        categoryModelList=new ArrayList<>();
        category_adapter=new Category_Adapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(category_adapter);
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                            categoryModel categoryModel=document.toObject(com.example.ecommerce.models.categoryModel.class);
                            categoryModelList.add(categoryModel);
                            category_adapter.notifyDataSetChanged();
                            linearLayout.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            todaysDealsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            todaysDealsModelList=new ArrayList<>();
            todaysDealsAdapter=new TodaysDealsAdapter(getContext(),todaysDealsModelList);
            todaysDealsRecyclerView.setAdapter(todaysDealsAdapter);
        db.collection("Today's Deals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TodaysDealsModel todaysDealsModel=document.toObject(com.example.ecommerce.models.TodaysDealsModel.class);
                                todaysDealsModelList.add(todaysDealsModel);
                                todaysDealsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        commonRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        commonModelList=new ArrayList<>();
        commonProducts_adapter=new CommonProducts_Adapter(getContext(),commonModelList);
        commonRecyclerView.setAdapter(commonProducts_adapter);
        db.collection("CommonProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                commonModel commonModels=document.toObject(com.example.ecommerce.models.commonModel.class);
                                commonModelList.add(commonModels);
                                commonProducts_adapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });




        recyclerViewSearch=root.findViewById(R.id.search_rec);
        search_bar=root.findViewById(R.id.searchBar);
        showAllModelList=new ArrayList<>();
        showAllAdapter=new showAllAdapter(getContext(),showAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(showAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);

        imgg=root.findViewById(R.id.speakbtn);
        imgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                startActivityForResult(intent,10);

            }

        });





        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().isEmpty()){
                    showAllModelList.clear();
                    showAllAdapter.notifyDataSetChanged();
                }else{
                    searchProduct(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }


    private void searchProduct(String type) {

        if(!type.isEmpty()){
            db.collection("ShowAll").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()&&task.getResult()!=null){
                                showAllModelList.clear();
                                showAllAdapter.notifyDataSetChanged();
                                for(DocumentSnapshot doc : task.getResult().getDocuments()){
                                    showAllModel showAllModel=doc.toObject(showAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                search_bar.setText(result.get(0));

        }}


}