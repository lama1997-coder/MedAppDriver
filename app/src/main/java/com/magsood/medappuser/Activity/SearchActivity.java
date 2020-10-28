package com.magsood.medappuser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.magsood.medappuser.Adapter.AdapterSearchResult;
import com.magsood.medappuser.Model.ModelSearch;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.SearchService;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<ModelSearch> modelSearchArrayList;
    AdapterSearchResult adapterSearchResult;
    EditText search ;
    SearchService searchService;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();



        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Your piece of code on keyboard search click
                  searchFun();
                    return true;
                }
                return false;
            }
        });



    }

    private void searchFun() {
        searchService = new SearchService();
        searchService.search(this,search.getText().toString());
    }

    ImageView ic_back;
    private void init() {
        ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler);
        search = findViewById(R.id.editsearch);

    }

//    private void initAdapter() {
//        modelSearchArrayList = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            ModelSearch modelSearch = new ModelSearch();
//            modelSearch.setId(i+"");
//            modelSearchArrayList.add(modelSearch);
//        }
//        adapterSearchResult = new AdapterSearchResult(this,modelSearchArrayList);
//        recyclerView.setAdapter(adapterSearchResult);
//    }

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