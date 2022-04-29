package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.AverageProductsSoldForPackage;

/**
 * Session Bean implementation class AverageProductSoldForPackageService
 */
@Stateless
public class AverageProductsSoldForPackageService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;
    
    public AverageProductsSoldForPackageService() {
    }
    
    public List<AverageProductsSoldForPackage> findAll() {
    	em.getEntityManagerFactory().getCache().evictAll();
    	return em.createNamedQuery("AverageProductsSoldForPackage.findAll", AverageProductsSoldForPackage.class).getResultList();
    } 
}
