package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the validityPeriod database table.
 * 
 */
@Entity
@Table(name="validityPeriod")
@NamedQuery(name="ValidityPeriod.findAll", query="SELECT v FROM ValidityPeriod v")
public class ValidityPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int duration;

	private int fee;

	//bi-directional many-to-one association to CustomOrder
	@OneToMany(mappedBy="validityPeriod")
	private List<CustomOrder> customOrders;

	//bi-directional many-to-one association to ServicePackage
	@ManyToOne
	@JoinColumn(name="servicePackage")
	private ServicePackage servicePackageBean;

	public ValidityPeriod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFee() {
		return this.fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public List<CustomOrder> getCustomOrders() {
		return this.customOrders;
	}

	public void setCustomOrders(List<CustomOrder> customOrders) {
		this.customOrders = customOrders;
	}

	public CustomOrder addCustomOrder(CustomOrder customOrder) {
		getCustomOrders().add(customOrder);
		customOrder.setValidityPeriod(this);

		return customOrder;
	}

	public CustomOrder removeCustomOrder(CustomOrder customOrder) {
		getCustomOrders().remove(customOrder);
		customOrder.setValidityPeriod(null);

		return customOrder;
	}

	public ServicePackage getServicePackageBean() {
		return this.servicePackageBean;
	}

	public void setServicePackageBean(ServicePackage servicePackageBean) {
		this.servicePackageBean = servicePackageBean;
	}

}