package com.magsood.medappuser.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.magsood.medappuser.GMapV2Direction;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.RequestService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.magsood.medappuser.Utils.SqlLiteDataBase;
import com.squareup.okhttp.internal.framed.FrameReader;

import org.joda.time.DateTime;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback ,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        View.OnClickListener{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private double longitude;
    private double latitude;
    private static final String TAG = "MapsActivity";
    private FusedLocationProviderClient fusedLocationClient;
    AppCompatButton btnAddToCart;
    Marker currLocationMarker;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = getFusedLocationProviderClient(this);




        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
//        ( (AppCompatButton)findViewById(R.id.btnAddToCart)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                showDialog();
//                Log.e("debugApp","1");
//                //   startActivity(new Intent(MapsActivity.this,SendRequest.class));
//                Bundle bundle = getIntent().getExtras();
//                Log.e("debugApp","1");
//                ModelCart modelCart = new ModelCart();
//                Log.e("debugApp","1");
//                modelCart.setId(bundle.getString("id"));
//                Log.e("debugApp","1");
//                modelCart.setName(bundle.getString("tradeName"));
//                Log.e("debugApp","1");
//                modelCart.setPharmacyAddress(bundle.getString("location"));
//                Log.e("debugApp","1");
//                modelCart.setPharmacyName(bundle.getString("pharmacyName"));
//                Log.e("debugApp","1");
//                modelCart.setPharmacyID(bundle.getString("pharmacyID"));
//                Log.e("debugApp","1");
//                modelCart.setMedicineID(bundle.getString("medicineID"));
//                Log.e("debugApp","1");
//                modelCart.setPrice(bundle.getString("price"));
//                Log.e("debugApp","1");
//                modelCart.setPharmacyLat(bundle.getString("dropLat"));
//                Log.e("debugApp","1");
//                modelCart.setPharmacyLong(bundle.getString("dropLng"));
//                Log.e("debugApp","1");
//                //   Toast.makeText(activity,"قـــــريــــبـــا", Toast.LENGTH_SHORT).show();
//
//
//
//
//                //add to cart code
//                if (AddToCart(modelCart)){
//                    Toast.makeText(getApplicationContext(), "تم اضافة الدواء الى سلة الادوية", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getApplicationContext(), "الدواء موجود مسبقا في سلة الادوية", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
        ((LinearLayout) (findViewById(R.id.ic_back))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }


    private void init() {




    }



    private boolean AddToCart(ModelCart modelCart){
        return new SqlLiteDataBase(this).AddToCart(modelCart);
    }

//    private void sendReq() {
//
//
//        Bundle bundle = getIntent().getExtras();
//        String medicineID = bundle.getString("medicineID");
//        String amount = bundle.getString("amount");
//        String lang = bundle.getString("dropLng");
//        String lat = bundle.getString("dropLat");
//        RequestService requestService = new RequestService();
//        requestService.sendRequest(this, medicineID, amount, lang, lat);
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String lang = bundle.getString("dropLng");
            String lat = bundle.getString("dropLat");
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(Double.parseDouble(lang), Double.parseDouble(lat));
            currLocationMarker=mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Pharmacy"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
        } else {
//            LatLng sydney = new LatLng(100f, 100f);
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Pharmacy"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));



            getCurrentLocation();}




//        int zoom = (int)mMap.getCameraPosition().zoom;
//        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new
//                LatLng(15.597544 + (double)90/Math.pow(2, zoom),
//                arg0.getPosition().longitude), zoom);
//        mMap.animateCamera(cu,500,null);


    }
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @SuppressLint("NewApi")
    private void getCurrentLocation() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);



        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
            Log.e(TAG,"i stopp in premission");
            return;
        }
        FusedLocationProviderClient fusedLocationClient = getFusedLocationProviderClient(this);
//        Location location;
//        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                // Logic to handle location object

                longitude = location.getLongitude();
                latitude = location.getLatitude();


                //moving the map to location
                moveMap();
            } else {



            }
        });

        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());


//
    }


    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());



        longitude = location.getLongitude();
        latitude = location.getLatitude();


        //moving the map to location
        moveMap();
    }
    private void moveMap() {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        LatLng latLng = new LatLng(latitude, longitude);
        currLocationMarker=    mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Marker in your location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setZoomControlsEnabled(true);


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
    @Override
    public void onClick(View view) {
        Log.v(TAG,"view click event");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // mMap.clear();
        currLocationMarker=    mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Toast.makeText(MapsActivity.this, "onMarkerDragStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Toast.makeText(MapsActivity.this, "onMarkerDrag", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        // getting the Co-ordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //move to current position
        moveMap();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(MapsActivity.this, "onMarkerClick", Toast.LENGTH_SHORT).show();
        return true;
    }
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                // Permission Denied
                Toast.makeText(this, "your message", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
//code for directions
private GeoApiContext getGeoContext() {
    GeoApiContext geoApiContext = new GeoApiContext();
    return geoApiContext.setQueryRateLimit(3)
            .setApiKey(getString(R.string.google_maps_key))
            .setConnectTimeout(1, TimeUnit.SECONDS)
            .setReadTimeout(1, TimeUnit.SECONDS)
            .setWriteTimeout(1, TimeUnit.SECONDS);
}



public void direction(){

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
        String lang = bundle.getString("dropLng");
        String lat = bundle.getString("dropLat");


        LatLng origin = new LatLng(longitude, latitude);
        LatLng destination = new LatLng(Double.parseDouble(lang), Double.parseDouble(lat));
        DateTime now = new DateTime();
        try {
            DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING).origin(String.valueOf(origin))
                    .destination(String.valueOf(destination)).departureTime(now)
                    .await();
            Log.e("responseMap",result.toString());
            addMarkersToMap(result, mMap);
            addPolyline(result, mMap);

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


//    protected void route(LatLng sourcePosition, LatLng destPosition, String mode) {
//        GMapV2Direction md = new GMapV2Direction();
//        Document doc = md.getDocument(sourcePosition, destPosition,
//                GMapV2Direction.MODE_DRIVING);
//
//        ArrayList<LatLng> directionPoint = md.getDirection(doc);
//        PolylineOptions rectLine = new PolylineOptions().width(3).color(
//                Color.RED);
//
//        for (int i = 0; i < directionPoint.size(); i++) {
//            rectLine.add(directionPoint.get(i));
//        }
//        Polyline polylin = mMap.addPolyline(rectLine);
//    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {

        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String lang = bundle.getString("dropLng");
            String lat = bundle.getString("dropLat");

            LatLng origin = new LatLng(longitude, latitude);
            LatLng destination = new LatLng(Double.parseDouble(lang), Double.parseDouble(lat));

            mMap.addMarker(new MarkerOptions().position(origin).title(results.routes[0].legs[0].startAddress));
            mMap.addMarker(new MarkerOptions().position(destination).title(results.routes[0].legs[0].startAddress).snippet(getEndLocationTitle(results)));
        }
    }

    private String getEndLocationTitle(DirectionsResult results){        return  "Time :"+ results.routes[0].legs[0].duration.humanReadable + " Distance :" + results.routes[0].legs[0].distance.humanReadable;    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) { List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }




    ///






}
