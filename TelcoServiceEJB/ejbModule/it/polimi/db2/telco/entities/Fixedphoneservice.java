package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fixedphoneservice database table.
 * 
 */
@Entity
@Table(name="fixedphoneservice")
@NamedQuery(name="Fixedphoneservice.findAll", query="SELECT f FROM Fixedphoneservice f")
public class Fixedphoneservice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_fixed_phone")
	private int idFixedPhone;

	public Fixedphoneservice() {
	}

	public int getIdFixedPhone() {
		return this.idFixedPhone;
	}

	public void setIdFixedPhone(int idFixedPhone) {
		this.idFixedPhone = idFixedPhone;
	}

}