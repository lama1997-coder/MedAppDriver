package com.magsood.medappuser.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.magsood.medappuser.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.magsood.medappuser.Service.Logout;
import com.magsood.medappuser.Service.SearchService;
import com.magsood.medappuser.SharedPrefrense.UserPreferences;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback , GoogleMap.OnMapClickListener {
    private GoogleMap mMap;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView name;
    AutoCompleteTextView search ;
    SearchService searchService;
    ImageView recordIcon;
    private static final int REQUEST_CODE = 1234;
    UserPreferences userPreferences;

    CardView cardSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nav_drawer);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




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
        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
        intent.putExtra("searchStr",search.getText().toString());
        startActivity(intent);
//        searchService = new SearchService();
//        searchService.search(this,search.getText().toString());
    }
    private void init() {
        userPreferences = new UserPreferences(this);
        recordIcon= findViewById(R.id.ic_search);
        search = findViewById(R.id.editsearch);

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });
//        cardSearch = findViewById(R.id.cardSearch);
//        cardSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,SearchActivity.class));
//            }
//        });

        recordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognition();
            }
        });
        UserPreferences userPreferences= new UserPreferences(this);
Log.e("logOut",userPreferences.getUserId());
        if(userPreferences.getUserId().equals("")){


           hideItem();

        }
        else {

            ((TextView)findViewById(R.id.fullName)).setText(userPreferences.getUserName());



            NavigationView navigationView = (NavigationView) findViewById(R.id.n_view);
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.name);
            navUsername.setText(userPreferences.getUserName());

            hideItemReg();
        }
        toolbar = findViewById(R.id.public_toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
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
//            case R.id.nav_menu_balance: {
//                Toast.makeText(this, "محتاجه تحليل", Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(MainActivity.this,NewRequestDetails.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_menu_my_order: {
////                Toast.makeText(this, "محتاجه تحليل", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this,MyOrder.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
            case R.id.nav_menu_logout:{

                Logout logout = new Logout();
                logout.logOut(MainActivity.this);
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
//            case R.id.nav_menu_sc3: {
//                startActivity(new Intent(MainActivity.this,PinCode.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
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
//            case R.id.nav_menu_sc6: {
//                startActivity(new Intent(MainActivity.this,MapsActivity.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_menu_sc7: {
//                startActivity(new Intent(MainActivity.this,PaymentsActivity.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
        }
        return true;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (isLocationEnabled(getApplicationContext())) {
            if (hasPermissions(Manifest.permission.ACCESS_FINE_LOCATION)) {
//                UpdateMarkers();
                getMyLocation();
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }


    FusedLocationProviderClient fusedLocationProviderClient;
    public void getMyLocation() {
        if (isLocationEnabled(this)) {
            if (hasPermissions(Manifest.permission.ACCESS_FINE_LOCATION)) {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                FetchLastLocation();
            }
        } else {
            GpsDialog(this);
        }
    }


    public static void GpsDialog(final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "تفعيل الموقع GPS";
//        builder.setIcon(R.drawable.ic_gps);

        builder.setMessage(message)
                .setPositiveButton("تفعيل",
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

    public boolean hasPermissions(String permission) {

        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permission}, 1);
            return false;

        } else {
            return true;
        }

    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    private boolean GPSdevice() {
        boolean z;
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }
    FloatingActionButton floatingActionButton;

    Location mylocation;
    private void FetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mylocation = location;
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mylocation.getLatitude(), mylocation.getLongitude()), 14));

                    userPreferences.setlon(String.valueOf(mylocation.getLongitude()));
                    userPreferences.setlat(String.valueOf(mylocation.getLatitude()));


                    Log.e("locationCurrent",String.valueOf(mylocation.getLatitude()) +"_____"+String.valueOf(mylocation.getLatitude()));

                }
            }
        });
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

    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.n_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_menu_setting).setVisible(false);
//        nav_Menu.findItem(R.id.nav_menu_my_order).setVisible(false);
        nav_Menu.findItem(R.id.nav_menu_logout).setVisible(false);
//        nav_Menu.findItem(R.id.nav_menu_sc4).setVisible(false);
        nav_Menu.findItem(R.id.nav_menu_sc5).setVisible(false);
//        nav_Menu.findItem(R.id.nav_menu_sc7).setVisible(false);

    }
    MarkerOptions options;
    Marker marker;
    @Override
    public void onMapClick(LatLng latLng) {






        if(marker!=null)
            marker.remove();

        mMap.clear();

         options=new MarkerOptions().snippet("Location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_location))
                .anchor(0.5f, 0.5f);
         Log.e("locationPin",String.valueOf(latLng.latitude) +"_____"+String.valueOf(latLng.longitude));
         userPreferences.setlat(String.valueOf(latLng.latitude));
         userPreferences.setlon(String.valueOf(latLng.longitude));

        options.position(latLng);
       marker= mMap.addMarker(options);
        if(getIntent().getStringExtra("changeLocation")!=null){

           LocationOk(this);

        }

    }


    public static void LocationOk(final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "تاكيد موفع الاستلام";
//        builder.setIcon(R.drawable.ic_gps);

        builder.setMessage(message)
                .setPositiveButton("تاكيد",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                            Intent intent = new Intent(activity,CartItems.class);
                            activity.startActivity(intent);
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
}