package com.example.victor.foodfinder;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback {

    private LatLng VicPos = new LatLng(0, 0);
    private double latitude;
    private double longitude;
    private String places = "Restaurants";

    private GoogleMap googleMap;

    Button btnShowLocation;

    GPSTracker gps;
    MapFragment mapFragment;

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
            if(mapFragment == null){
                /*
                New code 1/28
                 */

                mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            googleMap.setMyLocationEnabled(true);

            googleMap.setTrafficEnabled(true);

            googleMap.getUiSettings().setZoomControlsEnabled(true);

            //Puts marker on current location
            Marker marker = googleMap.addMarker(new MarkerOptions().position(VicPos).title("Lat: "+ latitude + "\nLong: "+ longitude));

            /**
             * Put restaurant location markers using PlacesService
             */


        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    /**New code 1/28 =============================================
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 16
        ));

        googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fd_icon))
                        .anchor(0.0f, 1.0f)
                        .position(new LatLng(latitude, longitude))
                //put the lat and long of restaraunts there^^
        );
    }
    ///=======================================================


    //New code source: http://karnshah8890.blogspot.com/2013/03/google-places-api-tutorial.html
    private abstract class GetPlaces extends AsyncTask{

        protected  void onPostExecute(ArrayList<Place> result){
            super.onPostExecute(result);
            for(int i = 0; i <  result.size(); i++){
                googleMap.addMarker(new MarkerOptions()
                .title(result.get(i).getName())
                .position(
                        new LatLng(result.get(i).getLatitude(), result.get(i).getLongitude()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fd_icon))
                .snippet(result.get(i).getVicinity()));
            }
        }




        protected ArrayList<Place> doInBackground(Void... arg0) {
            PlacesService service = new PlacesService(
                    "AIzaSyBX9FJxs9EVhIMWmcNoS0eBCatq0rFYy1E"
            );
            ArrayList<Place> findPlaces = service.findPlaces(latitude, longitude, places);
            for(int i = 0; i < findPlaces.size(); i++){
                Place placeDetail = findPlaces.get(i);
               return findPlaces;
            }

            return null;
        }
    }
    //===============================================================================================
    public void initCompo(){

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
