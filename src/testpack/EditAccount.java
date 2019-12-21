
package testpack;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditAccount
 */
@WebServlet("/EditAccount")
public class EditAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Integer uid = (Integer) request.getSession().getAttribute("uid");
    	
    	DB_Access db = new DB_Access(); 
		String uname = db.getUserName(uid);
		String upass = db.getUserById(uid);
		request.setAttribute("name", uname);
		request.setAttribute("pass", upass);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/editUser.jsp");
		rd.forward(request, response);	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer uid = (Integer) request.getSession().getAttribute("uid");
		String lname = (String) request.getSession().getAttribute("LoginName");
		System.out.println("Login name:" + lname);
		String name = request.getParameter("name");
		String lpass = request.getParameter("pass");
		User u = new User(-1, lname, name, lpass);

		
		DB_Access db = new DB_Access();
		boolean res = db.updateUser(uid, u);
		if(res == true)	{
			response.sendRedirect("Home?msg=Account modify successfully..!");
		}
		else {
			response.sendRedirect("Home?msg=Account cannot modify..!");
		}
	}

}
