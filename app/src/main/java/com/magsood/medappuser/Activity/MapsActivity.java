package com.magsood.medappuser.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.Projection;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.RequestService;
import com.magsood.medappuser.Utils.ToolbarClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ( (LinearLayout)(findViewById(R.id.ic_back))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() ,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showDialog();
             //   startActivity(new Intent(MapsActivity.this,SendRequest.class));
             sendReq();


            }
        });
    }

    private void sendReq() {


        Bundle bundle = getIntent().getExtras();
        String medicineID = bundle.getString("medicineID");
        String amount = bundle.getString("amount");
        String lang = bundle.getString("dropLng");
        String lat = bundle.getString("dropLat");
        RequestService requestService = new RequestService();
        requestService.sendRequest(this,medicineID,amount,lang,lat);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle = getIntent().getExtras();
if(bundle!=null) {
    String lang = bundle.getString("dropLng");
    String lat = bundle.getString("dropLat");
    // Add a marker in Sydney and move the camera
    LatLng sydney = new LatLng(Double.parseDouble(lang), Double.parseDouble(lat));
    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Pharmacy"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
}
else{
    LatLng sydney = new LatLng(100f, 100f);
    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Pharmacy"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));


}

//        int zoom = (int)mMap.getCameraPosition().zoom;
//        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new
//                LatLng(15.597544 + (double)90/Math.pow(2, zoom),
//                arg0.getPosition().longitude), zoom);
//        mMap.animateCamera(cu,500,null);


    }


    AppCompatButton btn;
    private void showDialog(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.dialog_deliev, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
            }
        });


        deleteDialog.show();
    }

}