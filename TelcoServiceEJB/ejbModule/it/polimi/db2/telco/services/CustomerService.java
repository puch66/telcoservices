package it.polimi.db2.telco.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.telco.entities.CustomOrder;
import it.polimi.db2.telco.entities.Customer;
import it.polimi.db2.telco.exceptions.BadCredentialsException;

import javax.persistence.NonUniqueResultException;

import java.util.List;

@Stateless
public class CustomerService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

	public CustomerService() {
	}

	public Customer checkCredentials(String username, String password) throws BadCredentialsException, NonUniqueResultException {
		List<Customer> uList = null;
		try {
			uList = em.createNamedQuery("Customer.checkCredentials", Customer.class).setParameter(1, username).setParameter(2, password).getResultList();
		} catch (PersistenceException e) {
			throw new BadCredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		else
			throw new NonUniqueResultException("More than one user registered with same credentials");
	}
	
	public void createCustomer(String username, String password, String email) throws BadCredentialsException {
		try {
			Customer customer = new Customer();
			customer.setUsername(username);
			customer.setPassword(password);
			customer.setEmail(email);
			//ADD TRY CATCH -- not working
			em.persist(customer);
			//em.flush();
		} catch (PersistenceException e) {
			throw new BadCredentialsException("Could not register customer");
		}
	}
	
	public void addInsolvence(CustomOrder order) {
		Customer customer = em.find(Customer.class, order.getCustomer().getUsername());
		customer.setIsInsolvent(customer.getIsInsolvent()+1);
	}

}
