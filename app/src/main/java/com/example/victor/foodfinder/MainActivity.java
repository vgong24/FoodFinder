package com.example.victor.foodfinder;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity {

    private LatLng VicPos = new LatLng(21.488, -157.72);
    private double latitude;
    private double longitude;

    private GoogleMap googleMap;

    Button btnShowLocation;

    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowLocation = (Button) findViewById(R.id.show_location);

        //Initialize current location
        gps = new GPSTracker(MainActivity.this);
        if(gps.canGetLocation){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            VicPos = new LatLng(latitude,longitude);
        }

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gps = new GPSTracker(MainActivity.this);

                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    VicPos = new LatLng(latitude,longitude);

                    Toast.makeText(getApplicationContext(), "Your location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
                }else{
                    gps.showSettingsAlert();
                }
            }
        });


        try{
            if(googleMap == null){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            googleMap.setMyLocationEnabled(true);

            googleMap.setTrafficEnabled(true);

            googleMap.getUiSettings().setZoomControlsEnabled(true);

            Marker marker = googleMap.addMarker(new MarkerOptions().position(VicPos).title("Lat: "+ latitude + "\nLong: "+ longitude));




        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
