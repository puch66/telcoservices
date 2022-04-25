package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the service database table.
 * 
 */
@Entity
@Table(name="service")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="service_type", discriminatorType = DiscriminatorType.STRING)
@NamedQuery(name="Service.findAvailableServices", query="SELECT s FROM Service s WHERE s.servicePackage is null")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to ServicePackage
	@ManyToOne
	@JoinColumn(name="serPackage")
	private ServicePackage servicePackage;

	//bi-directional many-to-many association to ServiceActivationSchedule
	@ManyToMany(mappedBy="services")
	private List<ServiceActivationSchedule> serviceActivationSchedules;

	public Service() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ServicePackage getServicePackage() {
		return this.servicePackage;
	}

	public void setServicePackage(ServicePackage servicePackage) {
		this.servicePackage = servicePackage;
	}

	public List<ServiceActivationSchedule> getServiceActivationSchedules() {
		return this.serviceActivationSchedules;
	}

	public void setServiceActivationSchedules(List<ServiceActivationSchedule> serviceActivationSchedules) {
		this.serviceActivationSchedules = serviceActivationSchedules;
	}

}