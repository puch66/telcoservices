package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.exceptions.BadCredentialsException;
import it.polimi.db2.telco.services.ProductService;
import it.polimi.db2.telco.services.ServicePackageService;
import it.polimi.db2.telco.services.ServiceService;

/**
 * Servlet implementation class CreateServicePackage
 */
@WebServlet("/employee/createServicePackage")
public class CreateServicePackage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.polimi.db2.telco.services/ServicePackageService")
	private ServicePackageService spService;
	@EJB(name = "it.polimi.db2.telco.services/ProductService")
	private ProductService prService;
	@EJB(name = "it.polimi.db2.telco.services/ServiceService")
	private ServiceService sService;
	
    public CreateServicePackage() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = StringEscapeUtils.escapeJava(request.getParameter("name"));
		if(name == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some parameter was set wrong while creating package");
			return;
		}

		int vp12 = 0, vp24 = 0, vp36 = 0;
		try {
			vp12 = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("vp12")));
			vp24 = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("vp24")));
			vp36 = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("vp36")));
		} catch(NumberFormatException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some parameter was set wrong while creating package");
			return;
		}			
		if(vp12 < vp24 || vp24 < vp36 || vp12 < vp36) {
			request.setAttribute("errorMsg", "Validity period must be greater when months are less");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/employee/home");
			dispatcher.forward(request, response);
			return;
		}
		
		List<Product> products = prService.findAllProducts();
		List<Product> productsSelected = new ArrayList<Product>();
		String reqproduct;
		for(Product p: products) {
			reqproduct = StringEscapeUtils.escapeJava(request.getParameter(p.getName()));
			if(reqproduct != null) {
				productsSelected.add(p);
			}
		}
		
		List<Service> servicesSelected = new ArrayList<Service>();
		String mpcheck = StringEscapeUtils.escapeJava(request.getParameter("mpcheck"));
		if(mpcheck != null) {
			int numMinutes = 0, feeMinutes = 0, numSMS = 0, feeSMS = 0;
			try {
				numMinutes = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numMinutes")));
				numSMS = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numSMS")));
				feeMinutes = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeMinutes")));
				feeSMS = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeSMS")));
			} catch(NumberFormatException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some parameter was set wrong while creating package");
				return;
			}
			servicesSelected.add(sService.createService(numMinutes, numSMS, feeMinutes, feeSMS));
		}
		String fpcheck = StringEscapeUtils.escapeJava(request.getParameter("fpcheck"));
		if(fpcheck != null) {		
			servicesSelected.add(sService.createService());
		}
		String micheck = StringEscapeUtils.escapeJava(request.getParameter("micheck"));
		if(micheck != null) {	
			int numGB = 0, feeGB = 0;
			try {
				numGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numGB")));
				feeGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeGB")));
			} catch(NumberFormatException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some parameter was set wrong while creating package");
				return;
			}
			servicesSelected.add(sService.createService(numGB, feeGB, "mobileinternet"));
		}
		String ficheck = StringEscapeUtils.escapeJava(request.getParameter("ficheck"));
		if(ficheck != null) {
			int numGB = 0, feeGB = 0;
			try {
				numGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numGBfi")));
				feeGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeGBfi")));
			} catch(NumberFormatException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some parameter was set wrong while creating package");
				return;
			}
			servicesSelected.add(sService.createService(numGB, feeGB, "fixedinternet"));
		}
		if(mpcheck == null && fpcheck == null && micheck == null && ficheck == null) {
			request.setAttribute("errorMsg", "You must select at least one service");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/employee/home");
			dispatcher.forward(request, response);
			return;
		}
		
		try {
			spService.createServicePackage(name,vp12,vp24,vp36,productsSelected,servicesSelected);
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "Package name has already been used");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/employee/home");
			dispatcher.forward(request, response);
			return;
		}
		
		request.setAttribute("packageSuccess", "The new package has been created succesfully");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/employee/home");
		dispatcher.forward(request, response);
	}

}
