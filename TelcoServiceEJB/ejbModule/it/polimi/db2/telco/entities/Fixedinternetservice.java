package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fixedinternetservice database table.
 * 
 */
@Entity
@Table(name="fixedinternetservice")
@NamedQuery(name="Fixedinternetservice.findAll", query="SELECT f FROM Fixedinternetservice f")
public class Fixedinternetservice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_fixed_internet")
	private int idFixedInternet;

	private int fee_extra_GB;

	private int num_GB;

	public Fixedinternetservice() {
	}

	public int getIdFixedInternet() {
		return this.idFixedInternet;
	}

	public void setIdFixedInternet(int idFixedInternet) {
		this.idFixedInternet = idFixedInternet;
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