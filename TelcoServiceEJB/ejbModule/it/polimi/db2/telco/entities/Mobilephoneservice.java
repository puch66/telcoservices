package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mobilephoneservice database table.
 * 
 */
@Entity
@DiscriminatorValue("Mobilephoneservice")
@NamedQuery(name="Mobilephoneservice.findAll", query="SELECT m FROM Mobilephoneservice m")
public class Mobilephoneservice extends Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="fee_extra_min")
	private int feeExtraMin;

	private int fee_extra_SMS;

	@Column(name="num_minutes")
	private int numMinutes;

	private int num_SMS;

	public Mobilephoneservice() {
	}

	public int getFeeExtraMin() {
		return this.feeExtraMin;
	}

	public void setFeeExtraMin(int feeExtraMin) {
		this.feeExtraMin = feeExtraMin;
	}

	public int getFee_extra_SMS() {
		return this.fee_extra_SMS;
	}

	public void setFee_extra_SMS(int fee_extra_SMS) {
		this.fee_extra_SMS = fee_extra_SMS;
	}

	public int getNumMinutes() {
		return this.numMinutes;
	}

	public void setNumMinutes(int numMinutes) {
		this.numMinutes = numMinutes;
	}

	public int getNum_SMS() {
		return this.num_SMS;
	}

	public void setNum_SMS(int num_SMS) {
		this.num_SMS = num_SMS;
	}

}