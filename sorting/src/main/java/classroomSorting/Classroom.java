package classroomSorting;

import java.util.LinkedList;

import exceptions.SearchingException;

public class Classroom {

	private final String teacherName;
	private final LinkedList<Integer> studentIds = new LinkedList<Integer>();
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

	public void addStudent(Integer id, boolean isFemale) throws SearchingException {
		studentIds.add(id);
		if (isFemale)
			totalFemaleStudents++;
	}

	public LinkedList<Integer> getStudentIds() {	
		return studentIds;
	}

	public void removeStudent(Integer id, boolean isFemale) throws SearchingException {
		studentIds.remove(id);
		if (isFemale)
			totalFemaleStudents--;
	}

	public int getTotalFemaleStudents() {
		return totalFemaleStudents;
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Teacher: ");
		stringBuilder.append(teacherName);
		stringBuilder.append(", students:");
		for (Integer studentId : studentIds) {
			stringBuilder.append(" " + NumberReference.findStudentNameByNumber(studentId) + ",");
		}
		return stringBuilder.toString();
		
	}
}
