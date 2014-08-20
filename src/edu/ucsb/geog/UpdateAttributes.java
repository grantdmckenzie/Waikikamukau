package edu.ucsb.geog;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

/**
 * Servlet implementation class UpdateAttributes
 */
@WebServlet("/UpdateAttributes")
public class UpdateAttributes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAttributes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}
	
	public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// Specify output headers (probably overkill)
    	res.setHeader("Content-Type", "application/json");
        res.setHeader("Content-Encoding", "UTF-8");
        res.setContentType("application/json");
        
        // A POI ID is essential
		if(req.getParameterMap().containsKey("id")) {
        	String id = String.valueOf(req.getParameter("id"));
        	String time = String.valueOf(new Date().getTime()/1000);
       	 	
        	// Loop over all params for key value pairs
    		Enumeration<String> parameterNames = req.getParameterNames();
    		Map<String, String> map = new HashMap<String, String>();
    		while (parameterNames.hasMoreElements()) {

    			String paramName = parameterNames.nextElement();
    			if (!paramName.equals("id")) {
	    			String[] paramValues = req.getParameterValues(paramName);
	    			map.put(paramName, paramValues[0]);
    			}
    		}
    		
    		// Make sure there is some data
    		if (map.size() > 0) {
    			// Set up MongoDB connection and collection
    			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        		DB db = mongoClient.getDB( "poibase" );
        		DBCollection coll = db.getCollection("development");
        		
        		// Create new Document (all documents should have an id and a timestamp at a minimum)
        		BasicDBObject doc = new BasicDBObject("id", id);
        		doc.append("ts", time);
        		
        		// Loop through all map entries
    			for (Map.Entry<String, String> entry : map.entrySet()) {
    				doc.append(entry.getKey(), entry.getValue());
    			}
    			try {
    				// Insert some wonderful data in to the NoSQL database
    				coll.insert(doc, WriteConcern.ACKNOWLEDGED);
    	        } catch (MongoException e) {
    	            e.printStackTrace();
    	        }
    			
    		} else {
    			PrintWriter out = res.getWriter();
            	out.println("{\"response\":{\"status\":406, \"message\":\"Please provide an additional attribute (other than ID).\"}}");
    		}

        } else {
        	PrintWriter out = res.getWriter();
        	out.println("{\"response\":{\"status\":406, \"message\":\"Please provide an ID parameter and at least one other attribute.\"}}");
        }
		
	}
}
