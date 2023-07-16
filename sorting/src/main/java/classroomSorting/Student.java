package classroomSorting;

import java.util.LinkedList;

import exceptions.SearchingException;

public class Student {

	private final LinkedList<Integer> allowedTeacherIds;
	private final LinkedList<Integer> requiredStudents = new LinkedList<Integer>();
	private final LinkedList<Integer> forbiddenStudents = new LinkedList<Integer>();
	private final Integer id;
	private boolean isFemale;
	
	
	public Student(Integer id, LinkedList<Integer> teacherIds) {
		this.id = id;
		allowedTeacherIds = (LinkedList<Integer>) teacherIds.clone();
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

	public LinkedList<Integer> getAllowedTeachers() {
		return allowedTeacherIds;
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
