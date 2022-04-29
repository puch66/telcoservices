package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the customOrder database table.
 * 
 */
@Entity
@Table(name="customOrder")
@NamedQuery(name="CustomOrder.findRejectedOrders", query="SELECT o FROM CustomOrder o WHERE o.customer = ?1 AND o.isValid <> 0")
@NamedQuery(name="CustomOrder.findAllRejectedOrders", query="SELECT o FROM CustomOrder o WHERE o.isValid <> 0")
public class CustomOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	private int isValid;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	private int totalValue;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="username")
	private Customer customer;

	//bi-directional many-to-one association to ServicePackage
	@ManyToOne
	@JoinColumn(name="servicePackage")
	private ServicePackage servicePackageBean;

	//bi-directional many-to-one association to ValidityPeriod
	@ManyToOne
	@JoinColumn(name="validity")
	private ValidityPeriod validityPeriod;

	//bi-directional many-to-many association to Product
	@ManyToMany(mappedBy="customOrders")
	private List<Product> products;

	public CustomOrder() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getIsValid() {
		return this.isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getTotalValue() {
		return this.totalValue;
	}

	public void setTotalValue(int totalValue) {
		this.totalValue = totalValue;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ServicePackage getServicePackageBean() {
		return this.servicePackageBean;
	}

	public void setServicePackageBean(ServicePackage servicePackageBean) {
		this.servicePackageBean = servicePackageBean;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product p) {
		this.getProducts().add(p);
		p.getCustomOrders().add(this);
	}

}