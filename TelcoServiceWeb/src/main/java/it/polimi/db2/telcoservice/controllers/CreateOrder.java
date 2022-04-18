package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.entities.CustomOrder;
import it.polimi.db2.telco.services.CustomOrderService;

/**
 * Servlet implementation class CreateOrder
 */
@WebServlet("/createOrder")
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/CustomOrderService")
	private CustomOrderService customOrderService;
	
    public CreateOrder() {
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
		CustomOrder order = (CustomOrder) request.getSession().getAttribute("order");
		String isValid = StringEscapeUtils.escapeJava(request.getParameter("isValid"));
		order.setIsValid(Integer.parseInt(isValid));
		
		//NON BASTA!
		if(isValid.equals("0")) order.getCustomer().setIsInsolvent(order.getCustomer().getIsInsolvent()+1);//set the user as insolvent
		else;//create a service activation schedule
		customOrderService.persistOrder(order);
		
		String path = getServletContext().getContextPath() + "/home";
		response.sendRedirect(path);
	}
}
