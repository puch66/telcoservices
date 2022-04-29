package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.TotalPurchasesPerPackage;

/**
 * Session Bean implementation class TotalPurchasesPerPackageService
 */
@Stateless
public class TotalPurchasesPerPackageService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public TotalPurchasesPerPackageService() {
    }
    
    public List<TotalPurchasesPerPackage> findAll() {
    	em.getEntityManagerFactory().getCache().evictAll();
    	return em.createNamedQuery("TotalPurchasesPerPackage.findAll", TotalPurchasesPerPackage.class).getResultList();
    }

}
