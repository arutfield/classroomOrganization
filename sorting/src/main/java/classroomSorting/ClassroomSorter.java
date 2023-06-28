package classroomSorting;

import java.util.LinkedList;

public class ClassroomSorter {
	
	private static int mostAllowedInClass;

	public static LinkedList<Classroom> solveClassrooms(){
		LinkedList<Classroom> emptyClasses = SheetDissector.getClasses();
		LinkedList<Student> students = SheetDissector.getStudents();
		mostAllowedInClass = (int) Math.ceil((double) students.size() / emptyClasses.size());
		return attemptToPlaceStudent(students, emptyClasses);
		
	}
	
	private static LinkedList<Classroom> attemptToPlaceStudent(LinkedList<Student> students, LinkedList<Classroom> initialClasses){
		LinkedList<Classroom> actualInitialClasses = (LinkedList<Classroom>) initialClasses.clone();
		LinkedList<Student> actualStudents = (LinkedList<Student>) students.clone();
		if (actualStudents.isEmpty())
			return actualInitialClasses;
		Student student = actualStudents.pop();
		for (String teacherName : student.getAllowedTeachers()) {
			if (studentIsAllowedInClass(student.getName(), teacherName, actualInitialClasses)) {
				for (Classroom classroom : actualInitialClasses)
					if (classroom.getTeacherName().equals(teacherName)) {
						classroom.addStudent(student.getName());
						LinkedList<Classroom> solution = attemptToPlaceStudent(actualStudents, actualInitialClasses);
						if (solution != null)
							return solution;
						classroom.removeStudent(student.getName());
					}	
			}
		}
		return null;
	}

	private static boolean studentIsAllowedInClass(String studentName, String teacherName, LinkedList<Classroom> currentClasses) {
		Classroom currentClassroom = null;
		for (Classroom classroom : currentClasses)
			if (classroom.getTeacherName().equals(teacherName)) {
				currentClassroom = classroom;
				break;
			}
		if (currentClassroom.getStudentNames().size() == mostAllowedInClass) {
			return false;
		}
		return true;
	}
	
}
