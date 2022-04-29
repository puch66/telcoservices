package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the averageProductsSoldForPackage database table.
 * 
 */
@Entity
@Table(name="averageProductsSoldForPackage")
@NamedQuery(name="AverageProductsSoldForPackage.findAll", query="SELECT a FROM AverageProductsSoldForPackage a ORDER BY a.averageProducts DESC")
public class AverageProductsSoldForPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="package")
	private String package_;

	private float averageProducts;

	public AverageProductsSoldForPackage() {
	}

	public String getPackage_() {
		return this.package_;
	}

	public void setPackage_(String package_) {
		this.package_ = package_;
	}

	public float getAverageProducts() {
		return this.averageProducts;
	}

	public void setAverageProducts(float averageProducts) {
		this.averageProducts = averageProducts;
	}

}