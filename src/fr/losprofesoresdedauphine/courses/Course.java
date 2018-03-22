package fr.losprofesoresdedauphine.courses;

import java.util.Objects;

/**
 * @author Tuan Nam DAVAUX (tuannamdavaux), Samuel COHEN (samuelcohen75)
 *         
 * <p> <b> Contract 1 This class makes it possible to store a course : Name,
 *         Peak Code, CM hours, TD hours, CMTD hours, TP hours, CM groups / TD
 *         hours / etc., and the year of studies in which the course is inserted
 *      package fr.losprofesoresdedauphine.courses;
   (L3 Computing, M1 Mathematics, L3 learning,...) </b> </p>
 */
public class Course {
	private String name, peakCode, yearOfStud;
	private double cmH, tdH, cmtdH, tpH;
	private int cmGrpNb, tdGrpNb, cmtdGrpNb, tpGrpNb;

	public Course(String name, String peakCode, String yearOfStud) {
		this.name = Objects.requireNonNull(name);
		this.peakCode = Objects.requireNonNull(peakCode);
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
	}

	@Override
	public String toString() {
		return "name : " + this.name + "  code : " + this.peakCode + "  year: " + this.yearOfStud + "\nCM hours: "
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

	public String getPeakCode() {
		return peakCode;
	}

	public void setPeakCode(String peakCode) {
		this.peakCode = Objects.requireNonNull(peakCode);
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
		else
			this.cmH = cmH;
	}

	public double getTD_Hour() {
		return tdH;
	}

	public void setTD_Hour(double tdH) {
		if (tdH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		else
			this.tdH = tdH;
	}

	public double getCMTD_Hour() {
		return cmtdH;
	}

	public void setCMTD_Hour(double cmtdH) {
		if (cmtdH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		else
			this.cmtdH = cmtdH;
	}

	public double getTP_Hour() {
		return tpH;
	}

	public void setTP_Hour(double tpH) {
		if (tpH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		else
			this.tpH = tpH;
	}

	public int getNbGrpCM() {
		return cmGrpNb;
	}

	public void setNbGrpCM(int cmGrpNb) {
		if (cmGrpNb < 0)
			throw new IllegalArgumentException("Negative number of groups !");
		else
			this.cmGrpNb = cmGrpNb;
	}

	public int getNbGrpTD() {
		return tdGrpNb;
	}

	public void setNbGrpTD(int tdGrpNb) {
		if (tdGrpNb < 0)
			throw new IllegalArgumentException("Negative number of groups !");
		else
			this.tdGrpNb = tdGrpNb;
	}

	public int getNbGrCMTD() {
		return cmtdGrpNb;
	}

	public void setNbGrpCMTD(int cmtdGrpNb) {
		if (cmtdGrpNb < 0)
			throw new IllegalArgumentException("Negative number of groups !");
		else
			this.cmtdGrpNb = cmtdGrpNb;
	}

	public int getNbGrpTP() {
		return tpGrpNb;
	}

	public void setNbGrpTP(int tpGrpNb) {
		if (tpGrpNb < 0)
			throw new IllegalArgumentException("Negative number of groups !");
		else
			this.tpGrpNb = tpGrpNb;
	}

}
