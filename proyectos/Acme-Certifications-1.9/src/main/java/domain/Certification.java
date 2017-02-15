/*
 * Certification.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Certification extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Certification() {
		super();

		exams = new HashSet<Exam>();
	}


	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	extinctionDate;
	private double	fee;


	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getExtinctionDate() {
		return extinctionDate;
	}

	public void setExtinctionDate(Date extinctionDate) {
		this.extinctionDate = extinctionDate;
	}

	@Min(0)
	@Digits(integer = 99, fraction = 2)
	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Exam>	exams;


	@NotEmpty
	@OneToMany(mappedBy = "certification", cascade = CascadeType.ALL)
	public Collection<Exam> getExams() {
		return exams;
	}

	public void setExams(Collection<Exam> exams) {
		this.exams = exams;
	}

	public void addExam(Exam exam) {
		exams.add(exam);
		exam.setCertification(this);
	}

	public void removeExam(Exam exam) {
		exams.remove(exam);
		exam.setCertification(null);
	}

}
