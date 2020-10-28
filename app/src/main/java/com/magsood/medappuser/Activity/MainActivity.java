package com.magsood.medappuser.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.magsood.medappuser.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.magsood.medappuser.SharedPrefrense.UserPreferences;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private GoogleMap mMap;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    CardView cardSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void init() {
        cardSearch = findViewById(R.id.cardSearch);
        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });


        UserPreferences userPreferences= new UserPreferences(this);

        if(userPreferences.getUserId().equals("")){

           hideItem();

        }
        else {
            hideItemReg();
        }
        toolbar = findViewById(R.id.public_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.n_drawer);
        navigationView = findViewById(R.id.n_view);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void hideItemReg() {


        navigationView = (NavigationView) findViewById(R.id.n_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_menu_sc1).setVisible(false);
        nav_Menu.findItem(R.id.nav_menu_sc2).setVisible(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //nav_btn
            case R.id.nav_menu_balance: {
                Toast.makeText(this, "محتاجه تحليل", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this,NewRequestDetails.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_my_order: {
//                Toast.makeText(this, "محتاجه تحليل", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,MyOrder.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_setting: {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_sc1: {
                startActivity(new Intent(MainActivity.this,Login.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_sc2: {
                startActivity(new Intent(MainActivity.this,Register.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_sc3: {
                startActivity(new Intent(MainActivity.this,PinCode.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_sc4: {
                startActivity(new Intent(MainActivity.this,OnProgressRequest.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_sc5: {
                startActivity(new Intent(MainActivity.this,CartItems.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_sc6: {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_sc7: {
                startActivity(new Intent(MainActivity.this,PaymentsActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }
        return true;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.n_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_menu_setting).setVisible(false);
        nav_Menu.findItem(R.id.nav_menu_my_order).setVisible(false);
        nav_Menu.findItem(R.id.nav_menu_balance).setVisible(false);
        nav_Menu.findItem(R.id.nav_menu_sc4).setVisible(false);
        nav_Menu.findItem(R.id.nav_menu_sc5).setVisible(false);
        nav_Menu.findItem(R.id.nav_menu_sc7).setVisible(false);

    }

}