package com.example.budgettracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private Location mLocation;
    private String locationProvider;
    private GoogleMap mMap;
    public static final int PERMISSIONS_REQUEST = 0;
    public static final String LOCATION = "location";
    DBHelper DB;

    private LocationManager locationManager;
    private LocationListener locationListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DB = new DBHelper(this);
        //Add Code
        locationProvider = LocationManager.GPS_PROVIDER;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the location provider
                Log.d(TAG, "Location changed");
                Toast.makeText(getApplicationContext(), "Location changed, updating map...",
                        Toast.LENGTH_SHORT).show();
                //location
                mLocation = location;
                if (mMap != null) {
                    updateMap();
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };


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

        Log.d(TAG, "Map OK");
        mMap = googleMap;
        updateMap();

    }

    private void updateMap() {
        Log.d(TAG, "Updating map...");
        if (mMap != null) {
            mMap.clear();
            if (mLocation == null) {
                mLocation = new Location(locationProvider);
                mLocation.setLatitude(0);
                mLocation.setLongitude(0);
            }
            LatLng pin = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));
            mMap.addMarker(new MarkerOptions().position(pin).title("Current location"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        requestLocationUpdates();

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Remove the listener you previously added
        if(locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }

    }

    private void requestLocationUpdates() {
        // Check permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Permission already granted
            locationManager.requestLocationUpdates(
                    locationProvider, 1000, 1, locationListener);

        } else {

            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);

        }
    }


    @Override
    public void onRequestPermissionsResult(int request, String permissions[], int[] results) {
        switch (request) {
            case PERMISSIONS_REQUEST: {

                // If request is cancelled, the result arrays are empty
                if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted, yay! Do something useful
                    requestLocationUpdates();

                } else {

                    // Permission was denied, boo! Disable the
                    // functionality that depends on this permission
                    Toast.makeText(this, "Permission denied to access device's location", Toast.LENGTH_SHORT).show();

                }
                return;
            }
        }
    }
    public void saveexpens(View view) {







        Log.d(TAG, " Save Location " + mLocation.getLatitude() + mLocation.getLongitude() );

        String lon=String.valueOf(mLocation.getLatitude());
        String la=String.valueOf(mLocation.getLongitude());

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String amount =  i.getStringExtra("amount");
        String date = i.getStringExtra("date");
        String cat =  i.getStringExtra("cat");



        Double d=Double.parseDouble(amount);
      Boolean insert = DB.insertDataExpens(name,cat,date,d,lon,la);

        Log.d(TAG, " Saved Success" + insert );

       HomeActivity.redirectActivity(this, ViewExpens.class);



    }



}