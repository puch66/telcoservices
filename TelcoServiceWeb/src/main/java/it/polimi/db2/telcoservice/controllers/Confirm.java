package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;
import java.util.ArrayList;
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

import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.entities.ServicePackage;
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
		
		String validityPeriod = StringEscapeUtils.escapeJava(request.getParameter("validityPeriod"));
		String packageSelectName = StringEscapeUtils.escapeJava(request.getParameter("packageSelect"));
		ServicePackage packageSelected = spService.findFormPackage(packageSelectName);
		ctx.setVariable("packageSelected",packageSelected);
		ctx.setVariable("validityPeriod",validityPeriod);
		
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
		ctx.setVariable("selectedProducts", optSelect);
		ctx.setVariable("totProductFees", totProductFees);
		
		templateEngine.process(path, ctx, response.getWriter());
	}

}
