
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Constructor

	public Finder() {
		super();
	}


	// Attributes

	private String	destinationCity;
	private Double	minimumPrice;
	private Double	maximumPrice;
	private String	keyWord;
	private Date	lastUpdate;


	@NotBlank
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public Double getMinimumPrice() {
		return minimumPrice;
	}
	public void setMinimumPrice(Double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public Double getMaximumPrice() {
		return maximumPrice;
	}
	public void setMaximumPrice(Double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public String getKeyWord() {

		return keyWord;
	}

	public void setKeyWord(String keyWord) {

		this.keyWord = keyWord;
	}

	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	//Relationships

	private Tenant					tenant;
	private Collection<Property>	cache;


	@Valid
	@OneToOne(optional = true)
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	@Valid
	@ManyToMany()
	public Collection<Property> getCache() {
		return cache;
	}
	public void setCache(Collection<Property> cache) {
		this.cache = cache;

	}

}
