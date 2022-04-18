package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the servicePackage database table.
 * 
 */
@Entity
@Table(name="servicePackage")
@NamedQuery(name="ServicePackage.findAll", query="SELECT s FROM ServicePackage s")
@NamedQuery(name="ServicePackage.findAllPackageNames", query="SELECT s.name FROM ServicePackage s")
@NamedQuery(name="ServicePackage.findFormPackage", query="SELECT s FROM ServicePackage s WHERE s.name = ?1")
public class ServicePackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to CustomOrder
	@OneToMany(mappedBy="servicePackageBean")
	private List<CustomOrder> customOrders;

	//bi-directional many-to-many association to Product
	@ManyToMany
	@JoinTable(
		name="optProductsForPackage"
		, joinColumns={
			@JoinColumn(name="servicepackage")
			}
		, inverseJoinColumns={
			@JoinColumn(name="product")
			}
		)
	private List<Product> products;

	//bi-directional many-to-one association to Service
	@OneToMany(mappedBy="servicePackage")
	private List<Service> services;

	//bi-directional many-to-one association to ValidityPeriod
	@OneToMany(mappedBy="servicePackageBean")
	private List<ValidityPeriod> validityPeriods;

	public ServicePackage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CustomOrder> getCustomOrders() {
		return this.customOrders;
	}

	public void setCustomOrders(List<CustomOrder> customOrders) {
		this.customOrders = customOrders;
	}

	public CustomOrder addCustomOrder(CustomOrder customOrder) {
		getCustomOrders().add(customOrder);
		customOrder.setServicePackageBean(this);

		return customOrder;
	}

	public CustomOrder removeCustomOrder(CustomOrder customOrder) {
		getCustomOrders().remove(customOrder);
		customOrder.setServicePackageBean(null);

		return customOrder;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Service> getServices() {
		return this.services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public Service addService(Service service) {
		getServices().add(service);
		service.setServicePackage(this);

		return service;
	}

	public Service removeService(Service service) {
		getServices().remove(service);
		service.setServicePackage(null);

		return service;
	}

	public List<ValidityPeriod> getValidityPeriods() {
		return this.validityPeriods;
	}

	public void setValidityPeriods(List<ValidityPeriod> validityPeriods) {
		this.validityPeriods = validityPeriods;
	}

	public ValidityPeriod addValidityPeriod(ValidityPeriod validityPeriod) {
		getValidityPeriods().add(validityPeriod);
		validityPeriod.setServicePackageBean(this);

		return validityPeriod;
	}

	public ValidityPeriod removeValidityPeriod(ValidityPeriod validityPeriod) {
		getValidityPeriods().remove(validityPeriod);
		validityPeriod.setServicePackageBean(null);

		return validityPeriod;
	}

}