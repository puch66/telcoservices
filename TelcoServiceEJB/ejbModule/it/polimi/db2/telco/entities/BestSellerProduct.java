package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bestSellerProduct database table.
 * 
 */
@Entity
@Table(name="bestSellerProduct")
@NamedQuery(name="BestSellerProduct.findAll", query="SELECT b FROM BestSellerProduct b")
public class BestSellerProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String product;

	private int totalSales;

	public BestSellerProduct() {
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getTotalSales() {
		return this.totalSales;
	}

	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}

}