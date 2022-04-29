package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the totalPurchasesPerPackage database table.
 * 
 */
@Entity
@Table(name="totalPurchasesPerPackage")
@NamedQuery(name="TotalPurchasesPerPackage.findAll", query="SELECT t FROM TotalPurchasesPerPackage t ORDER BY t.total DESC")
public class TotalPurchasesPerPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="package")
	private String package_;

	private int total;

	public TotalPurchasesPerPackage() {
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

}