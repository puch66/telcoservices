package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.ServicePackage;

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

}
