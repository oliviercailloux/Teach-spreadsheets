package io.github.oliviercailloux.y2018.teach_spreadsheets;

import java.util.Objects;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Samuel COHEN (samuelcohen75)
 * 
 *         <p>
 *         <b> Contract 1 This class makes it possible to store a course : Name,
 *         Peak Code, CM hours, TD hours, CMTD hours, TP hours, CM groups / TD
 *         hours / etc., and the year of studies in which the course is inserted
 *         package io.github.oliviercailloux.y2018.teach_spreadsheets; (L3 Computing, M1
 *         Mathematics, L3 learning,...) </b>
 *         </p>
 */
public class Course {
	private String name = "", apogeeCode = "", yearOfStud = "";
	private double cmH = 0.0, tdH = 0.0 , cmtdH = 0.0 , tpH = 0.0;
	private int cmGrpNb = 0 , tdGrpNb = 0 , cmtdGrpNb = 0, tpGrpNb = 0;

	public Course(String name, String apogeeCode, String yearOfStud) {
		this.name = Objects.requireNonNull(name);
		this.apogeeCode = Objects.requireNonNull(apogeeCode);
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
	}

	@Override
	public String toString() {
		return "name : " + this.name + "  code : " + this.apogeeCode + "  year: " + this.yearOfStud + "\nCM hours: "
				+ this.cmH + "  TD hours: " + this.tdH + "  CMTD hour: " + this.cmtdH + "\nTP hours: " + this.tpH
				+ "  CM groups number: " + this.cmGrpNb + "  TD groups number: " + this.tdGrpNb
				+ "  CMTD groups number: " + this.cmtdGrpNb + "\nTP groups number: " + this.tpGrpNb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public String getapogeeCode() {
		return apogeeCode;
	}

	public void setapogeeCode(String apogeeCode) {
		this.apogeeCode = Objects.requireNonNull(apogeeCode);
	}

	public String getYearOfStud() {
		return yearOfStud;
	}

	public void setYearOfStud(String yearOfStud) {
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
	}

	public double getCM_Hour() {
		return cmH;
	}

	public void setCM_Hour(double cmH) {
		if (cmH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.cmH = cmH;
	}

	public double getTD_Hour() {
		return tdH;
	}

	public void setTD_Hour(double tdH) {
		if (tdH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.tdH = tdH;
	}

	public double getCMTD_Hour() {
		return cmtdH;
	}

	public void setCMTD_Hour(double cmtdH) {
		if (cmtdH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.cmtdH = cmtdH;
	}

	public double getTP_Hour() {
		return tpH;
	}

	public void setTP_Hour(double tpH) {
		if (tpH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.tpH = tpH;
	}

	public int getNbGrpCM() {
		return cmGrpNb;
	}

	public void setNbGrpCM(int cmGrpNb) {
		if (cmGrpNb < 0)
			throw new IllegalArgumentException("Negative number of groups !");
		this.cmGrpNb = cmGrpNb;
	}

	public int getNbGrpTD() {
		return tdGrpNb;
	}

	public void setNbGrpTD(int tdGrpNb) {
		if (tdGrpNb < 0)
			throw new IllegalArgumentException("Negative number of groups !");
		this.tdGrpNb = tdGrpNb;
	}

	public int getNbGrCMTD() {
		return cmtdGrpNb;
	}

	public void setNbGrpCMTD(int cmtdGrpNb) {
		if (cmtdGrpNb < 0)
			throw new IllegalArgumentException("Negative number of groups !");
		this.cmtdGrpNb = cmtdGrpNb;
	}

	public int getNbGrpTP() {
		return tpGrpNb;
	}

	public void setNbGrpTP(int tpGrpNb) {
		if (tpGrpNb < 0)
			throw new IllegalArgumentException("Negative number of groups !");
		this.tpGrpNb = tpGrpNb;
	}

}