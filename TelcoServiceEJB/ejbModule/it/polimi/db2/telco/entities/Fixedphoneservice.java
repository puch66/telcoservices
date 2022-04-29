package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fixedphoneservice database table.
 * 
 */
@Entity
@DiscriminatorValue("Fixedphoneservice")
@NamedQuery(name="Fixedphoneservice.findAll", query="SELECT f FROM Fixedphoneservice f")
public class Fixedphoneservice extends Service implements Serializable {
	private static final long serialVersionUID = 1L;

	public Fixedphoneservice() {
	}

}