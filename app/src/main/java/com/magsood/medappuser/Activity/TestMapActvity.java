package com.magsood.medappuser.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.magsood.medappuser.GMapV2Direction;
import com.magsood.medappuser.Model.ModelCart;
import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.SqlLiteDataBase;

import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TestMapActvity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    TextView nameofpharmacy;
    MarkerOptions origin, destination;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Marker currLocationMarker;
    String url,lat,lon,nameofpharmacyi;
    LatLng latLng;
    DownloadTask downloadTask;
    Boolean isCameraSet =false;
    LinearLayout back;
    final Bundle bundle = new Bundle();
    double longitudeCurrentLocation;
    double latitudeCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lat  = getIntent().getExtras() != null && getIntent().getExtras().getString("dropLat") != null ? getIntent().getExtras().getString("dropLat") : "";
        lon  = getIntent().getExtras() != null && getIntent().getExtras().getString("dropLng") != null ? getIntent().getExtras().getString("dropLng") : "";
        // nameofpharmacyi  = getIntent().getExtras() != null && getIntent().getExtras().getString("nameofpharmacy") != null ? getIntent().getExtras().getString("nameofpharmacy") : "";
        // Toast.makeText(this,lat+lon,Toast.LENGTH_LONG).show();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // buildAlertMessageNoGps();

        ((LinearLayout) (findViewById(R.id.ic_back))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Setting marker to draw route between these two points
        // Start downloading json data from Google Directions API
        // downloadTask.execute(url);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //buildAlertMessageNoGps();
            return;
        }

        mMap.setMyLocationEnabled(true);

        buildGoogleApiClient();

        mGoogleApiClient.connect();
        // mMap.addMarker(destination);

        /*
        mMap.addMarker(origin);
        mMap.addMarker(destination);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin.getPosition(), 10));*/
    }
    //////////////////
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /////////////////////
//    @SuppressLint("StaticFieldLeak")
//    public class DownloadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... url) {
//
//            String data = "";
//
//            try {
//                data = downloadUrl(url[0]);
//            } catch (Exception e) {
//                Log.d("Background Task", e.toString());
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            ParserTask parserTask = new ParserTask();
//            parserTask.execute(result);
//        }
//    }

    @SuppressLint("StaticFieldLeak")
    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = null;

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.e("urlResult",result.toString());

            GMapV2Direction md = new GMapV2Direction();
            ArrayList<LatLng> directionPoint = md.getDirection(result);
            PolylineOptions rectLine = new PolylineOptions().width(15).color(
                    Color.rgb(0,179,253));

            for (int i = 0; i < directionPoint.size(); i++) {
                rectLine.add(directionPoint.get(i));
            }
            Polyline polylin = mMap.addPolyline(rectLine);



        }
    }




    /**
     * A class to parse the JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
//                DirectionsJSONParser parser = new DirectionsJSONParser();
//
//                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = new ArrayList();
            PolylineOptions lineOptions = new PolylineOptions();

//            for (int i = 0; i < result.size(); i++) {
//
//                List<HashMap<String, String>> path = result.get(i);
//
//                for (int j = 0; j < path.size(); j++) {
//                    HashMap<String, String> point = path.get(j);
//
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//                    LatLng position = new LatLng(lat, lng);
//
//                    points.add(position);
//                }
//
//                lineOptions.addAll(points);
//                lineOptions.width(12);
//                lineOptions.color(Color.RED);
//                lineOptions.geodesic(true);
//
//            }
            points.add(latLng);
            points.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));

            lineOptions.addAll(points);
            lineOptions.width(12);
            lineOptions.color(Color.RED);
            lineOptions.geodesic(true);
            // Drawing polyline in the Google Map
            if (points.size() != 0)
                mMap.addPolyline(lineOptions);
            else
                Toast.makeText(getApplicationContext(), "pp", Toast.LENGTH_LONG).show();

        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String sensor = "sensor=false";

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        //setting transportation mode
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "xml";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyCx0Vmlspyg8IYS2GG4rhF-p_iqzhokcnc";

        return url;

    }

    /**
     * A method to download json data from url
     */
