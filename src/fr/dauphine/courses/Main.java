package fr.dauphine.courses;
public class Main {

	public static void main(String[] args) {
		Course c=new Course("samuel","14","2008",14,15,16,17,10,11,12,13);
		CoursePref cp = new CoursePref(c, "Samuel", "A", "B", "C", "Absent");
		CoursePref cp1 = new CoursePref(c, "Test", "A", "B", "C", "Absent");
		System.out.println(c.getName() + " CM : " + cp.getCmChoice());
		System.out.println(c.getName() + " TD : " + cp.getTdChoice());
		System.out.println(c.getName() + " CMTD : " + cp.getCmtdChoice());
		System.out.println(c.getName() + " TP : " + cp.getTpChoice());
		System.out.println("\n" + cp.toString());
		System.out.println("\n" + cp1.toString());
		
		System.out.println("\n" + c);
		// TODO Auto-generated method stub

	}

}
