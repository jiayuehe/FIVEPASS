package com.example.angelahe.stepcounter.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.angelahe.stepcounter.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private static final String TAG = "MapsActivity";
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (true) {
            Log.d(TAG, "onMapReady: ready to add everything\n\n");

            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            // display my position
            mMap.setMyLocationEnabled(true);


        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: try to get location\n\n");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (true) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                            findGym(currentLocation);
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: Security Exception: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//        MarkerOptions options = new MarkerOptions().position(latLng);
//        mMap.addMarker(options);
    }

    private void findGym(Location currentLocation){
        Log.d(TAG, "\n\n\ngeoLocate: geolocating\n");
        String searchString = "gym";
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                +currentLocation.getLatitude()+","+currentLocation.getLongitude()
                +"&radius=1000&type=gym&key=AIzaSyAcIICLHNIOJn0vEl8eBzKW9_ZWlCetd4Y";

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " +response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject!=null){
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                for(int i = 0; i < Math.min(jsonArray.length(),5); i++){
                                    JSONObject cur = jsonArray.getJSONObject(i);
                                    JSONObject loc = cur.getJSONObject("geometry").getJSONObject("location");
                                    Double lat = loc.getDouble("lat");
                                    Double lng = loc.getDouble("lng");
                                    String name = cur.getString("name");
                                    Log.e(TAG, "onResponse: "+"found a gym: "+lat);
                                    LatLng latLng = new LatLng(lat, lng);
                                    MarkerOptions options = new MarkerOptions().position(latLng).title(name);
                                    mMap.addMarker(options);
                                }
                            }
                        } catch (Exception e){
                            Log.e(TAG, "onResponse: error in parsing to json: " +e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

//        Geocoder geocoder = new Geocoder(this);
//        List<Address> list = new ArrayList<>();
//        try{
//            list = geocoder.getFromLocationName(searchString, 3);
//            Log.e(TAG, "geoLocate: "+list );
//        } catch (IOException e){
//            Log.e(TAG, "geoLocate: IOException: "+ e.getMessage());
//        }
//        if(list!=null&&list.size()>0){
//            for(Address address: list){
//                Log.d(TAG, "geoLocate: found a new gym at: "+address.getLatitude()+", "+address.getLongitude());
//                // mark the place we found
//                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                MarkerOptions options = new MarkerOptions().position(latLng).title(address.getAddressLine(0));
//                mMap.addMarker(options);
//            }
//        }
    }




}
