package it.polimi.db2.telcoservice.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.telco.services.ServiceService;

/**
 * Servlet implementation class CreateService
 */
@WebServlet("/createService")
public class CreateService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.polimi.db2.telco.services/ServiceService")
	private ServiceService sService;
       
    public CreateService() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = StringEscapeUtils.escapeJava(request.getParameter("type"));
		switch(type) {
			case "mobilephone":
				int numMinutes = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numMinutes")));
				int numSMS = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numSMS")));
				int feeMinutes = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeMinutes")));
				int feeSMS = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeSMS")));
				sService.createService(numMinutes, numSMS, feeMinutes, feeSMS);
				break;
			case "fixedphone":
				sService.createService();
				break;
			case "mobileinternet":
			case "fixedinternet":
				int numGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numGB")));
				int feeGB = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("feeGB")));
				sService.createService(numGB, feeGB, type);
				break;
		}

		String path = getServletContext().getContextPath() + "/employee/home";
		response.sendRedirect(path);
	}

}
