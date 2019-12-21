package testpack;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditItem
 */
@WebServlet("/EditItem")
public class EditItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");  
        int iid=Integer.parseInt(id);
        DB_Access db = new DB_Access();
        Item item = db.getItemById(iid);
        request.setAttribute("item", item);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/editItem.jsp");
        rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");  
		int iid=Integer.parseInt(id);  
		Integer uid = (Integer) request.getSession().getAttribute("uid");
		
		String iname = request.getParameter("iname");
		int iqty = Integer.parseInt(request.getParameter("iqty"));
		Item i = new Item(-1, iname, iqty, uid);
		
		DB_Access db = new DB_Access();
		boolean res = db.updateItem(iid, i);
		if(res == true)	{
			response.sendRedirect("Home?msg=Item edited!");
		} else {
			response.sendRedirect("Home?msg=Could not edit Item!");
		}
		
	}

}
