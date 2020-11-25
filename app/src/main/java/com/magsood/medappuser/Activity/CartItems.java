package com.magsood.medappuser.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.magsood.medappuser.Adapter.AdapterCart;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.RequestService;
import com.magsood.medappuser.Utils.SqlLiteDataBase;
import com.magsood.medappuser.Utils.ToolbarClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartItems extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterCart adapterCart;
    ArrayList<ModelCart> arrayList;
    AppCompatButton sendOrder;
    Context context = this;



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

        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = null;
                try {
                for (int i =0 ; i<arrayList.size();i++){
                    ModelCart item = arrayList.get(i);

                    jsonObject = new JSONObject();
                        jsonObject.put("amount",item.getAmount());
                        jsonObject.put("medicineID",item.getMedicineID());

                        jsonObject.put("dropLng",item.getPharmacyLong());

                        jsonObject.put("dropLat",item.getPharmacyLat());
                        jsonArray.put(jsonObject);



                }

                JSONObject finalobject = new JSONObject();
                finalobject.put("orders", jsonArray);
                Log.e("response",finalobject.toString());

                    RequestService requestService = new RequestService();
                    requestService.sendRequest((Activity) context, finalobject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void init() {
        sendOrder = findViewById(R.id.btnOrder);
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
            findViewById(R.id.btnOrder).setVisibility(View.GONE); }
    }
}