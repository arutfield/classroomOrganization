package classroomSorting;

import java.util.ArrayList;
import java.util.Collections;

import exceptions.SearchingException;

public class Classroom {

	private final int teacherId;
	private final ArrayList<Integer> studentIds = new ArrayList<Integer>();
	private boolean isEll;
	private boolean isIEP;
	private int totalFemaleStudents = 0;

	public Classroom(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Classroom(Classroom classroom) {
		this.teacherId = classroom.teacherId;
		this.isEll = classroom.isEll;
		this.isIEP = classroom.isIEP;
		this.totalFemaleStudents = classroom.totalFemaleStudents;
		for (int i=0; i<classroom.studentIds.size(); i++) this.studentIds.add(0);
		Collections.copy(this.studentIds, classroom.studentIds);
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

	public ArrayList<Integer> getStudentIds() {
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
