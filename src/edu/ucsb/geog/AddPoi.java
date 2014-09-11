package edu.ucsb.geog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class AddPoi
 */
@WebServlet("/AddPoi")
public class AddPoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPoi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        
        if(request.getParameterMap().containsKey("lat") && request.getParameterMap().containsKey("lng") && request.getParameterMap().containsKey("name")) {
        	Float latitude = Float.valueOf(request.getParameter("lat"));
        	Float longitude = Float.valueOf(request.getParameter("lng"));
        	String name = String.valueOf(request.getParameter("name"));
        	int cat = Integer.valueOf(request.getParameter("cat"));
        	insertNewPoi(response, latitude, longitude, name, cat);
        } else {
        	PrintWriter out = response.getWriter();
        	out.println("[{\"Error\":\"Please provide latitude, longitude, name and category ID parameters\"}]");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("lat") && request.getParameterMap().containsKey("lng") && request.getParameterMap().containsKey("name")) {
        	Float latitude = Float.valueOf(request.getParameter("lat"));
        	Float longitude = Float.valueOf(request.getParameter("lng"));
        	String name = String.valueOf(request.getParameter("name"));
        	int cat = Integer.valueOf(request.getParameter("cat"));
        	insertNewPoi(response, latitude, longitude, name, cat);
        } else {
        	PrintWriter out = response.getWriter();
        	out.println("[{\"Error\":\"Please provide latitude, longitude, name and category ID parameters\"}]");
        }
	}

	private static void insertNewPoi(HttpServletResponse response, Float latitude, Float longitude, String name, int cat) throws IOException {
    	
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        poi np = new poi();
        np.setLat(latitude);
        np.setLng(longitude);
        np.setName(name);
        np.setCat(cat);
        np.setUri("");
        PrintWriter out = response.getWriter();
        try {
        	session.save(np);
        	session.getTransaction().commit();
        	out.println("[{\"status\":200}]");
        } catch(HibernateException e) {
        	
        	out.println("[{\"status\":500, \"Error\":\""+e+"\"}]");
        }
        // SessionFactoryUtil.shutdown();
    }
}
