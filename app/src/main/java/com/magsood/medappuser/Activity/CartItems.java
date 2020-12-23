package com.magsood.medappuser.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.magsood.medappuser.Adapter.AdapterCart;
import com.magsood.medappuser.Adapter.DialogAdapter;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.RequestService;
import com.magsood.medappuser.SharedPrefrense.UserPreferences;
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

    UserPreferences userPreferences;

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


                final AlertDialog.Builder builder = new AlertDialog.Builder(CartItems.this);
                final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
                final String message = "المتابعه بالموقع الحالي؟";
//        builder.setIcon(R.drawable.ic_gps);

                builder.setMessage(message)
                        .setPositiveButton("متابعة",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {


                                      lastDialog();
                                    }
                                })
                        .setNegativeButton("تحديد على الخريطة",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {

                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        intent.putExtra("changeLocation","change");
                                        startActivity(intent);


                                    }
                                });
                builder.create().show();


            }
        });

    }


public void send(){

    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = null;
    try {
        for (int i =0 ; i<arrayList.size();i++){
            ModelCart item = arrayList.get(i);

            jsonObject = new JSONObject();
            jsonObject.put("amount",item.getAmount());
            jsonObject.put("medicineID",item.getMedicineID());

            jsonObject.put("dropLng",userPreferences.getlong());

            jsonObject.put("dropLat",userPreferences.getlat());
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
    public void lastDialog(){


        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialog_last);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        Button send = dialog.findViewById(R.id.send);
        Button close = dialog.findViewById(R.id.cancele);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent = new Intent(getApplicationContext(),CartItems.class);
              startActivity(intent);
            }
        });

        RecyclerView rvTest = (RecyclerView) dialog.findViewById(R.id.recycle_last_dialog);
        rvTest.setHasFixedSize(true);
        rvTest.setLayoutManager(new LinearLayoutManager(context));
        arrayList = new SqlLiteDataBase(this).GetAllCarts();
        if (arrayList.size()==0){
            dialog.dismiss();
        }
        DialogAdapter rvAdapter = new DialogAdapter(this, arrayList,dialog,adapterCart);
        rvTest.setAdapter(rvAdapter);
    }


    public static void GpsDialog(final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "المتابعه بالموقع الحالي؟";
//        builder.setIcon(R.drawable.ic_gps);

        builder.setMessage(message)
                .setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("رجوع",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }


    private void init() {
        userPreferences = new UserPreferences(this);
        sendOrder = findViewById(R.id.btnOrder);
        recyclerView = findViewById(R.id.recycler);
        arrayList = new SqlLiteDataBase(this).GetAllCarts();
        if (arrayList.size()>0){
            adapterCart = new AdapterCart(this,arrayList);
            recyclerView.setAdapter(adapterCart);
            adapterCart.notifyDataSetChanged();
        }
        else {
            findViewById(R.id.empty).setVisibility(View.VISIBLE);
            findViewById(R.id.empty_text).setVisibility(View.VISIBLE);
            Toast.makeText(this, "لاتوجد عناصر", Toast.LENGTH_SHORT).show();
            findViewById(R.id.btnOrder).setVisibility(View.GONE); }
    }
}