//    private String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try {
//            URL url = new URL(strUrl);
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            urlConnection.connect();
//
//            iStream = urlConnection.getInputStream();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            data = sb.toString();
//
//            br.close();
//
//        } catch (Exception e) {
//            Log.d("Exception", e.toString());
//        } finally {
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }



    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {



//            HttpClient httpClient = new DefaultHttpClient();
//url =  url.replaceAll("[^\\x20-\\x7e]", "");
//            Log.d("urlInGMAP", url);
//            HttpContext localContext = new BasicHttpContext();
//            Log.d("urlInGMAP", localContext.toString());
//            HttpPost httpPost = new HttpPost(url);
//            Log.d("urlInGMAP", httpPost.toString());
//
//
//            HttpResponse response = httpClient.execute(httpPost, localContext);
//            Log.d("urlInGMAP", httpPost.toString());
//            InputStream in = response.getEntity().getContent();
//
//            Log.d("urlInGMAP", httpPost.toString());
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
//


          //  ByteArrayInputStream encXML = new ByteArrayInputStream(.getBytes("UTF8"));
            DocumentBuilderFactory domFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = domFact.newDocumentBuilder();
            Log.d("urlInGMAP", String.valueOf(iStream.read()));
           // Document doc = builder.parse(data);



//            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document document = builder.parse(in);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("urlInGMAP", String.valueOf(e));
        } finally {
        iStream.close();
        urlConnection.disconnect();
    }
        return null;
    }

    ///////////////////////////////////////
    protected synchronized void buildGoogleApiClient() {
        // Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mMap.addMarker(markerOptions);

        }
        else
        {

            final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                buildAlertMessageNoGps();
            }
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);



    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCurrentLocation() {
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);

            return;
        }
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        Location location;
//        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                // Logic to handle location object

                longitudeCurrentLocation = location.getLongitude();
                latitudeCurrentLocation = location.getLatitude();


            } else {



            }
        });
//
    }





    @Override
    public void onLocationChanged(Location location) {

        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mMap.addMarker(markerOptions);

        // Toast.makeText(this,latLng+"Location Changed",Toast.LENGTH_SHORT).show();

        //zoom to current position:
        if(!isCameraSet){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
        isCameraSet=true;}
        origin = new MarkerOptions().position(new LatLng(latitudeCurrentLocation, longitudeCurrentLocation)).title("").snippet("origin");
        destination = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon))).title("").snippet("destination");
        /////////////////////////////////////////////////////////////
        mMap.addMarker(destination);
        LatLng des= new LatLng(latitudeCurrentLocation,longitudeCurrentLocation);
        /////////////////////////////////////////////////////////
        // Getting URL to the Google Directions API
        url = getDirectionsUrl(latLng, destination.getPosition());
        url = getDirectionsUrl(latLng,destination.getPosition() );
        Log.d("urlInGMAP", url);


//
        downloadTask = new DownloadTask();
        downloadTask.execute(url);



        GMapV2Direction md = new GMapV2Direction();


//
//
//        Document  doc = md.getDocument(latLng, destination.getPosition(),
//                    GMapV2Direction.MODE_DRIVING);
//
//        Log.d("MapUrl", String.valueOf(doc)+"not null");
//        ArrayList<LatLng> directionPoint = md.getDirection(doc);
//        PolylineOptions rectLine = new PolylineOptions().width(3).color(
//                Color.RED);
//
//        for (int i = 0; i < directionPoint.size(); i++) {
//            rectLine.add(directionPoint.get(i));
//        }
//        Polyline polylin = mMap.addPolyline(rectLine);


    }

    public void addCart(View view) {



        Log.e("debugApp","1");
        //   startActivity(new Intent(MapsActivity.this,SendRequest.class));
        Bundle bundle = getIntent().getExtras();
        Log.e("debugApp","1");
        ModelCart modelCart = new ModelCart();
        Log.e("debugApp","1");
        modelCart.setId(bundle.getString("id"));
        Log.e("debugApp","1");
        modelCart.setName(bundle.getString("tradeName"));
        Log.e("debugApp","1");
        modelCart.setPharmacyAddress(bundle.getString("location"));
        Log.e("debugApp","1");
        modelCart.setPharmacyName(bundle.getString("pharmacyName"));
        Log.e("debugApp","1");
        modelCart.setPharmacyID(bundle.getString("pharmacyID"));
        Log.e("debugApp","1");
        modelCart.setMedicineID(bundle.getString("medicineID"));
        Log.e("debugApp","1");
        modelCart.setPrice(bundle.getString("price"));
        Log.e("debugApp","1");
        modelCart.setPharmacyLat(bundle.getString("dropLat"));
        Log.e("debugApp","1");
        modelCart.setPharmacyLong(bundle.getString("dropLng"));
        Log.e("debugApp","1");
        //   Toast.makeText(activity,"قـــــريــــبـــا", Toast.LENGTH_SHORT).show();




        //add to cart code
        if (AddToCart(modelCart)){
            Toast.makeText(getApplicationContext(), "تم اضافة الدواء الى سلة الادوية", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "الدواء موجود مسبقا في سلة الادوية", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean AddToCart(ModelCart modelCart){
        return new SqlLiteDataBase(this).AddToCart(modelCart);
    }

    ///////////////////////////////////////////////////////
}