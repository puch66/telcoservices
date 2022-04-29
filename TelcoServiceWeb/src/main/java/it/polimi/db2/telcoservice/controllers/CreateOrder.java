package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.telco.entities.CustomOrder;
import it.polimi.db2.telco.entities.Customer;
import it.polimi.db2.telco.services.AuditingTableService;
import it.polimi.db2.telco.services.CustomOrderService;
import it.polimi.db2.telco.services.CustomerService;
import it.polimi.db2.telco.services.ServiceActivationScheduleService;

/**
 * Servlet implementation class CreateOrder
 */
@WebServlet("/createOrder")
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.polimi.db2.telco.services/CustomOrderService")
	private CustomOrderService customOrderService;
	@EJB(name = "it.polimi.db2.telco.services/CustomerService")
	private CustomerService customerService;
	@EJB(name = "it.polimi.db2.telco.services/AuditingTableService")
	private AuditingTableService atService;
	@EJB(name = "it.polimi.db2.telco.services/ServiceActivationScheduleService")
	private ServiceActivationScheduleService sasService;
	
    public CreateOrder() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomOrder order = (CustomOrder) request.getSession().getAttribute("order");
		String isValid = StringEscapeUtils.escapeJava(request.getParameter("isValid"));
		Customer c = (Customer) request.getSession().getAttribute("user");
		
		//if new order, create it
		if(order.getId() == 0) {
			order.setIsValid(Integer.parseInt(isValid));
			customOrderService.persistOrder(order);
			//if payment fails, add insolvence to the user
			if(!isValid.equals("0")) customerService.addInsolvence(c.getUsername(), 1);
			//else create service activation schedule
			else sasService.createActivationSchedule(order);
		}
		//otherwise update order
		else {
			//if payment is valid, decrease user insolvence and create service activation schedule
			if(isValid.equals("0")) {
				customerService.addInsolvence(c.getUsername(), -order.getIsValid());
				customOrderService.validateOrder(order.getId());
				//create service activation schedule
				sasService.createActivationSchedule(order);
			}
			//otherwise increase insolvence
			else {
				customerService.addInsolvence(c.getUsername(),1);
				customOrderService.invalidateOrder(order.getId());
			}
		}
		
		//create auditing table if user has more than 3 failed payments
		if(!isValid.equals("0")) atService.createAuditingTable(c.getUsername());
		
		String path = getServletContext().getContextPath() + "/home";
		response.sendRedirect(path);
	}
}
