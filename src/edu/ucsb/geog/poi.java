package edu.ucsb.geog;

import java.math.BigInteger;

public class poi implements java.io.Serializable {
	BigInteger id;
    String name;
    int cat;
    float lat;
    float lng;
    float distance;

   public BigInteger getId() {
        return id;
    }
    private void setId(BigInteger id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCat() {
        return cat;
    }
    public void setCat(int cat) {
        this.cat = cat;
    }
    public float getLat() {
        return lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public float getLng() {
        return lng;
    }
    public void setLng(float lng) {
        this.lng = lng;
    }
    public float getDistance() {
        return distance;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }
}
