package it.polimi.db2.telco.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.entities.ServicePackage;
import it.polimi.db2.telco.entities.ValidityPeriod;

/**
 * Session Bean implementation class ServicePackageService
 */
@Stateless
public class ServicePackageService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public ServicePackageService() {
        // TODO Auto-generated constructor stub
    }
    
    public List<ServicePackage> findAllPackages() {
    	return em.createNamedQuery("ServicePackage.findAll", ServicePackage.class).getResultList();
    }
    
    public List<String> findAllPackageNames() {
		return em.createNamedQuery("ServicePackage.findAllPackageNames", String.class).getResultList();
	}
    
    public ServicePackage findFormPackage(String name) {
    	return em.createNamedQuery("ServicePackage.findFormPackage", ServicePackage.class).setParameter(1, name).getSingleResult();
    }
    
    public void createServicePackage(String name, int vp12, int vp24, int vp36, List<Product> productsSelected, List<Service> servicesSelected) {
    	ServicePackage sp = new ServicePackage();
    	sp.setName(name);
    	
    	ValidityPeriod v12 = new ValidityPeriod(); v12.setDuration(12); v12.setFee(vp12);
    	ValidityPeriod v24 = new ValidityPeriod(); v24.setDuration(24); v24.setFee(vp24);
    	ValidityPeriod v36 = new ValidityPeriod(); v36.setDuration(36); v36.setFee(vp36);
    	
    	sp.setValidityPeriods(new ArrayList<ValidityPeriod>());
    	sp.addValidityPeriod(v12);
    	sp.addValidityPeriod(v24);
    	sp.addValidityPeriod(v36);
    	
    	sp.setProducts(new ArrayList<Product>());
    	for(Product p: productsSelected) {
    		sp.addProduct(p);
    	}
    	
    	sp.setServices(new ArrayList<Service>());
    	for(Service s: servicesSelected) {
    		s = em.find(Service.class, s.getId());
    		sp.addService(s);
    	}
    	
    	em.persist(sp);
    }

}
