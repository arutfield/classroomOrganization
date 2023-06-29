package classroomSorting;

import java.util.LinkedList;

public class Classroom {

	private final String teacherName;
	private final LinkedList<String> studentNames = new LinkedList<String>();
	private boolean isEll;
	private boolean isIEP;
	private int totalFemaleStudents = 0;
	
	public Classroom(String teacher) {
		this.teacherName = teacher;
	}
	
	public void enableEll() {
		isEll = true;
	}
	
	public void enableIEP() {
		isIEP = true;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public boolean IsEll() {
		return isEll;
	}

	public boolean IsIEP() {
		return isIEP;
	}

	public void addStudent(String name, boolean isFemale) {
		studentNames.add(name);
		if (isFemale)
			totalFemaleStudents++;
	}

	public LinkedList<String> getStudentNames() {
		
		return studentNames;
	}

	public void removeStudent(String name, boolean isFemale) {
		studentNames.remove(name);
		if (isFemale)
			totalFemaleStudents--;
	}

	public int getTotalFemaleStudents() {
		// TODO Auto-generated method stub
		return totalFemaleStudents;
	}
}
