package classroomSorting;

public class Classroom {

	private final String teacherName;
	private boolean isEll;
	private boolean isIEP;
	
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
}
