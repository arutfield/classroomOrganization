package classroomSorting;

import java.util.ArrayList;
import java.util.Collections;

import exceptions.SearchingException;

public class Student {

	private final ArrayList<Integer> allowedTeacherIds = new ArrayList<Integer>();
	private final ArrayList<Integer> requiredStudents = new ArrayList<Integer>();
	private final ArrayList<Integer> forbiddenStudents = new ArrayList<Integer>();
	private final Integer id;
	private boolean isFemale;
	
	
	public Student(Integer id, ArrayList<Integer> teacherIds) {
		this.id = id;
		for (int i=0; i<teacherIds.size(); i++) allowedTeacherIds.add(0);
		Collections.copy(allowedTeacherIds, teacherIds);
	}

	public Integer getId() {
		return id;
	}
	
	public void setIsFemale(boolean female) {
		isFemale = female;
	}

	public void setOnlyAllowedTeacher(Integer onlyAllowedTeacherId) {
		allowedTeacherIds.clear();
		allowedTeacherIds.add(onlyAllowedTeacherId);
		
	}

	public void addForbiddenTeacher(Integer teacherIdInList) {
		allowedTeacherIds.remove(teacherIdInList);
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

	public ArrayList<Integer> getAllowedTeachers() {
		return allowedTeacherIds;
	}

	public ArrayList<Integer> getRequiredStudents() {
		return requiredStudents;
	}

	public ArrayList<Integer> getForbiddenStudents() {
		return forbiddenStudents;
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(NumberReference.findStudentNameByNumber(id) + ": ");
		stringBuilder.append("ID: " + id);
		stringBuilder.append(", ");
		stringBuilder.append(isFemale ? "female" : "not female");
		stringBuilder.append(", allowed in ");
		for (int i=0; i<allowedTeacherIds.size(); i++) {
			stringBuilder.append(allowedTeacherIds.get(i));
			if (i < allowedTeacherIds.size() - 1)
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
