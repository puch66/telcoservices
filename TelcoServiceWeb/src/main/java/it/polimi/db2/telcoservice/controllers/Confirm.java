package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
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
import it.polimi.db2.telco.exceptions.BadCredentialsException;
import it.polimi.db2.telco.services.CustomOrderService;
import it.polimi.db2.telco.services.ServicePackageService;

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
		
		String sDate = StringEscapeUtils.escapeJava(request.getParameter("startDate"));
		Date startDate = Calendar.getInstance().getTime();
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		Customer customer = (Customer) request.getSession().getAttribute("user");
		
		String feeAndDuration = StringEscapeUtils.escapeJava(request.getParameter("validityPeriod"));
		String[] validityPeriod = feeAndDuration.split("-");
		
		String packageSelectName = StringEscapeUtils.escapeJava(request.getParameter("packageSelect"));
		ServicePackage packageSelected = spService.findFormPackage(packageSelectName);
		
		ValidityPeriod validity = null;
		for(ValidityPeriod vp: packageSelected.getValidityPeriods() ) {
			if(vp.getFee() == Integer.parseInt(validityPeriod[0]) && vp.getDuration() == Integer.parseInt(validityPeriod[1]))
				validity = vp;
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
		
		int totalValue = (totProductFees+Integer.parseInt(validityPeriod[0]))*Integer.parseInt(validityPeriod[1]);
		
		try {
			CustomOrder order = customOrderService.createAbstractOrder(startDate, totalValue, customer, packageSelected, validity, optSelect);
			request.getSession().setAttribute("order", order);
		} catch (BadCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomOrder order = (CustomOrder) request.getSession().getAttribute("order");
		order.setCustomer((Customer) request.getSession().getAttribute("user"));
		request.getSession().setAttribute("order", order);
		
		String path = "/WEB-INF/confirmation_page.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		templateEngine.process(path, ctx, response.getWriter());
	}

}
