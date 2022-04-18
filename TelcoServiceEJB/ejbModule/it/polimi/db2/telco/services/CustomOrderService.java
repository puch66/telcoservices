package it.polimi.db2.telco.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.telco.entities.CustomOrder;
import it.polimi.db2.telco.entities.Customer;
import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.entities.ServicePackage;
import it.polimi.db2.telco.entities.ValidityPeriod;
import it.polimi.db2.telco.exceptions.BadCredentialsException;

/**
 * Session Bean implementation class CustomOrderService
 */
@Stateless
public class CustomOrderService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public CustomOrderService() {
        // TODO Auto-generated constructor stub
    }
    
    public CustomOrder createAbstractOrder(Date startDate, int totalValue, Customer customer, ServicePackage serPackage, ValidityPeriod validityPeriod, List<Product> products) throws BadCredentialsException {
		try {
			CustomOrder order = new CustomOrder();
			order.setCreationDate(Calendar.getInstance().getTime());
			order.setStartDate(startDate);
			order.setTotalValue(totalValue);
			order.setCustomer(customer);
			order.setServicePackageBean(serPackage);
			order.setValidityPeriod(validityPeriod);
			order.setProducts(products);
			return order;
		} catch (PersistenceException e) {
			throw new BadCredentialsException("Could not create order");
		}
	}
    
    public void persistOrder(CustomOrder order) {
    	em.persist(order);
    }

}
