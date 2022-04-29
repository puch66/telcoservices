package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the salesValue database table.
 * 
 */
@Entity
@Table(name="salesValue")
@NamedQuery(name="SalesValue.findAll", query="SELECT s FROM SalesValue s ORDER BY s.totalSale DESC, s.saleWithoutProd DESC")
public class SalesValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="package")
	private String package_;

	private int saleWithoutProd;

	private int totalSale;

	public SalesValue() {
	}

	public String getPackage_() {
		return this.package_;
	}

	public void setPackage_(String package_) {
		this.package_ = package_;
	}

	public int getSaleWithoutProd() {
		return this.saleWithoutProd;
	}

	public void setSaleWithoutProd(int saleWithoutProd) {
		this.saleWithoutProd = saleWithoutProd;
	}

	public int getTotalSale() {
		return this.totalSale;
	}

	public void setTotalSale(int totalSale) {
		this.totalSale = totalSale;
	}

}