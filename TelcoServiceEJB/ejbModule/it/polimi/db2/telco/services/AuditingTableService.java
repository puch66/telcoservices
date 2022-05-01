package it.polimi.db2.telco.services;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.telco.entities.AuditingTable;
import it.polimi.db2.telco.entities.Customer;
import it.polimi.db2.telco.exceptions.BadCredentialsException;

/**
 * Session Bean implementation class AuditingTableService
 */
@Stateless
public class AuditingTableService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;
	@EJB(name = "it.polimi.db2.telco.services/CustomOrderService")
	private CustomOrderService coService;

    public AuditingTableService() {
    }

    public void createAuditingTable(String username) throws BadCredentialsException {
    	try {
        	Customer c = em.find(Customer.class, username);
    	    if(c.getIsInsolvent() == 3) {
    	    	AuditingTable a = new AuditingTable();
    	    	a.setUsername(username);
    	    	a.setEmail(c.getEmail());
    	    	int amount = coService.getAmountForAuditingTable(c);
    	    	a.setAmount(amount);
    	    	a.setRejectionDateTime(Calendar.getInstance().getTime());
    	    	em.persist(a);
    	    	em.flush();    	    }
    	} catch(PersistenceException e) {
    		throw new BadCredentialsException("Could not persist order");
    	}
    }
    
    public List<AuditingTable> findAll() {
    	em.getEntityManagerFactory().getCache().evictAll();
    	return em.createNamedQuery("AuditingTable.findAll", AuditingTable.class).getResultList();
    }
}
