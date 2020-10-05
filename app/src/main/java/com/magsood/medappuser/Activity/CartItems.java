package com.magsood.medappuser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.magsood.medappuser.Adapter.AdapterCart;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.SqlLiteDataBase;
import com.magsood.medappuser.Utils.ToolbarClass;

import java.util.ArrayList;

public class CartItems extends ToolbarClass {
    RecyclerView recyclerView;
    AdapterCart adapterCart;
    ArrayList<ModelCart> arrayList;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_cart_items, "سلة طلبيات الادوية");
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler);
        arrayList = new SqlLiteDataBase(this).GetAllCarts();
        if (arrayList.size()>0){
            adapterCart = new AdapterCart(this,arrayList);
            recyclerView.setAdapter(adapterCart);
        }
        else {
            Toast.makeText(this, "لاتوجد عناصر", Toast.LENGTH_SHORT).show();
        }
    }
}