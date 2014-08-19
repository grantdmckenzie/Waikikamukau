package edu.ucsb.geog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.ucsb.geog.SessionFactoryUtil;
import edu.ucsb.geog.poi;

/**
 * Servlet implementation class Waikikamukau
 */
@WebServlet("/Nearby")
public class Nearby extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Nearby() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        
        if(request.getParameterMap().containsKey("lat") && request.getParameterMap().containsKey("lng")) {
        	Float latitude = Float.valueOf(request.getParameter("lat"));
        	Float longitude = Float.valueOf(request.getParameter("lng"));
        	queryNearby(session, response, latitude, longitude);
        } else {
        	PrintWriter out = response.getWriter();
        	out.println("[{\"Error\":\"Please provide latitude and longitude parameters\"}]");
        }
	}

	private static void queryNearby(Session session, HttpServletResponse response, Float latitude, Float longitude) throws IOException {
    	
        Query query = session.getNamedQuery("callNearbyStoredProcedure"); 
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Content-Encoding", "UTF-8");
       
        // Sample query parameters: lat=-119.8005785&lng=34.4361521
        query.setParameter("lng", longitude);
        query.setParameter("lat", latitude);
        List result = query.list(); 
        JSONArray pArray = new JSONArray();
        JSONObject pJson = new JSONObject();
        PrintWriter out = response.getWriter();
        for(int i=0; i<result.size(); i++){
        	poi p = (poi)result.get(i);
        	JSONObject obj = new JSONObject();
        	obj.put("id",p.getId());
        	obj.put("name",p.getName());
        	obj.put("cat",p.getCat());
        	obj.put("lat",p.getLat());
        	obj.put("lng",p.getLng());
        	obj.put("dist",p.getDistance());
        	pArray.add(obj);
        }
        pJson.put("response", pArray);
        
        // JSONValue.writeJSONString(pJson, out);
        response.setContentType("application/json");
        PrintWriter done = response.getWriter();
	     // Assuming your json object is **jsonObject**, perform the following, it will return your json object  
        done.print(pJson);
        done.flush();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        if(request.getParameterMap().containsKey("lat") && request.getParameterMap().containsKey("lng")) {
        	Float latitude = Float.valueOf(request.getParameter("lat"));
        	Float longitude = Float.valueOf(request.getParameter("lng"));
        	queryNearby(session, response, latitude, longitude);
        } else {
        	PrintWriter out = response.getWriter();
        	out.println("[{\"Error\":\"Please provide latitude and longitude parameters\"}]");
        }
	}

}
