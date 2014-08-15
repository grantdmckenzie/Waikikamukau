package edu.ucsb.geog;

import java.io.IOException;
import java.io.PrintWriter;
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
		// PrintWriter out = res.getWriter();
    	res.setHeader("Content-Type", "application/json");
        res.setHeader("Content-Encoding", "UTF-8");
        res.setContentType("application/json");
		if(req.getParameterMap().containsKey("id")) {
        	String id = String.valueOf(req.getParameter("id"));
        	
        	
            
    		Enumeration<String> parameterNames = req.getParameterNames();
    		Map<String, String> map = new HashMap<String, String>();
    		while (parameterNames.hasMoreElements()) {

    			String paramName = parameterNames.nextElement();
    			if (!paramName.equals("id")) {
	    			//out.write(paramName);
	
	    			String[] paramValues = req.getParameterValues(paramName);
	    			map.put(paramName, paramValues[0]);
	    			/*for (int i = 0; i < paramValues.length; i++) {
	    				String paramValue = paramValues[i];
	    				//out.write("\t" + paramValue);
	    				//out.write("\n");
	    				map.put(paramName, paramValue);
	    			}*/
    			}
    		}
    		
    		if (map.size() > 0) {
    			
    			
    			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        		DB db = mongoClient.getDB( "poibase" );
        		DBCollection coll = db.getCollection("development");
        		BasicDBObject doc = new BasicDBObject("id", id);
    			for (Map.Entry<String, String> entry : map.entrySet()) {
    				String key = entry.getKey();
    				String value = entry.getValue();
    				doc.append(key, value);
    			}
    			try {
    				coll.insert(doc, WriteConcern.ACKNOWLEDGED);
    	        } catch (MongoException e) {
    	            e.printStackTrace();
    	        }
    			
    		} else {
    			PrintWriter out = res.getWriter();
            	out.println("[{\"Error\":\"Please provide an additional attribute (other than ID) \"}]");
    		}
    		// out.write(""+map.size());
    		//out.close();

        	/*MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    		DB db = mongoClient.getDB( "poibase" );
    		DBCollection coll = db.getCollection("development");
    		BasicDBObject doc = new BasicDBObject("name", "MongoDB")
    		        .append("type", "database");
    		coll.insert(doc);*/
        } else {
        	PrintWriter out = res.getWriter();
        	out.println("[{\"Error\":\"Please provide an ID parameter and at least one other attribute\"}]");
        }
		
	}
}
