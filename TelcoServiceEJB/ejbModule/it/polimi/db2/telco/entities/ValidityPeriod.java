package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


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

	public ServicePackage getServicePackageBean() {
		return this.servicePackageBean;
	}

	public void setServicePackageBean(ServicePackage servicePackageBean) {
		this.servicePackageBean = servicePackageBean;
	}

}