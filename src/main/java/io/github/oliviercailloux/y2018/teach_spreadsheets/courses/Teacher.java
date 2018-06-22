package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

import java.util.Objects;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Louis Fontaine (fontlo15)
 * 
 *         <p>
 *         <b> This class represents a teacher. </b> Each teacher is represented
 *         by an unique id.
 *         </p>
 */
public class Teacher {

	private int id;
	private String name = "", firstName = "", adress = "", postCode = "",
			city = "", personalPhone = "", mobilePhone = "", personalMail = "",
			dauphineMail = "", status = "", dauphinePhone = "", desk = "";

	public Teacher() {

	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void set(int index, String data) {
		assert data != null;

		switch (index) {
			case 0 :
				setId(Integer.parseInt(data));
				break;
			case 1 :
				setName(data);
				break;
			case 2 :
				setFirstName(data);
				break;
			case 3 :
				setAdress(data);
				break;
			case 4 :
				setPostCode(data);
				break;
			case 5 :
				setCity(data);
				break;
			case 6 :
				setPersonalPhone(data);
				break;
			case 7 :
				setMobilePhone(data);
				break;
			case 8 :
				setPersonalMail(data);
				break;
			case 9 :
				setDauphineMail(data);
				break;
			case 10 :
				setStatus(data);
				break;
			case 11 :
				setDauphinePhone(data);
				break;
			case 12 :
				setDesk(data);
				break;
			default :
				throw new IllegalArgumentException();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = Objects.requireNonNull(firstName);
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = Objects.requireNonNull(adress);
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = Objects.requireNonNull(postCode);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = Objects.requireNonNull(city);
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = Objects.requireNonNull(personalPhone);
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = Objects.requireNonNull(mobilePhone);
	}

	public String getPersonalMail() {
		return personalMail;
	}

	public void setPersonalMail(String personalMail) {
		this.personalMail = Objects.requireNonNull(personalMail);
	}

	public String getDauphineMail() {
		return dauphineMail;
	}

	public void setDauphineMail(String dauphineMail) {
		this.dauphineMail = Objects.requireNonNull(dauphineMail);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = Objects.requireNonNull(status);
	}

	public String getDauphinePhone() {
		return dauphinePhone;
	}

	public void setDauphinePhone(String dauphinePhone) {
		this.dauphinePhone = Objects.requireNonNull(dauphinePhone);
	}

	public String getDesk() {
		return desk;
	}

	public void setDesk(String desk) {
		this.desk = Objects.requireNonNull(desk);
	}

}
