
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	//Constructor

	public SystemConfiguration() {
		super();
	}


	//Attributes

	private double	fee;
	private String	VATNumber;


	@Min(0)
	@NotNull
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

	@NotBlank
	public String getVATNumber() {
		return VATNumber;
	}
	public void setVATNumber(String VATNumber) {
		this.VATNumber = VATNumber;
	}

}
