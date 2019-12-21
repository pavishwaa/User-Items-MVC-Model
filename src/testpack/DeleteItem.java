package testpack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteItem")
public class DeleteItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
//        String id=request.getParameter("iid");  
//        int iid=Integer.parseInt(id); 
        String id=request.getParameter("id");  
        int iid=Integer.parseInt(id);  
//        DB_Access db = new DB_Access();
//        Item item = db.getItemById(iid);
        
        DB_Access.deleteItem(iid);
     
		response.sendRedirect("Home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Home?msg = Deleted Successfully");
	}

}
