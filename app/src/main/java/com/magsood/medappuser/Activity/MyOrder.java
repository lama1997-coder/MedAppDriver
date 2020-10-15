package com.magsood.medappuser.Activity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;


import com.magsood.medappuser.Adapter.AdapterMyOrder;
import com.magsood.medappuser.Model.ModelMyOrder;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.ToolbarClass;

import java.util.ArrayList;

public class MyOrder extends ToolbarClass {

    RecyclerView recycler;
    ArrayList<ModelMyOrder> arrayList;
    AdapterMyOrder adapterMyOrder;


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_my_order, "طلباتي");

        init();
    }

    private void init() {
        recycler = findViewById(R.id.recycler);
        arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ModelMyOrder item = new ModelMyOrder();
            item.setId(i+"");
            arrayList.add(item);
        }
        adapterMyOrder = new AdapterMyOrder(this,arrayList);
        recycler.setAdapter(adapterMyOrder);
    }
}