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

import it.polimi.db2.telco.entities.AuditingTable;
import it.polimi.db2.telco.entities.AverageProductsSoldForPackage;
import it.polimi.db2.telco.entities.BestSellerProduct;
import it.polimi.db2.telco.entities.CustomOrder;
import it.polimi.db2.telco.entities.Customer;
import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.entities.SalesValue;
import it.polimi.db2.telco.entities.TotalPurchasesPerPackage;
import it.polimi.db2.telco.entities.TotalPurchasesPerPackageAndValidityPeriod;
import it.polimi.db2.telco.services.AuditingTableService;
import it.polimi.db2.telco.services.AverageProductsSoldForPackageService;
import it.polimi.db2.telco.services.BestSellerProductService;
import it.polimi.db2.telco.services.CustomOrderService;
import it.polimi.db2.telco.services.CustomerService;
import it.polimi.db2.telco.services.SalesValueService;
import it.polimi.db2.telco.services.TotalPurchasesPerPackageAndValidityPeriodService;
import it.polimi.db2.telco.services.TotalPurchasesPerPackageService;

/**
 * Servlet implementation class EmployeeSalesReport
 */
@WebServlet("/employee/salesReport")
public class EmployeeSalesReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/TotalPurchasesPerPackageService")
	private TotalPurchasesPerPackageService tService;
	@EJB(name = "it.polimi.db2.telco.services/TotalPurchasesPerPackageAndValidityPeriodService")
	private TotalPurchasesPerPackageAndValidityPeriodService tpService;
	@EJB(name = "it.polimi.db2.telco.services/SalesValueService")
	private SalesValueService svService;
	@EJB(name = "it.polimi.db2.telco.services/AverageProductsSoldForPackageService")
	private AverageProductsSoldForPackageService apService;
	@EJB(name = "it.polimi.db2.telco.services/BestSellerProductService")
	private BestSellerProductService bpService;
	@EJB(name = "it.polimi.db2.telco.services/CustomerService")
	private CustomerService cService;
	@EJB(name = "it.polimi.db2.telco.services/CustomOrderService")
	private CustomOrderService coService;
	@EJB(name = "it.polimi.db2.telco.services/AuditingTableService")
	private AuditingTableService atService;
	
    public EmployeeSalesReport() {
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
		String path = "/WEB-INF/employeeSalesReport.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		Employee e = (Employee) request.getSession().getAttribute("employee");
		if(e == null) {
			path = getServletContext().getContextPath() + "/employee/index";
			response.sendRedirect(path);
		}
		else {
			List<TotalPurchasesPerPackage> tpp = tService.findAll();
			ctx.setVariable("tpp", tpp);
			List<TotalPurchasesPerPackageAndValidityPeriod> tppvp = tpService.findAll();
			ctx.setVariable("tppvp", tppvp);
			List<SalesValue> sv = svService.findAll();
			ctx.setVariable("sv", sv);
			List<BestSellerProduct> bp = bpService.findAll();
			ctx.setVariable("bp", bp);
			List<AverageProductsSoldForPackage> ap = apService.findAll();
			ctx.setVariable("ap", ap);
			List<Customer> insolvents = cService.getInsolventUsers();
			ctx.setVariable("insolvents", insolvents);
			List<CustomOrder> orders = coService.findAllRejectedOrders();
			ctx.setVariable("rejectedOrders", orders);
			List<AuditingTable> alerts = atService.findAll();
			ctx.setVariable("alerts", alerts);
			templateEngine.process(path, ctx, response.getWriter());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
