package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.BestSellerProduct;

/**
 * Session Bean implementation class BestSellerProductService
 */
@Stateless
public class BestSellerProductService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;
    
    public BestSellerProductService() {
    }
    
    public List<BestSellerProduct> findAll() {
    	em.getEntityManagerFactory().getCache().evictAll();
    	return em.createNamedQuery("BestSellerProduct.findAll", BestSellerProduct.class).getResultList();
    } 

}
