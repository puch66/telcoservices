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
		
		List<Service> servicesSelected = new ArrayList<Service>();
		String mpcheck = StringEscapeUtils.escapeJava(request.getParameter("mpcheck"));
		if(mpcheck != null) {
			int numMinutes = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numMinutes")));
			int numSMS = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numSMS")));
			int feeMinutes = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeMinutes")));
			int feeSMS = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeSMS")));
			servicesSelected.add(sService.createService(numMinutes, numSMS, feeMinutes, feeSMS));
		}
		String fpcheck = StringEscapeUtils.escapeJava(request.getParameter("fpcheck"));
		if(fpcheck != null) {		
			servicesSelected.add(sService.createService());
		}
		String micheck = StringEscapeUtils.escapeJava(request.getParameter("micheck"));
		if(micheck != null) {		
			int numGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numGB")));
			int feeGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeGB")));
			servicesSelected.add(sService.createService(numGB, feeGB, "mobileinternet"));
		}
		String ficheck = StringEscapeUtils.escapeJava(request.getParameter("ficheck"));
		if(ficheck != null) {
			int numGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numGBfi")));
			int feeGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeGBfi")));
			servicesSelected.add(sService.createService(numGB, feeGB, "fixedinternet"));
		}
		
		spService.createServicePackage(name,vp12,vp24,vp36,productsSelected,servicesSelected);
		
		String path = getServletContext().getContextPath() + "/employee/home";
		response.sendRedirect(path);
	}

}
