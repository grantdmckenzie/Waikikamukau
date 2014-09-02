package edu.ucsb.geog;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.mongodb.MongoClient;

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
    		BasicDBObject proj = new BasicDBObject("_id", 0);
    		proj.append("id", 0);
    		
    		DBCursor cursor = coll.find(query, proj);
    		
    		JSONArray pArray = new JSONArray();
            JSONObject pJson = new JSONObject();
    		
    		try {
    		    while (cursor.hasNext()) {
    		        // System.out.println(cursor.next());
    		        String entity = cursor.next().toString();
    		        
    		        try {
    		        	// Clean up the data coming out of the database.
						AttributeEntry d = new AttributeEntry(new JSONObject(entity));
						pArray.add(d.getObject());
					} catch (JSONException e) {
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
        	out.println("{\"response\":{\"status\":406, \"message\":\"Please provide an ID parameter.\"}}");
        }
		
	}
}
