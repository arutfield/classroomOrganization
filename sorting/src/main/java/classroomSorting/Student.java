package classroomSorting;

import java.util.LinkedList;

public class Student {

	private final LinkedList<String> allowedTeachers;
	private final LinkedList<String> requiredStudents = new LinkedList<String>();
	private final LinkedList<String> forbiddenStudents = new LinkedList<String>();
	private final String name;
	private boolean isFemale;
	
	
	public Student(String studentName, LinkedList<String> teachers) {
		name = studentName;
		allowedTeachers = (LinkedList<String>) teachers.clone();
	}

	public String getName() {
		return name;
	}
	
	public void setIsFemale(boolean female) {
		isFemale = female;
	}

	public void setOnlyAllowedTeacher(String onlyAllowedTeacher) {
		allowedTeachers.clear();
		allowedTeachers.add(onlyAllowedTeacher);
		
	}

	public void addForbiddenTeacher(String teacherInList) {
		allowedTeachers.remove(teacherInList);
	}

	public void addRequiredClassmate(String studentInList) {
		requiredStudents.add(studentInList);
	}

	public void addForbiddenClassmate(String studentName) {
		forbiddenStudents.add(studentName);
	}
	
	public boolean IsFemale() {
		return isFemale;
	}

	public LinkedList<String> getAllowedTeachers() {
		return allowedTeachers;
	}

	public LinkedList<String> getRequiredStudents() {
		return requiredStudents;
	}

	public LinkedList<String> getForbiddenStudents() {
		return forbiddenStudents;
	}


}
