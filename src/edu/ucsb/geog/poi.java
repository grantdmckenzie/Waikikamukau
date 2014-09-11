package edu.ucsb.geog;


public class poi implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	String w_id;
	int w_id_int;
    String name;
    int cat;
    float lat;
    float lng;
    float distance;
    String uri;

    public int getW_id_int() {
        return w_id_int;
    }
    private void setW_id_int(int id) {
        this.w_id_int = id;
    }
    public String getW_id() {
        return w_id;
    }
    private void setW_id(String id) {
        this.w_id = id;
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
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
}
