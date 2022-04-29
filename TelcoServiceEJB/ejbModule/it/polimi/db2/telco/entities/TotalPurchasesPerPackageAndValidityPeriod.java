package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the totalPurchasesPerPackageAndValidityPeriod database table.
 * 
 */
@Entity
@Table(name="totalPurchasesPerPackageAndValidityPeriod")
@NamedQuery(name="TotalPurchasesPerPackageAndValidityPeriod.findAll", query="SELECT t FROM TotalPurchasesPerPackageAndValidityPeriod t ORDER BY t.total DESC")
public class TotalPurchasesPerPackageAndValidityPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="package")
	private String package_;

	private int total;

	private int validity;

	public TotalPurchasesPerPackageAndValidityPeriod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPackage_() {
		return this.package_;
	}

	public void setPackage_(String package_) {
		this.package_ = package_;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getValidity() {
		return this.validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

}