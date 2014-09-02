package edu.ucsb.geog;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class AttributeEntry extends JSONObject {
	private String author;
	private String ts;
	private JSONObject newObj;
	
	public AttributeEntry(JSONObject object){
        try {
        	// It has to have a timestamp
        	if (object.has("ts")) {
        		this.newObj = new JSONObject();
        		// It may or may not have an author.  Either way, make sure the author attribute exists
        		this.ts = object.getString("ts");
	        	if (object.has("author"))
	        		this.author = object.getString("author");
	        	else
	        		this.author = "";
        	
	        	this.newObj.put("author", this.author);
	        	this.newObj.put("ts", this.ts);
	        	
	        	Iterator<String> iter = object.keys();
	            while (iter.hasNext()) {
	                String key = iter.next().toLowerCase();
	                try {
	                	if (!key.equals("ts") && !key.equals("author")) {
	                		if (key.equals("d") || key.equals("desc") || key.equals("description")) {
	                			this.newObj.put("d",object.get(key));
	                		} else if (key.equals("check-in") || key.equals("checkin") || key.equals("chekin") || key.equals("checkingin") || key.equals("c")) {
	                			this.newObj.put("c",object.get(key));
	                		} else if (key.equals("l") || key.equals("link") || key.equals("links")) {
	                			// boolean fs = ((String) object.get(key)).toLowerCase().contains("foursquare");
	                			// boolean yelp = ((String) object.get(key)).toLowerCase().contains("yelp");
	                			this.newObj.put("l", object.get(key));
	                		} else {
	                			this.newObj.put(key,object.get(key));
	                		}
	                	}
	                    
	                } catch (JSONException e) {
	                    // Something went wrong!
	                }
	            }
        	
        	}
       } catch (JSONException e) {
            e.printStackTrace();
       }
    }
	public JSONObject getObject() {
		return this.newObj;
	}
}
