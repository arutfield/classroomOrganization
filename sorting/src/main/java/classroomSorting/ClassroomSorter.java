package classroomSorting;

import java.util.LinkedList;

import exceptions.SearchingException;

public class ClassroomSorter {
	
	private static int mostAllowedInClass;
	private static double mostFemaleStudentsAllowedPerClass;
	private static double mostNotFemaleStudentsAllowedPerClass;
	
	public static LinkedList<Classroom> solveClassrooms() throws SearchingException{
		LinkedList<Classroom> emptyClasses = SheetDissector.getClasses();
		LinkedList<Student> students = SheetDissector.getStudents();
		mostAllowedInClass = (int) Math.ceil((double) students.size() / emptyClasses.size());
		mostFemaleStudentsAllowedPerClass = (SheetDissector.getTotalFemaleStudents() % SheetDissector.getClasses().size() == 0) ?
				(SheetDissector.getTotalFemaleStudents() / SheetDissector.getClasses().size()) : Math.ceil((double) SheetDissector.getTotalFemaleStudents() / (double) emptyClasses.size());
		mostNotFemaleStudentsAllowedPerClass = (double) (SheetDissector.getStudents().size() - SheetDissector.getTotalFemaleStudents()) / (double) emptyClasses.size() + 1.0/(double) emptyClasses.size();
		return attemptToPlaceStudent(students, emptyClasses);
		
	}
	
	private static LinkedList<Classroom> attemptToPlaceStudent(LinkedList<Student> students, LinkedList<Classroom> initialClasses) throws SearchingException{
		LinkedList<Classroom> actualInitialClasses = (LinkedList<Classroom>) initialClasses.clone();
		LinkedList<Student> actualStudents = (LinkedList<Student>) students.clone();
		if (actualStudents.isEmpty())
			return actualInitialClasses;
		Student student = actualStudents.pop();
		//get students who were requested by this student
		String teacherWithFriend = null;
		for (String friendName : student.getRequiredStudents()) {
			for (Classroom classToCheck : initialClasses) {
				if (classToCheck.getStudentNames().contains(friendName)) {
					if (teacherWithFriend == null)
						teacherWithFriend = classToCheck.getTeacherName();
					else if (!teacherWithFriend.equals(classToCheck.getTeacherName())) {
						//multiple requested students with different teachers won't work
						return null;
					}
				}
			}
		}
		
		
		//see if any placed students requested this one
		for (Classroom classroomToCheck : initialClasses) {
			for (String friendName : classroomToCheck.getStudentNames()) {
				if (SheetDissector.getStudentByName(friendName).getRequiredStudents().contains(student.getName())) {
					if (teacherWithFriend == null) {
						teacherWithFriend = classroomToCheck.getTeacherName();
					} else if (!teacherWithFriend.equals(classroomToCheck.getTeacherName())){
						//student requests don't line up
						return null;
					}
				}
			}
		}
		
		LinkedList<String> allowedTeachers = student.getAllowedTeachers();
		if (teacherWithFriend != null) {
			allowedTeachers.clear();
			allowedTeachers.add(teacherWithFriend);
		}
		if (student.getName().equals("Eight")) {
			for (String teacherName : allowedTeachers)
				System.out.println("potential teacher: " + teacherName);
		}
		for (String teacherName : allowedTeachers) {
			if (studentIsAllowedInClass(student.getName(), teacherName, actualInitialClasses)) {
				for (Classroom classroom : actualInitialClasses)
					if (classroom.getTeacherName().equals(teacherName)) {
						classroom.addStudent(student.getName(), student.IsFemale());
						LinkedList<Classroom> solution = attemptToPlaceStudent(actualStudents, actualInitialClasses);
						if (solution != null)
							return solution;
						classroom.removeStudent(student.getName(), student.IsFemale());
					}	
			}
		}
		return null;
	}

	private static boolean studentIsAllowedInClass(String studentName, String teacherName, LinkedList<Classroom> currentClasses) throws SearchingException {
		Classroom currentClassroom = null;
		for (Classroom classroom : currentClasses)
			if (classroom.getTeacherName().equals(teacherName)) {
				currentClassroom = classroom;
				break;
			}
		if (currentClassroom.getStudentNames().size() == mostAllowedInClass) {
			return false;
		}
		Student student = SheetDissector.getStudentByName(studentName);
		if (student.IsFemale() && currentClassroom.getTotalFemaleStudents() > mostFemaleStudentsAllowedPerClass - 1)
			return false;
		if (!student.IsFemale() && (currentClassroom.getStudentNames().size() - currentClassroom.getTotalFemaleStudents()) > mostNotFemaleStudentsAllowedPerClass - 1)
			return false;
		
		for (String studentInClassName : currentClassroom.getStudentNames()) {
			Student studentInClass = SheetDissector.getStudentByName(studentInClassName);
			if (studentInClass.getForbiddenStudents().contains(studentName) || student.getForbiddenStudents().contains(studentInClassName))
				return false;
		}
		return true;
	}
	
	
}
