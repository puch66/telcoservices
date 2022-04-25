package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.exceptions.BadCredentialsException;

/**
 * Session Bean implementation class EmployeeService
 */
@Stateless
public class EmployeeService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;

    public EmployeeService() {
    }

	public Employee checkCredentials(String username, String password) throws BadCredentialsException, NonUniqueResultException {
		List<Employee> uList = null;
		try {
			uList = em.createNamedQuery("Employee.checkCredentials", Employee.class).setParameter(1, username).setParameter(2, password).getResultList();
		} catch (PersistenceException e) {
			throw new BadCredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		else
			throw new NonUniqueResultException("More than one employee registered with same credentials");
	}
}
