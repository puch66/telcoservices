package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the serviceActivationSchedule database table.
 * 
 */
@Entity
@Table(name="serviceActivationSchedule")
@NamedQuery(name="ServiceActivationSchedule.findAll", query="SELECT s FROM ServiceActivationSchedule s")
public class ServiceActivationSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date activationDate;

	@Temporal(TemporalType.DATE)
	private Date deactivationDate;

	//bi-directional many-to-many association to Product
	@ManyToMany
	@JoinTable(
		name="productsForActivationSchedule"
		, joinColumns={
			@JoinColumn(name="activationSchedule")
			}
		, inverseJoinColumns={
			@JoinColumn(name="product")
			}
		)
	private List<Product> products;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="username")
	private Customer customer;

	//bi-directional many-to-many association to Service
	@ManyToMany
	@JoinTable(
		name="servicesForActivationSchedule"
		, joinColumns={
			@JoinColumn(name="activationSchedule")
			}
		, inverseJoinColumns={
			@JoinColumn(name="service")
			}
		)
	private List<Service> services;

	public ServiceActivationSchedule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getActivationDate() {
		return this.activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getDeactivationDate() {
		return this.deactivationDate;
	}

	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Service> getServices() {
		return this.services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

}