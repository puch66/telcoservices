package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@Table(name="employee")
@NamedQuery(name="Employee.checkCredentials", query="SELECT e FROM Employee e  WHERE e.username = ?1 and e.password = ?2")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	@Column(nullable = false)
	private String password;

	public Employee() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}