package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.exceptions.BadCredentialsException;

/**
 * Session Bean implementation class ProductService
 */
@Stateless
public class ProductService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public ProductService() {
    }

    public List<Product> findAllProducts() {
    	return em.createNamedQuery("Product.findAll", Product.class).getResultList();
    }
    
    public void createProduct(String name, int fee) throws BadCredentialsException {
    	try {
        	Product p = new Product();
        	p.setName(name);
        	p.setFee(fee);
        	em.persist(p);
        	em.flush();
    	}  catch(PersistenceException e) {
    		throw new BadCredentialsException("Could not create product");
    	}
    }
}
