package classroomSorting;

import java.util.LinkedList;

import exceptions.SearchingException;

public class Classroom {

	private final int teacherId;
	private final LinkedList<Integer> studentIds = new LinkedList<Integer>();
	private boolean isEll;
	private boolean isIEP;
	private int totalFemaleStudents = 0;

	public Classroom(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public void enableEll() {
		isEll = true;
	}

	public void enableIEP() {
		isIEP = true;
	}

	public Integer getTeacherId() {
		return teacherId;
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
		stringBuilder.append(NumberReference.findTeacherNameByNumber(teacherId));
		stringBuilder.append("\n  students:\n");
		for (Integer studentId : studentIds) {
			try {
				stringBuilder.append("   " + SheetDissector.getStudentById(studentId).toString() + "\n");
			} catch (SearchingException e) {
				e.printStackTrace();
				return "";
			}
		}
		return stringBuilder.toString();

	}
}
