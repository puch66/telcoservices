package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.TotalPurchasesPerPackageAndValidityPeriod;

@Stateless
public class TotalPurchasesPerPackageAndValidityPeriodService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public TotalPurchasesPerPackageAndValidityPeriodService() {
    }

    public List<TotalPurchasesPerPackageAndValidityPeriod> findAll() {
    	em.getEntityManagerFactory().getCache().evictAll();
    	return em.createNamedQuery("TotalPurchasesPerPackageAndValidityPeriod.findAll", TotalPurchasesPerPackageAndValidityPeriod.class).getResultList();
    }
}
