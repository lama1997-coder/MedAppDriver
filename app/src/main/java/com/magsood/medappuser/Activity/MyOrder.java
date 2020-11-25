package com.magsood.medappuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.magsood.medappuser.Adapter.AdapterMyOrder;
import com.magsood.medappuser.Model.ModelMyOrder;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.PrevOrdersService;
import com.magsood.medappuser.Utils.ToolbarClass;

import java.util.ArrayList;

public class MyOrder extends AppCompatActivity {

    RecyclerView recycler;
    ArrayList<ModelMyOrder> arrayList;
    AdapterMyOrder adapterMyOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_activity);
        init();
        ( (LinearLayout)(findViewById(R.id.ic_back))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() ,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
//        recycler = findViewById(R.id.recycler);
//        arrayList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ModelMyOrder item = new ModelMyOrder();
//            item.setId(i+"");
//            arrayList.add(item);
//        }

        PrevOrdersService prevOrdersService = new PrevOrdersService();
        prevOrdersService.pre_orders(this);
//        adapterMyOrder = new AdapterMyOrder(this,arrayList);
//        recycler.setAdapter(adapterMyOrder);
    }
}