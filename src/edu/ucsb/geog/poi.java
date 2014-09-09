package edu.ucsb.geog;


public class poi implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	String id;
    String name;
    int cat;
    float lat;
    float lng;
    float distance;
    String uri;
    String provider;

   public String getId() {
        return id;
    }
    private void setId(String id) {
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
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getProvider() {
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }
}
