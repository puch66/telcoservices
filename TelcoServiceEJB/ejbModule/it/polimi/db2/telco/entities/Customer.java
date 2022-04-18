package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="customer")
@NamedQuery(name="Customer.checkCredentials", query="SELECT c FROM Customer c  WHERE c.username = ?1 and c.password = ?2")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private String email;

	private int isInsolvent;

	private String password;

	//bi-directional many-to-one association to CustomOrder
	@OneToMany(mappedBy="customer")
	private List<CustomOrder> customOrders;

	public Customer() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsInsolvent() {
		return this.isInsolvent;
	}

	public void setIsInsolvent(int isInsolvent) {
		this.isInsolvent = isInsolvent;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<CustomOrder> getCustomOrders() {
		return this.customOrders;
	}

	public void setCustomOrders(List<CustomOrder> customOrders) {
		this.customOrders = customOrders;
	}

	public CustomOrder addCustomOrder(CustomOrder customOrder) {
		getCustomOrders().add(customOrder);
		customOrder.setCustomer(this);

		return customOrder;
	}

	public CustomOrder removeCustomOrder(CustomOrder customOrder) {
		getCustomOrders().remove(customOrder);
		customOrder.setCustomer(null);

		return customOrder;
	}

}