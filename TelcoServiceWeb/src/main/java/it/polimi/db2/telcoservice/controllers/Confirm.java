package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.entities.CustomOrder;
import it.polimi.db2.telco.entities.Customer;
import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.entities.ServicePackage;
import it.polimi.db2.telco.entities.ValidityPeriod;
import it.polimi.db2.telco.services.CustomOrderService;
import it.polimi.db2.telco.services.ServicePackageService;
import it.polimi.db2.telco.services.ValidityPeriodService;

/**
 * Servlet implementation class Confirm
 */
@WebServlet("/confirm")
public class Confirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/ServicePackageService")
	private ServicePackageService spService;
	@EJB(name = "it.polimi.db2.telco.services/CustomOrderService")
	private CustomOrderService customOrderService;
	@EJB(name = "it.polimi.db2.telco.services/ValidityPeriodService")
	private ValidityPeriodService vpService;
    
    public Confirm() {
        super();
    }
    
    public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/WEB-INF/confirmation_page.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		String packageSelectName = StringEscapeUtils.escapeJava(request.getParameter("packageSelect"));
		ServicePackage packageSelected = spService.findFormPackage(packageSelectName);
		if(packageSelected == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not find package");
			return;
		}
		
		String sDate = StringEscapeUtils.escapeJava(request.getParameter("startDate"));
		Date startDate = null;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not parse date");
			return;
		}
		
		if(startDate.before(Calendar.getInstance().getTime())) {
			request.setAttribute("errorMsg", "Start date of subscription cannot be earlier than today");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/buyservices?packageSelect="+packageSelectName);
			dispatcher.forward(request, response);
			return;
		}
		
		Customer customer = (Customer) request.getSession().getAttribute("user");
		
		String validityPeriod = StringEscapeUtils.escapeJava(request.getParameter("validityPeriod"));
		ValidityPeriod validity = null;
		try {
			validity = vpService.findValidity(Integer.parseInt(validityPeriod));
		} catch(NumberFormatException e) {
			e.printStackTrace(); //debug
			request.setAttribute("errorMsg", "Please fill out all fields");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/buyservices?packageSelect="+packageSelectName);
			dispatcher.forward(request, response);
			return;
		}
		
		
		List<Product> optSelect = new ArrayList<Product>();
		String reqproduct;
		int totProductFees = 0;
		for(Product p: packageSelected.getProducts()) {
			reqproduct = StringEscapeUtils.escapeJava(request.getParameter(p.getName()));
			if(reqproduct != null) {
				optSelect.add(p);
				totProductFees+=p.getFee();
			}
		}
		
		//int totalValue = (totProductFees+Integer.parseInt(validityPeriod[0]))*Integer.parseInt(validityPeriod[1]);
		int totalValue = (totProductFees+validity.getFee())*validity.getDuration();

		CustomOrder order = customOrderService.createAbstractOrder(startDate, totalValue, customer, packageSelected, validity, optSelect);
		request.getSession().setAttribute("order", order);
		
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Customer c = (Customer) request.getSession().getAttribute("user");
		if(c == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not confirm order");
			return;
		}
		
		String orderId = StringEscapeUtils.escapeJava(request.getParameter("id"));
		CustomOrder order = (CustomOrder) request.getSession().getAttribute("order");
		String path;
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		if(order == null && orderId == null) {			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not confirm order");
			return;
		}
		else {
			if(orderId != null) {
				try {
					order = customOrderService.findOrder(Integer.parseInt(orderId));
				} catch(NumberFormatException e) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not confirm order");
					return;
				}
			}
			else order.setCustomer(c);
			request.getSession().setAttribute("order", order);
			
			path = "/WEB-INF/confirmation_page.html";
			templateEngine.process(path, ctx, response.getWriter());
		}
	}
}
