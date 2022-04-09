package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mobileinternetservice database table.
 * 
 */
@Entity
@Table(name="mobileinternetservice")
@NamedQuery(name="Mobileinternetservice.findAll", query="SELECT m FROM Mobileinternetservice m")
public class Mobileinternetservice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_mobile_internet")
	private int idMobileInternet;

	private int fee_extra_GB;

	private int num_GB;

	public Mobileinternetservice() {
	}

	public int getIdMobileInternet() {
		return this.idMobileInternet;
	}

	public void setIdMobileInternet(int idMobileInternet) {
		this.idMobileInternet = idMobileInternet;
	}

	public int getFee_extra_GB() {
		return this.fee_extra_GB;
	}

	public void setFee_extra_GB(int fee_extra_GB) {
		this.fee_extra_GB = fee_extra_GB;
	}

	public int getNum_GB() {
		return this.num_GB;
	}

	public void setNum_GB(int num_GB) {
		this.num_GB = num_GB;
	}

}