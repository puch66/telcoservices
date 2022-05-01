package it.polimi.db2.telco.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.ValidityPeriod;

/**
 * Session Bean implementation class ValidityPeriodService
 */
@Stateless
@LocalBean
public class ValidityPeriodService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public ValidityPeriodService() {
    }
    
    public ValidityPeriod findValidity(int id) {
    	return em.find(ValidityPeriod.class, id);
    }

}
