package testpack;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewItem")
public class ViewItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String id=request.getParameter("id");  
        int iid=Integer.parseInt(id);  
    
        DB_Access db = new DB_Access();
        Item item = db.getItemById(iid);
        request.setAttribute("item", item);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/ViewItem.jsp");
        rd.forward(request, response);		
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
