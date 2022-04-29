package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.SalesValue;

/**
 * Session Bean implementation class SalesValueService
 */
@Stateless
public class SalesValueService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public SalesValueService() {
    }
    
    public List<SalesValue> findAll() {
    	em.getEntityManagerFactory().getCache().evictAll();
    	return em.createNamedQuery("SalesValue.findAll", SalesValue.class).getResultList();
    }

}
