package ITKN177Rakhman;

import java.time.LocalDate;
import java.time.Period;

public class User {
	private Long id;
	private String name;
	private String surname;
	private LocalDate dateOfBirth;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public LocalDate getDateOfBirthday() {
		return dateOfBirth;
	}
	public void setDateOfBirthday(LocalDate dateOfBirthday) {
		this.dateOfBirth = dateOfBirthday;
	}
	
	public Object getFullName() {
		return new StringBuilder().append(getName()).append(", ").append(getSurname()).toString();
	}
	
	public int getAge(LocalDate dateOfBirth) {
		if (dateOfBirth != null) {
			if(Period.between(dateOfBirth, LocalDate.now()).getYears() < 0) {
				return 0;
			}
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        } else {
            return 0;
        }
	}
	
	
}
	
