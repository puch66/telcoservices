package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	private int fee;

	//bi-directional many-to-many association to ServicePackage
	@ManyToMany(mappedBy="products")
	private List<ServicePackage> servicePackages;

	//bi-directional many-to-many association to CustomOrder
	@ManyToMany
	@JoinTable(
		name="selectedProductsForOrder"
		, joinColumns={
			@JoinColumn(name="product")
			}
		, inverseJoinColumns={
			@JoinColumn(name="customOrder")
			}
		)
	private List<CustomOrder> customOrders;

	public Product() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFee() {
		return this.fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public List<ServicePackage> getServicePackages() {
		return this.servicePackages;
	}

	public void setServicePackages(List<ServicePackage> servicePackages) {
		this.servicePackages = servicePackages;
	}

	public List<CustomOrder> getCustomOrders() {
		return this.customOrders;
	}

	public void setCustomOrders(List<CustomOrder> customOrders) {
		this.customOrders = customOrders;
	}

}