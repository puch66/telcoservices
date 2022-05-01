package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Logout() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("order");
		
		String path = getServletContext().getContextPath() + "/index";
		
		String type = StringEscapeUtils.escapeJava(request.getParameter("type"));
		if(type == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Logout needs a parameter type");
			return;
		}
		if(type.equals("c")) request.getSession().removeAttribute("user");
		else if(type.equals("e")) {
			request.getSession().removeAttribute("employee");
			path = getServletContext().getContextPath() + "/employee/index";
		}
		
		
		response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
