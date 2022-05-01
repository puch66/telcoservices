package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.telco.exceptions.BadCredentialsException;
import it.polimi.db2.telco.services.CustomerService;

/**
 * Servlet implementation class CheckRegistration
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.polimi.db2.telco.services/CustomerService")
	private CustomerService customerService;
       
    public Register() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = null;
		String password = null;
		String email = null;
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			email = StringEscapeUtils.escapeJava(request.getParameter("email"));
			if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty() ) {
				throw new Exception("Missing registration data");
			}

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing registration data");
			return;
		}
		
		try {
			customerService.createCustomer(username, password, email);
		} catch (BadCredentialsException e) {
			e.printStackTrace(); //for debug
			request.setAttribute("registerMsg", "User or email have already been used");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index");
			dispatcher.forward(request, response);
			return;
		}
		
		request.setAttribute("regSuccess", "User correctly registered");
		String redirect = StringEscapeUtils.escapeJava(request.getParameter("redirect"));
		if(redirect != null) request.setAttribute("redirect", "true");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index");
		dispatcher.forward(request, response);
	}

}
