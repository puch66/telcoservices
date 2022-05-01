package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.telco.entities.CustomOrder;
import it.polimi.db2.telco.entities.Customer;
import it.polimi.db2.telco.exceptions.BadCredentialsException;
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
		if(order == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No order has been set to be created");
			return;
		}
		
		Customer c = (Customer) request.getSession().getAttribute("user");
		if(c == null ) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No order has been set to be created");
			return;
		}
		
		String isValid = StringEscapeUtils.escapeJava(request.getParameter("isValid"));
		if(isValid == null || (!isValid.equals("0") && !isValid.equals("1")) ) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No order has been set to be created");
			return;
		}
		
		//if new order, create it
		if(order.getId() == 0) {
			order.setIsValid(Integer.parseInt(isValid));
			try {
				customOrderService.persistOrder(order);
			} catch (BadCredentialsException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not persist order");
				return;
			}
			//if payment fails, add insolvence to the user
			if(!isValid.equals("0")) customerService.addInsolvence(c.getUsername(), 1);
			//else create service activation schedule
			else try {
				sasService.createActivationSchedule(order);
			} catch (BadCredentialsException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not create activation schedule");
				return;
			}
		}
		//otherwise update order
		else {
			//if payment is valid, decrease user insolvence and create service activation schedule
			if(isValid.equals("0")) {
				customerService.addInsolvence(c.getUsername(), -order.getIsValid());
				customOrderService.validateOrder(order.getId());
				//create service activation schedule
				try {
					sasService.createActivationSchedule(order);
				} catch (BadCredentialsException e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not create activation schedule");
					return;
				}
			}
			//otherwise increase insolvence
			else {
				customerService.addInsolvence(c.getUsername(),1);
				customOrderService.invalidateOrder(order.getId());
			}
		}
		
		//create auditing table if user has more than 3 failed payments
		if(!isValid.equals("0")) {
			try {
				atService.createAuditingTable(c.getUsername());
			} catch (BadCredentialsException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not create activation schedule");
				return;
			}
			String path = getServletContext().getContextPath() + "/home";
			response.sendRedirect(path);
		}
		else {
			request.setAttribute("orderSuccess", "Order has been paid succesfully");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home");
			dispatcher.forward(request, response);
		}
		
		request.getSession().removeAttribute("order");
	}
}
