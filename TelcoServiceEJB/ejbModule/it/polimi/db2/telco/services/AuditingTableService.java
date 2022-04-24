package it.polimi.db2.telco.services;

import java.util.Calendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.AuditingTable;
import it.polimi.db2.telco.entities.Customer;

/**
 * Session Bean implementation class AuditingTableService
 */
@Stateless
public class AuditingTableService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public AuditingTableService() {
    }

    public void createAuditingTable(String username, int amount) {
    	Customer c = em.find(Customer.class, username);
	    if(c.getIsInsolvent() >= 3) {
	    	AuditingTable a = new AuditingTable();
	    	a.setUsername(username);
	    	a.setEmail(c.getEmail());
	    	a.setAmount(amount);
	    	a.setRejectionDateTime(Calendar.getInstance().getTime());
	    	em.persist(a);
	    }
    }
}
