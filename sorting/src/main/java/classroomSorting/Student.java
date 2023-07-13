package classroomSorting;

import java.util.LinkedList;

import exceptions.SearchingException;

public class Student {

	private final LinkedList<String> allowedTeachers;
	private final LinkedList<Integer> requiredStudents = new LinkedList<Integer>();
	private final LinkedList<Integer> forbiddenStudents = new LinkedList<Integer>();
	private final Integer id;
	private boolean isFemale;
	
	
	public Student(Integer id, LinkedList<String> teachers) {
		this.id = id;
		allowedTeachers = (LinkedList<String>) teachers.clone();
	}

	public Integer getId() {
		return id;
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

	public void addRequiredClassmate(String studentInList) throws SearchingException {
		requiredStudents.add(NumberReference.findStudentNumberByName(studentInList));
	}

	public void addForbiddenClassmate(String studentName) throws SearchingException {
		forbiddenStudents.add(NumberReference.findStudentNumberByName(studentName));
	}
	
	public boolean IsFemale() {
		return isFemale;
	}

	public LinkedList<String> getAllowedTeachers() {
		return allowedTeachers;
	}

	public LinkedList<Integer> getRequiredStudents() {
		return requiredStudents;
	}

	public LinkedList<Integer> getForbiddenStudents() {
		return forbiddenStudents;
	}


}
