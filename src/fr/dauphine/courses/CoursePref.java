package fr.dauphine.courses;

public class CoursePref {

	private String course;
	
	private String enseignant;
	
	private String choixCM;
	
	private String choixTD;
	
	private String choixCMTD;
	
	private String choixTP;
	

	public CoursePref() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CoursePref(String course, String enseignant, String choixCM, String choixTD, String choixCMTD, String choixTP) {
		this.course = course;
		this.enseignant = enseignant;
		this.choixCM = choixCM;
		this.choixTD = choixTD;
		this.choixCMTD = choixCMTD;
		this.choixTP = choixTP;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(String enseignant) {
		this.enseignant = enseignant;
	}

	public String getchoixCM() {
		return choixCM;
	}

	public void setchoixCM(String choixCM) {
		this.choixCM = choixCM;
	}

	public String getchoixTD() {
		return choixTD;
	}

	public void setchoixTD(String choixTD) {
		this.choixTD = choixTD;
	}

	public String getchoixCMTD() {
		return choixCMTD;
	}

	public void setchoixCMTD(String choixCMTD) {
		this.choixCMTD = choixCMTD;
	}

	public String getchoixTP() {
		return choixTP;
	}

	public void setchoixTP(String choixTP) {
		this.choixTP = choixTP;
	}

	@Override
	public String toString() {
		return "Coursechoix [course=" + course + ", enseignant=" + enseignant + ", choixCM=" + choixCM + ", choixTD="
				+ choixTD + ", choixCMTD=" + choixCMTD + ", choixTP=" + choixTP + "]";
	}
	
	
}
