package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the auditingTable database table.
 * 
 */
@Entity
@Table(name="auditingTable")
@NamedQuery(name="AuditingTable.findAll", query="SELECT a FROM AuditingTable a")
public class AuditingTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int amount;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	private Date rejectionDateTime;

	private String username;

	public AuditingTable() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRejectionDateTime() {
		return this.rejectionDateTime;
	}

	public void setRejectionDateTime(Date rejectionDateTime) {
		this.rejectionDateTime = rejectionDateTime;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}