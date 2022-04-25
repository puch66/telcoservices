package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.services.ProductService;
import it.polimi.db2.telco.services.ServicePackageService;
import it.polimi.db2.telco.services.ServiceService;

/**
 * Servlet implementation class CreateServicePackage
 */
@WebServlet("/createServicePackage")
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
		int vp12 = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("vp12")));
		int vp24 = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("vp24")));
		int vp36 = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("vp36")));
		
		List<Product> products = prService.findAllProducts();
		List<Product> productsSelected = new ArrayList<Product>();
		String reqproduct;
		for(Product p: products) {
			reqproduct = StringEscapeUtils.escapeJava(request.getParameter(p.getName()));
			if(reqproduct != null) {
				productsSelected.add(p);
			}
		}
		
		List<Service> services = sService.findAvailableServices();
		List<Service> servicesSelected = new ArrayList<Service>();
		for(Service s: services) {
			reqproduct = StringEscapeUtils.escapeJava(request.getParameter(Integer.toString(s.getId())));
			if(reqproduct != null) {
				servicesSelected.add(s);
			}
		}
		
		spService.createServicePackage(name,vp12,vp24,vp36,productsSelected,servicesSelected);
		
		String path = getServletContext().getContextPath() + "/employee/home";
		response.sendRedirect(path);
	}

}
