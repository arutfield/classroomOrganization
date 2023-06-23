package classroomSorting;

import java.util.LinkedList;

public class Student {

	private final LinkedList<String> allowedTeachers;
	private final LinkedList<String> desiredClassmates = new LinkedList<String>();
	private final LinkedList<String> forbiddenClassmates = new LinkedList<String>();
	private final String name;
	private boolean isFemale;
	
	
	public Student(String studentName, LinkedList<String> teachers) {
		name = studentName;
		allowedTeachers = (LinkedList<String>) teachers.clone();
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
		desiredClassmates.add(studentInList);
	}

	public void addForbiddenClassmate(String studentName) {
		forbiddenClassmates.add(studentName);
	}

}
