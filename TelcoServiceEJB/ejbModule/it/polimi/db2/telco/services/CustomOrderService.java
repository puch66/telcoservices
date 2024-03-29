package it.polimi.db2.telco.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

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
    
    public CustomOrder createAbstractOrder(Date startDate, int totalValue, Customer customer, ServicePackage serPackage, ValidityPeriod validityPeriod, List<Product> products) {
		CustomOrder order = new CustomOrder();
		order.setCreationDate(Calendar.getInstance().getTime());
		order.setStartDate(startDate);
		order.setTotalValue(totalValue);
		order.setCustomer(customer);
		order.setServicePackageBean(serPackage);
		order.setValidityPeriod(validityPeriod);
		order.setProducts(products);
		return order;
	}
    
    @Transactional
    public void persistOrder(CustomOrder order) throws BadCredentialsException {
    	try {
        	em.persist(order);
        	em.flush();
        	List<Product> productsToAdd = new ArrayList<>(order.getProducts());
        	order.setProducts(new ArrayList<>());
        	for(Product p:productsToAdd) {
        		this.addToOrderedProduct(p.getName(), order.getId());
        	}
    	} catch(PersistenceException e) {
    		throw new BadCredentialsException("Could not persist order");
    	}
    }
    
    private void addToOrderedProduct(String p, int c) {
    	Product pr = em.find(Product.class,p);
    	CustomOrder co = em.find(CustomOrder.class, c);
    	co.addProduct(pr);
    }
    
    public List<CustomOrder> findRejectedOrders(Customer c) {
    	List<CustomOrder> rejectedOrders = em.createNamedQuery("CustomOrder.findRejectedOrders", CustomOrder.class).setParameter(1, c).getResultList();
    	return rejectedOrders;
    }
    
    public List<CustomOrder> findAllRejectedOrders() {
    	List<CustomOrder> rejectedOrders = em.createNamedQuery("CustomOrder.findAllRejectedOrders", CustomOrder.class).getResultList();
    	return rejectedOrders;
    }
    
    public CustomOrder findOrder(int id) {
    	return em.find(CustomOrder.class, id);
    }
    
    public void validateOrder(int id) {
    	CustomOrder order = em.find(CustomOrder.class, id);
    	order.setIsValid(0);
    }
    
    public void invalidateOrder(int id) {
    	CustomOrder order = em.find(CustomOrder.class, id);
    	order.setIsValid(order.getIsValid()+1);
    }
    
    public int getAmountForAuditingTable(Customer c) {
    	int amount = em.createNamedQuery("CustomOrder.getAmount", Long.class).setParameter(1, c).getResultList().get(0).intValue();
    	return amount;
    }

}
