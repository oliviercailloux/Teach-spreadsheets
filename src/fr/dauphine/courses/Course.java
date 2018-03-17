package fr.dauphine.courses;
/**
 * 
 */

/**
 * @author davau
 *
 */
public class Course {
	private String name, peakCode, yearOfStud;
	private double cMH, tDH, cMTDH, tPH;
	private int cMGrNB, tDGrNB, cMTDGrNB, tPGrNB;

	public Course(String name, String peakCode, String yearOfStud) {
		this.name = name;
		this.peakCode = peakCode;
		this.yearOfStud = yearOfStud;
	}

	public Course(String name, String peakCode, String yearOfStud, double cMH, double tDH, double cMTDH, double tPH,
			int cMGrNB, int tDGrNB, int cMTDGrNB, int tPGrNB) {
		this.name = name;
		this.peakCode = peakCode;
		this.yearOfStud = yearOfStud;
		this.cMH = cMH;
		this.tDH = tDH;
		this.cMTDH = cMTDH;
		this.tPH = tPH;
		this.cMGrNB = cMGrNB;
		this.tDGrNB = tDGrNB;
		this.cMTDGrNB = cMTDGrNB;
		this.tPGrNB = tPGrNB;
	}
	
	

@Override
public String toString() {
	// TODO Auto-generated method stub
	return "name : " + this.name +"  code : "+ this.peakCode+"  year: " + this.yearOfStud+ "\nCM hours: "+ this.cMH+ "  TD hours: "+
			this.tDH+ "  CMTD hour: "+this.cMTDH+ "\nTP hours: "+this.tPH+ "  CM groups number: "+this.cMGrNB+ "  TD groups number: "+this.tDGrNB
			+ "  CMTD groups number: "+this.cMTDGrNB+ "\nTP groups number: "+ this.tPGrNB;
}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getpeakCode() {
		return peakCode;
	}

	public void setpeakCode(String peakCode) {
		this.peakCode = peakCode;
	}

	public String getyearOfStud() {
		return yearOfStud;
	}

	public void setyearOfStud(String yearOfStud) {
		this.yearOfStud = yearOfStud;
	}

	public double gethCM() {
		return cMH;
	}

	public void sethCM(double cMH) {
		this.cMH = cMH;
	}

	public double gethTD() {
		return tDH;
	}

	public void sethTD(double tDH) {
		this.tDH = tDH;
	}

	public double gethCMTD() {
		return cMTDH;
	}

	public void sethCMTD(double cMTDH) {
		this.cMTDH = cMTDH;
	}

	public double gethTP() {
		return tPH;
	}

	public void sethTP(double tPH) {
		this.tPH = tPH;
	}

	public int getNbGrCM() {
		return cMGrNB;
	}

	public void setNbGrCM(int cMGrNB) {
		this.cMGrNB = cMGrNB;
	}

	public int getNbGrTD() {
		return tDGrNB;
	}

	public void setNbGrTD(int tDGrNB) {
		this.tDGrNB = tDGrNB;
	}

	public int getNbGrCMTD() {
		return cMTDGrNB;
	}

	public void setNbGrCMTD(int cMTDGrNB) {
		this.cMTDGrNB = cMTDGrNB;
	}

	public int getNbGrTP() {
		return tPGrNB;
	}

	public void setNbGrTP(int tPGrNB) {
		this.tPGrNB = tPGrNB;
	}
	
	

}
