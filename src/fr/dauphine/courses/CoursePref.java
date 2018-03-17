package fr.dauphine.courses;

public class CoursePref {

	private String course;
	
	private String teacher;
	
	private String cMChoice;
	
	private String tDChoice;
	
	private String cMTDChoice;
	
	private String tPChoice;
	

	public CoursePref() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CoursePref(String course, String teacher, String cMChoice, String tDChoice, String cMTDChoice, String tPChoice) {
		this.course = course;
		this.teacher = teacher;
		this.cMChoice = cMChoice;
		this.tDChoice = tDChoice;
		this.cMTDChoice = cMTDChoice;
		this.tPChoice = tPChoice;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getEnseignant() {
		return teacher;
	}

	public void setEnseignant(String teacher) {
		this.teacher = teacher;
	}

	public String getchoixCM() {
		return cMChoice;
	}

	public void setchoixCM(String cMChoice) {
		this.cMChoice = cMChoice;
	}

	public String getchoixTD() {
		return tDChoice;
	}

	public void setchoixTD(String tDChoice) {
		this.tDChoice = tDChoice;
	}

	public String getchoixCMTD() {
		return cMTDChoice;
	}

	public void setchoixCMTD(String cMTDChoice) {
		this.cMTDChoice = cMTDChoice;
	}

	public String getchoixTP() {
		return tPChoice;
	}

	public void setchoixTP(String tPChoice) {
		this.tPChoice = tPChoice;
	}

	@Override
	public String toString() {
		return "Coursechoix [course=" + course + ", teacher=" + teacher + ", cMChoice=" + cMChoice + ", tDChoice="
				+ tDChoice + ", cMTDChoice=" + cMTDChoice + ", tPChoice=" + tPChoice + "]";
	}
	
	
}
