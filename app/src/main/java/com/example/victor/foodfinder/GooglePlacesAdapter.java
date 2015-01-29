package com.example.victor.foodfinder;

import android.os.AsyncTask;
import android.view.View;

/**
 * Created by Victor on 1/28/2015.
 */
public class GooglePlacesAdapter extends AsyncTask {
    String temp;
    String lat;
    String lng;

    public GooglePlacesAdapter(String latitude, String longitutde){
        lat = latitude;
        lng = longitutde;
    }


    @Override
    protected Object doInBackground(Object[] param) {
        // make Call to the url
        //temp = makeCall("https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude + "," + longtitude + "&radius=100&sensor=true&key=" + GOOGLE_KEY);

        return null;
    }

    public static String makeCall(String url){
        StringBuffer buffer_string = new StringBuffer(url);
        String replyString = "";
        return "";

    }
}
