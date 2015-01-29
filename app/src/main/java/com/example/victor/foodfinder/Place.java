package com.example.victor.foodfinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Victor on 1/28/2015.
 */
public class Place {
    private String id;
    private String icon;
    private String name;
    private String category;
    private String open;
    private String vicinity;

    private Double latitude;
    private Double longitude;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }
    public String getIcon(){
        return icon;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getOpenNow(){
        return open;
    }
    public void setOpenNow(String open){
        this.open = open;
    }
    public String getVicinity(){
        return vicinity;
    }
    public void setVicinity(String vicinity){
        this.vicinity = vicinity;
    }
    public Double getLatitude(){
        return latitude;
    }
    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }
    public Double getLongitude(){
        return longitude;
    }
    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }


    static Place jsonToPontoReferencia(JSONObject pontoReferencia) {
        try {
            Place result = new Place();
            JSONObject geometry = (JSONObject) pontoReferencia.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            result.setLatitude((Double) location.get("lat"));
            result.setLongitude((Double) location.get("lng"));
            result.setIcon(pontoReferencia.getString("icon"));
            result.setName(pontoReferencia.getString("name"));
            result.setVicinity(pontoReferencia.getString("vicinity"));
            result.setId(pontoReferencia.getString("id"));
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", icon=" + icon + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }





}
