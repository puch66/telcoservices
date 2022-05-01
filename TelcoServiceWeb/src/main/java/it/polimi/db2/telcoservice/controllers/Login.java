package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.telco.entities.Customer;
import it.polimi.db2.telco.exceptions.BadCredentialsException;
import it.polimi.db2.telco.services.CustomerService;



@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.polimi.db2.telco.services/CustomerService")
	private CustomerService customerService;
	
	public Login() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = null;
		String password = null;
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
				throw new Exception("Missing user or password");
			}

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user or password");
			return;
		}
		Customer customer;
		try {
			customer = customerService.checkCredentials(username, password);
		} catch (BadCredentialsException | NonUniqueResultException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
			return;
		}

		// If the user exists, add info to the session and go to home page, otherwise
		// show login page with error message

		String path;
		if (customer == null) {
			ServletContext servletContext = getServletContext();
			request.setAttribute("errorMsg", "Incorrect username or password");
			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/index");
			dispatcher.forward(request, response);
		} else {
			request.getSession().setAttribute("user", customer);
			if(request.getParameter("redirect") != null) path = getServletContext().getContextPath() + "/confirm";
			else path = getServletContext().getContextPath() + "/home";
			response.sendRedirect(path);
		}
	}
}
