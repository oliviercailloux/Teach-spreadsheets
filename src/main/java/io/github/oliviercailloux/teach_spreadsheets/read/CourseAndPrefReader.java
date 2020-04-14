package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.LinkedHashSet;

import org.apache.commons.lang3.StringUtils;

import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CourseAndPrefReader {
	
	private final static String COURSTD = "CMTD";
	private final static String COURSTP = "CMTP";
	private final static String TD = "TD";
	private final static String TP = "TP";
	
	private final static int FIRST_COURSE_S1_COL=1;
	private final static int FIRST_COURSE_S1_ROW=3;
	private final static int FIRST_COURSE_S2_COL=13;
	private final static int FIRST_COURSE_S2_ROW=3;
	
	private final static String semesterPosition="G1";
	
	private boolean flagCM;
	private boolean flagTD;
	private boolean flagCMTD;
	private boolean flagTP;
	private boolean flagCMTP;
	
	int currentCol=FIRST_COURSE_S1_COL;
	int currentRow=FIRST_COURSE_S1_ROW;
	
	
	int currentSemester=1;

	LinkedHashSet<Course> courseList;
	
	public static CourseAndPrefReader newInstance(){
		return new CourseAndPrefReader();
	}
	private CourseAndPrefReader() {
		courseList = new LinkedHashSet<>();
	}

	public boolean isThereANextCourse(Table sheet) {
		Cell cell;
		cell=sheet.getCellByPosition(currentCol,currentRow);
		String test=cell.getDisplayText();
		return !"".equals(test) && test!=null;
	}
	
	public ImmutableSet<CoursePref> readSemester(Table sheet, Teacher teacher) {
		LinkedHashSet<CoursePref> coursePrefList= new LinkedHashSet<>();
		while(isThereANextCourse(sheet)){
			
			Course.Builder courseBuilder=Course.Builder.newInstance();
			setInfoCourse(sheet,courseBuilder,currentCol,currentRow,currentSemester);
			Course course=courseBuilder.build();
			courseList.add(course);
			
			CoursePref.Builder prefBuilder=CoursePref.Builder.newInstance(course, teacher);
			setInfoPref(sheet,prefBuilder,currentCol+8,currentRow); // Beware, there are hidden columns in the ods file.
			coursePrefList.add(prefBuilder.build());
			currentRow++;
		}
		currentCol=FIRST_COURSE_S2_COL;
		currentRow=FIRST_COURSE_S2_ROW;
		currentSemester = 2;
		
		return ImmutableSet.copyOf(coursePrefList);
		
	}
	
	public void setInfoPref(Table sheet,CoursePref.Builder prefBuilder,int j,int i) {
		prefBuilder.setPrefCM(readPref(sheet,j,i,flagCM));
		prefBuilder.setPrefTD(readPref(sheet,j+1,i,flagTD));
		prefBuilder.setPrefCMTD(readPref(sheet,j+1,i,flagCMTD));
		prefBuilder.setPrefTP(readPref(sheet,j+2,i,flagTP));
		prefBuilder.setPrefCMTP(readPref(sheet,j+2,i,flagCMTP));
		
		Cell actualCell = sheet.getCellByPosition(j+3, i);
		String cellText = actualCell.getDisplayText();
		if (!isDiagonalBorder(sheet, j, i) && !"".equals(cellText) && cellText != null) {
			String[] cellData = cellText.split(" ");
			int value;
			for(int k=0;k<cellData.length;k++) {
				if(k<cellData.length-1 && StringUtils.isNumeric(cellData[k])) {
					value=Integer.parseInt(cellData[k]);
					if(cellData[k+1].equals("CMTD")) {
						prefBuilder.setPrefNbGroupsCMTD(value);
					}
					if(cellData[k+1].equals("CMTP")) {
						prefBuilder.setPrefNbGroupsCMTP(value);
					}
					if(cellData[k+1].equals("TD")) {
						prefBuilder.setPrefNbGroupsTD(value);
					}
					if(cellData[k+1].equals("TP")) {
						prefBuilder.setPrefNbGroupsTP(value);		
					}
				}
			}
			
		}
			
	}
	
	private Preference readPref(Table sheet, int j, int i, boolean flag) {
		if(!flag) {
			return Preference.UNSPECIFIED;
		}
		if (isDiagonalBorder(sheet, j, i)) {
			return Preference.UNSPECIFIED;
		}
		Cell actualCell = sheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();
		if(cellText==null || cellText.equals("")) {
			return Preference.UNSPECIFIED;
		}
		String[] choice = cellText.split(" ");
		if(choice.length !=2) {
			throw new IllegalStateException("Preference at "+sheet.getTableName()+" "+i+","+j+" is not in a valid format");
		}
		if(choice[1].equals("A")){
			return Preference.A;
		}
		else if(choice[1].equals("B")){
			return Preference.B;
		}
		else if(choice[1].equals("C")){
			return Preference.C;
		}
		else {
			return Preference.UNSPECIFIED;
		}
	}
	public void setInfoCourse(Table currentSheet,Course.Builder courseBuilder, int currentCol,int currentRow,int semester) {
		flagCM=false;
		flagTD=false;
		flagCMTD=false;
		flagTP=false;
		flagCMTP=false;
		
		int j =currentCol,i=currentRow;
		Cell actualCell = currentSheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();
		String[] cellData;
		
		courseBuilder.setSemester(semester);

		courseBuilder.setName(cellText.replaceAll("\n", " "));
		
		actualCell=currentSheet.getCellByPosition(semesterPosition);
		cellData=actualCell.getDisplayText().split(" ");
		if(cellData.length!=2) {
			throw new IllegalStateException("The semester cell isn't in the right format");
		}
		courseBuilder.setStudyYear(cellData[1]);

		j+=4;
		
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbMinutesCM(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			courseBuilder.setNbMinutesCM(hoursToMinutes(hourTab[0]));
			courseBuilder.setCountGroupsCM(hoursToMinutes(hourTab[0]));
			flagCM=true;
		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbMinutesTD(0);
			courseBuilder.setNbMinutesCMTD(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			if (hourStr.contains(COURSTD)) {
				courseBuilder.setNbMinutesCMTD(hoursToMinutes(hourTab[0]));
				flagCMTD=true;
			} else if (hourStr.contains(TD)) {
				courseBuilder.setNbMinutesTD(hoursToMinutes(hourTab[0]));
				flagTD=true;
			}
		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbMinutesTP(0);
			courseBuilder.setCountGroupsCMTP(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			if (hourStr.contains(COURSTP)) {
				courseBuilder.setNbMinutesCMTP(hoursToMinutes(hourTab[0]));
				flagCMTP=true;
			} else if (hourStr.contains(TP)) {
				courseBuilder.setNbMinutesTP(hoursToMinutes(hourTab[0]));
				flagTP=true;
			}

		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();
		
		courseBuilder.setCountGroupsCM(1);
		
		if (isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setCountGroupsCMTD(0);
			courseBuilder.setCountGroupsCMTP(0);
			courseBuilder.setCountGroupsTD(0);
			courseBuilder.setCountGroupsTP(0);

		} else {
			cellData = cellText.split(" ");
			int value;
			for(int k=0;k<cellData.length;k++) {
				if(k<cellData.length-1 && StringUtils.isNumeric(cellData[k])) {
					value=Integer.parseInt(cellData[k]);
					if(cellData[k+1].equals("CMTD")) {
						courseBuilder.setCountGroupsCMTD(value);
					}
					if(cellData[k+1].equals("CMTP")) {
						courseBuilder.setCountGroupsCMTP(value);
					}
					if(cellData[k+1].equals("TD")) {
						courseBuilder.setCountGroupsTD(value);
					}
					if(cellData[k+1].equals("TP")) {
						courseBuilder.setCountGroupsTD(value);			
					}
				}
			}
			
		}
		

	}
	public boolean isDiagonalBorder(Table currentSheet, String cellPosition) {
		/*
		 * There is a problem with ODFTookit, their function getBorder return NULL if
		 * the border doesn't exists, but if there is a border, It doesn't return the
		 * description but a NumberFormatException, so the catch fix it
		 */
		Cell cell = currentSheet.getCellByPosition(cellPosition);
		if (cell == null)
			return false;
		try {
			cell.getBorder(CellBordersType.DIAGONALBLTR);
		} catch (@SuppressWarnings("unused") NullPointerException e) {
			return false;
		} catch (@SuppressWarnings("unused") NumberFormatException z) {
			return true;
		}
		return false;
	}

	public boolean isDiagonalBorder(Table currentSheet, int columnIndex, int rowIndex) {
		Cell cell = currentSheet.getCellByPosition(columnIndex, rowIndex);
		if (cell == null)
			return false;
		try {
			cell.getBorder(CellBordersType.DIAGONALBLTR);
		} catch (@SuppressWarnings("unused") NullPointerException e) {
			return false;
		} catch (@SuppressWarnings("unused") NumberFormatException z) {
			return true;
		}
		return false;
	}
	
	private int hoursToMinutes(String hours){
		return (int)(Double.parseDouble(hours)*60); 	
	}
	
	
	
		
	}

	
	




