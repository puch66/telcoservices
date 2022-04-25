package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.telco.services.ProductService;

/**
 * Servlet implementation class CreateProduct
 */
@WebServlet("/createProduct")
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
		prService.createProduct(name, Integer.parseInt(fee));
		
		String path = getServletContext().getContextPath() + "/employee/home";
		response.sendRedirect(path);
	}

}
