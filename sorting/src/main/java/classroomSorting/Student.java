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
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(NumberReference.findStudentNameByNumber(id) + ": ");
		stringBuilder.append("ID: " + id);
		stringBuilder.append(", ");
		stringBuilder.append(isFemale ? "female" : "not female");
		stringBuilder.append(", allowed in ");
		for (int i=0; i<allowedTeachers.size(); i++) {
			stringBuilder.append(allowedTeachers.get(i));
			if (i < allowedTeachers.size() - 1)
				stringBuilder.append(", ");
			
		}
		stringBuilder.append(" classes");
		stringBuilder.append(", friends with ");
		if (requiredStudents.isEmpty())
			stringBuilder.append("None, ");
		for (Integer studentId : requiredStudents)
			stringBuilder.append(NumberReference.findStudentNameByNumber(studentId) + ", ");
		stringBuilder.append("doesn't get along with ");
		if (forbiddenStudents.isEmpty())
			stringBuilder.append("None, ");
		for (Integer studentId : forbiddenStudents)
			stringBuilder.append(NumberReference.findStudentNameByNumber(studentId) + ", ");
		return stringBuilder.toString();
		
	}


}
