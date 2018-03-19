package fr.dauphine.courses;

/**
 * @author Tuan Nuam DAVAUX (tuannamdavaux), Samuel COHEN (samuelcohen75)
 * Contract 1 
 * This class makes it possible to store a course : Name, Peak Code, CM hours, TD 
 * hours, CMTD hours, TP hours, CM groups / TD hours / etc., and the
 * year of studies in which the course is inserted (L3 Computing, M1
 * Mathematics, L3 learning,...)
 */
public class Course {
	private String name, peakCode, yearOfStud;
	private double cmH, tdH, cmtdH, tpH;
	private int cmGrpNb, tdGrpNb, cmtdGrpNb, tpGrpNb;

	public Course(String name, String peakCode, String yearOfStud) {
		this.name = name;
		this.peakCode = peakCode;
		this.yearOfStud = yearOfStud;
	}

	public Course(String name, String peakCode, String yearOfStud, double cmH, double tdH, double cmtdH, double tpH,
			int cmGrpNb, int tdGrpNb, int cmtdGrpNb, int tpGrpNb) {
		this.name = name;
		this.peakCode = peakCode;
		this.yearOfStud = yearOfStud;
		this.cmH = cmH;
		this.tdH = tdH;
		this.cmtdH = cmtdH;
		this.tpH = tpH;
		this.cmGrpNb = cmGrpNb;
		this.tdGrpNb = tdGrpNb;
		this.cmtdGrpNb = cmtdGrpNb;
		this.tpGrpNb = tpGrpNb;
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
		this.name = name;
	}

	public String getPeakCode() {
		return peakCode;
	}

	public void setPeakCode(String peakCode) {
		this.peakCode = peakCode;
	}

	public String getYearOfStud() {
		return yearOfStud;
	}

	public void setYearOfStud(String yearOfStud) {
		this.yearOfStud = yearOfStud;
	}

	public double getCM_Hour() {
		return cmH;
	}

	public void setCM_Hour(double cmH) {
		this.cmH = cmH;
	}

	public double getTD_Hour() {
		return tdH;
	}

	public void setTD_Hour(double tdH) {
		this.tdH = tdH;
	}

	public double getCMTD_Hour() {
		return cmtdH;
	}

	public void setCMTD_Hour(double cmtdH) {
		this.cmtdH = cmtdH;
	}

	public double getTP_Hour() {
		return tpH;
	}

	public void setTP_Hour(double tpH) {
		this.tpH = tpH;
	}

	public int getNbGrpCM() {
		return cmGrpNb;
	}

	public void setNbGrpCM(int cmGrpNb) {
		this.cmGrpNb = cmGrpNb;
	}

	public int getNbGrpTD() {
		return tdGrpNb;
	}

	public void setNbGrpTD(int tdGrpNb) {
		this.tdGrpNb = tdGrpNb;
	}

	public int getNbGrCMTD() {
		return cmtdGrpNb;
	}

	public void setNbGrpCMTD(int cmtdGrpNb) {
		this.cmtdGrpNb = cmtdGrpNb;
	}

	public int getNbGrpTP() {
		return tpGrpNb;
	}

	public void setNbGrpTP(int tpGrpNb) {
		this.tpGrpNb = tpGrpNb;
	}

}
