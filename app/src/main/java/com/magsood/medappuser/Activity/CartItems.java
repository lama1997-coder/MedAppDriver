package com.magsood.medappuser.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.magsood.medappuser.Adapter.AdapterCart;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.SqlLiteDataBase;
import com.magsood.medappuser.Utils.ToolbarClass;

import java.util.ArrayList;

public class CartItems extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterCart adapterCart;
    ArrayList<ModelCart> arrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);
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
        recyclerView = findViewById(R.id.recycler);
        arrayList = new SqlLiteDataBase(this).GetAllCarts();
        if (arrayList.size()>0){
            adapterCart = new AdapterCart(this,arrayList);
            recyclerView.setAdapter(adapterCart);
        }
        else {
            findViewById(R.id.empty).setVisibility(View.VISIBLE);
            findViewById(R.id.empty_text).setVisibility(View.VISIBLE);
            Toast.makeText(this, "لاتوجد عناصر", Toast.LENGTH_SHORT).show();
        }
    }
}