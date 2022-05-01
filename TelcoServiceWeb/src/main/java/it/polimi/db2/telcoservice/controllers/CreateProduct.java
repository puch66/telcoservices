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
import it.polimi.db2.telco.services.ProductService;

/**
 * Servlet implementation class CreateProduct
 */
@WebServlet("/employee/createProduct")
public class CreateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.polimi.db2.telco.services/ProductService")
	private ProductService prService;

    public CreateProduct() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = StringEscapeUtils.escapeJava(request.getParameter("name"));
		String fee = StringEscapeUtils.escapeJava(request.getParameter("fee"));
		if(name == null ||fee == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing name or fee");
			return;
		}
		
		try {
			prService.createProduct(name, Integer.parseInt(fee));
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			request.setAttribute("productErr", "Product name has already been used");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/employee/home");
			dispatcher.forward(request, response);
			return;
		} catch (NumberFormatException e2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not parse fee");
			return;
		}
		
		request.setAttribute("productSuccess", "The new product has been created succesfully");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/employee/home");
		dispatcher.forward(request, response);
	}

}
