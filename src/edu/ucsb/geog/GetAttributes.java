package edu.ucsb.geog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

/**
 * Servlet implementation class GetAttributes
 */
@WebServlet("/GetAttributes")
public class GetAttributes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAttributes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			handleRequest(request, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			handleRequest(request, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, JSONException {
		res.setHeader("Content-Type", "application/json");
        res.setHeader("Content-Encoding", "UTF-8");
        res.setContentType("application/json");
        
		if(req.getParameterMap().containsKey("id")) {
        	String id = String.valueOf(req.getParameter("id"));
        
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    		DB db = mongoClient.getDB( "poibase" );
    		DBCollection coll = db.getCollection("development");
    		
    		BasicDBObject query = new BasicDBObject("id", id);
    		DBCursor cursor = coll.find(query);
    		
    		JSONArray pArray = new JSONArray();
            JSONObject pJson = new JSONObject();
    		
    		try {
    		    while (cursor.hasNext()) {
    		        // System.out.println(cursor.next());
    		        String entity = cursor.next().toString();
    		        
    		        try {
						JSONObject d = new JSONObject(entity);
						Iterator<?> keys = d.keys();
				        while( keys.hasNext() ){
				            String key = (String)keys.next();
				            if (!key.equals("_id") && !key.equals("id")) {
				            	String val = (String)d.get(key);
				            	// System.out.println(key+" "+val);
				            	JSONObject obj = new JSONObject();
				            	obj.put(key, val);
				            	pArray.add(obj);
				            }
				        }
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		    }
    		    pJson.put("response", pArray);
    		    PrintWriter done = res.getWriter();
    	        done.print(pJson);
    	        done.flush();
    		} finally {
    		    cursor.close();	
    		    
    		}
        		
        } else {
        	PrintWriter out = res.getWriter();
        	out.println("[{\"Error\":\"Please provide an ID parameter\"}]");
        }
		
	}
}