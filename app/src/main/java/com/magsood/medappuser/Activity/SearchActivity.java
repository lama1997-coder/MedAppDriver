package com.magsood.medappuser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.magsood.medappuser.Adapter.AdapterSearchResult;
import com.magsood.medappuser.Model.ModelSearch;
import com.magsood.medappuser.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<ModelSearch> modelSearchArrayList;
    AdapterSearchResult adapterSearchResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    ImageView ic_back;
    private void init() {
        ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler);
        initAdapter();
    }

    private void initAdapter() {
        modelSearchArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ModelSearch modelSearch = new ModelSearch();
            modelSearch.setId(i+"");
            modelSearchArrayList.add(modelSearch);
        }
        adapterSearchResult = new AdapterSearchResult(this,modelSearchArrayList);
        recyclerView.setAdapter(adapterSearchResult);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ic_back:{
                finish();
                break;
            }
        }
    }




}