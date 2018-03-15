package fr.dauphine.courses;
/**
 * 
 */

/**
 * @author davau
 *
 */
public class Course {
	private String nom, codeApogee, anneeEt;
	private double hCM, hTD, hCMTD, hTP;
	private int nbGrCM, nbGrTD, nbGrCMTD, nbGrTP;

	public Course(String nom, String codeApogee, String anneeEt) {
		this.nom = nom;
		this.codeApogee = codeApogee;
		this.anneeEt = anneeEt;
	}

	public Course(String nom, String codeApogee, String anneeEt, double hCM, double hTD, double hCMTD, double hTP,
			int nbGrCM, int nbGrTD, int nbGrCMTD, int nbGrTP) {
		this.nom = nom;
		this.codeApogee = codeApogee;
		this.anneeEt = anneeEt;
		this.hCM = hCM;
		this.hTD = hTD;
		this.hCMTD = hCMTD;
		this.hTP = hTP;
		this.nbGrCM = nbGrCM;
		this.nbGrTD = nbGrTD;
		this.nbGrCMTD = nbGrCMTD;
		this.nbGrTP = nbGrTP;
	}
	
	

@Override
public String toString() {
	// TODO Auto-generated method stub
	return "Nom : " + this.nom +"  code : "+ this.codeApogee+"  annee: " + this.anneeEt+ "\nnbHeuresCoursCommun: "+ this.hCM+ "  nbheuresTD: "+
			this.hTD+ "  nbheuresCMTD: "+this.hCMTD+ "\nnbheuresTP: "+this.hTP+ "  nbheuresGroupeCommun: "+this.nbGrCM+ "  nbheuresGroupeTD: "+this.nbGrTD
			+ "  nbheuresGroupeCMTD: "+this.nbGrCMTD+ "\nnbheuresGroupeTP: "+ this.nbGrTP;
}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodeApogee() {
		return codeApogee;
	}

	public void setCodeApogee(String codeApogee) {
		this.codeApogee = codeApogee;
	}

	public String getAnneeEt() {
		return anneeEt;
	}

	public void setAnneeEt(String anneeEt) {
		this.anneeEt = anneeEt;
	}

	public double gethCM() {
		return hCM;
	}

	public void sethCM(double hCM) {
		this.hCM = hCM;
	}

	public double gethTD() {
		return hTD;
	}

	public void sethTD(double hTD) {
		this.hTD = hTD;
	}

	public double gethCMTD() {
		return hCMTD;
	}

	public void sethCMTD(double hCMTD) {
		this.hCMTD = hCMTD;
	}

	public double gethTP() {
		return hTP;
	}

	public void sethTP(double hTP) {
		this.hTP = hTP;
	}

	public int getNbGrCM() {
		return nbGrCM;
	}

	public void setNbGrCM(int nbGrCM) {
		this.nbGrCM = nbGrCM;
	}

	public int getNbGrTD() {
		return nbGrTD;
	}

	public void setNbGrTD(int nbGrTD) {
		this.nbGrTD = nbGrTD;
	}

	public int getNbGrCMTD() {
		return nbGrCMTD;
	}

	public void setNbGrCMTD(int nbGrCMTD) {
		this.nbGrCMTD = nbGrCMTD;
	}

	public int getNbGrTP() {
		return nbGrTP;
	}

	public void setNbGrTP(int nbGrTP) {
		this.nbGrTP = nbGrTP;
	}
	
	

}
