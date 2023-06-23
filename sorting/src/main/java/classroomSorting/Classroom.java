package classroomSorting;

public class Classroom {

	private final String teacherName;
	private boolean isEll;
	private boolean is504;
	
	public Classroom(String teacher) {
		this.teacherName = teacher;
	}
	
	public void enableEll() {
		isEll = true;
	}
	
	public void enable504() {
		is504 = true;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public boolean IsEll() {
		return isEll;
	}

	public boolean Is504() {
		return is504;
	}
}
