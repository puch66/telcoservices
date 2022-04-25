package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.Fixedinternetservice;
import it.polimi.db2.telco.entities.Fixedphoneservice;
import it.polimi.db2.telco.entities.Mobileinternetservice;
import it.polimi.db2.telco.entities.Mobilephoneservice;
import it.polimi.db2.telco.entities.Service;

/**
 * Session Bean implementation class ServiceService
 */
@Stateless
@LocalBean
public class ServiceService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public ServiceService() {
    }
    
    public List<Service> findAvailableServices() {
    	return em.createNamedQuery("Service.findAvailableServices", Service.class).getResultList();
    }
    
    public void createService() {
    	Fixedphoneservice s = new Fixedphoneservice();
    	em.persist(s);
    }
    
    public void createService(int numMinutes, int numSMS, int feeExtraMin, int feeExtraSMS) {
    	Mobilephoneservice s = new Mobilephoneservice();
    	s.setNumMinutes(numMinutes);
    	s.setNum_SMS(numSMS);
    	s.setFeeExtraMin(feeExtraMin);
    	s.setFee_extra_SMS(feeExtraSMS);
    	em.persist(s);
    }
    
    public void createService(int numGB, int feeExtraGB, String type) {
    	switch(type) {
    		case "fixedinternet":
    			Fixedinternetservice s = new Fixedinternetservice();
    	    	s.setNum_GB(numGB);
    	    	s.setFee_extra_GB(feeExtraGB);
    	    	em.persist(s);
    	    	break;
    		case "mobileinternet":
    			Mobileinternetservice s2 = new Mobileinternetservice();
    	    	s2.setNum_GB(numGB);
    	    	s2.setFee_extra_GB(feeExtraGB);
    	    	em.persist(s2);
    	    	break;
    	}
    }

}
