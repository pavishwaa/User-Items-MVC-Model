package testpack;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class InsertItem
 */
@WebServlet("/InsertItem")
public class InsertItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/addItem.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String itemName = request.getParameter("iname");
		int iQty = Integer.parseInt(request.getParameter("iqty"));
		Integer uid = (Integer) request.getSession().getAttribute("uid");
		
		Item i = new Item(-1, itemName, iQty, uid); 
		
		
		DB_Access db = new DB_Access();
		boolean res = db.addNewItem(i);
		
		if(res==true) {
			response.sendRedirect("Home");
		}else {
			response.sendRedirect("InsertItem");
		}
	}

}
