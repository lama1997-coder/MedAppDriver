package com.magsood.medappuser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magsood.medappuser.Adapter.AdapterSearchResult;
import com.magsood.medappuser.Model.ModelSearch;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.SearchService;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<ModelSearch> modelSearchArrayList;
    AdapterSearchResult adapterSearchResult;
    AutoCompleteTextView search ;
    SearchService searchService;
    Context context;
    private static final int REQUEST_CODE = 1234;
    ImageView recordIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();



        if(getIntent().getExtras()!=null){
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("searchStr");

            searchService = new SearchService();
            searchService.search(this,title);}


        ( (LinearLayout)(findViewById(R.id.back))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() ,MainActivity.class);
                startActivity(intent);
            }
        });
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);



        recordIcon.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startVoiceRecognition();
    }
});
        searchService = new SearchService();
        ArrayList<String> medicine = searchService.getMedicine(this);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medicine);
        search.setAdapter(adapter);
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
    public void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        intent.putExtra("android.speech.extra.DICTATION_MODE", true);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);


        startActivityForResult(intent, REQUEST_CODE);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList< String > matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty())
            {
                String Query = matches.get(0);
                search.setText(Query + matches);


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ImageView ic_back;
    private void init() {
        recordIcon= findViewById(R.id.ic_search);
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