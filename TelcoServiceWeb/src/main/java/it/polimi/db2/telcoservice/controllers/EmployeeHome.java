package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.services.ProductService;
import it.polimi.db2.telco.services.ServiceService;

/**
 * Servlet implementation class EmployeeHome
 */
@WebServlet("/employee/home")
public class EmployeeHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/ProductService")
	private ProductService prService;
	@EJB(name = "it.polimi.db2.telco.services/ServiceService")
	private ServiceService sService;
       
    public EmployeeHome() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("order");
		
		String path = "/WEB-INF/employeeHome.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		Employee e = (Employee) request.getSession().getAttribute("employee");
		if(e == null) {
			path = getServletContext().getContextPath() + "/employee/index";
			response.sendRedirect(path);
		}
		else {
			List<Product> products = prService.findAllProducts();
			ctx.setVariable("products", products);
			templateEngine.process(path, ctx, response.getWriter());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
