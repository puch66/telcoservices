package it.polimi.db2.telco.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.time.DateUtils;

import it.polimi.db2.telco.entities.CustomOrder;
import it.polimi.db2.telco.entities.Product;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.entities.ServiceActivationSchedule;

/**
 * Session Bean implementation class ServiceActivationScheduleService
 */
@Stateless
public class ServiceActivationScheduleService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public ServiceActivationScheduleService() {
    }

    public void createActivationSchedule(CustomOrder o) {
    	ServiceActivationSchedule sas = new ServiceActivationSchedule();
    	sas.setCustomer(o.getCustomer());
    	sas.setActivationDate(o.getStartDate());
    	sas.setDeactivationDate(DateUtils.addMonths(o.getStartDate(), o.getValidityPeriod().getDuration()));
    	em.persist(sas);
    	em.flush();
    	List<Product> productsToAdd = new ArrayList<>(o.getProducts());
    	for(Product p:productsToAdd) {
    		this.addProductToSas(p.getName(), sas.getId());
    	}
    	List<Service> servicesToAdd = new ArrayList<>(o.getServicePackageBean().getServices());
    	for(Service s:servicesToAdd) {
    		this.addServiceToSas(s.getId(), sas.getId());
    	}
    }
    
    private void addProductToSas(String p, int s) {
    	Product pr = em.find(Product.class,p);
    	ServiceActivationSchedule sas = em.find(ServiceActivationSchedule.class, s);
    	sas.addProduct(pr);
    }
    
    private void addServiceToSas(int s, int sa) {
    	Service service = em.find(Service.class,s);
    	ServiceActivationSchedule sas = em.find(ServiceActivationSchedule.class, sa);
    	sas.addService(service);
    }
}
