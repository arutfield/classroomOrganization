package classroomSorting;

import java.util.Arrays;
import java.util.LinkedList;

import exceptions.SearchingException;

public class ClassroomSorter {

	private static int mostAllowedInClass;
	private static int minAllowedInClass;
	private static int extraStudents;
	private static double mostFemaleStudentsAllowedPerClass;
	private static double mostNotFemaleStudentsAllowedPerClass;

	public static Classroom[] solveClassrooms() throws SearchingException {
		Classroom[] emptyClasses = SheetDissector.getClasses();
		Student[] students = SheetDissector.getStudents();
		mostAllowedInClass = (int) Math.ceil((double) students.length / emptyClasses.length);
		minAllowedInClass = (int) Math.floorDiv(students.length, emptyClasses.length);
		extraStudents = students.length % emptyClasses.length;
		if (extraStudents == 0)
			extraStudents = emptyClasses.length;
		mostFemaleStudentsAllowedPerClass = (SheetDissector.getTotalFemaleStudents()
				% SheetDissector.getClasses().length == 0)
						? (SheetDissector.getTotalFemaleStudents() / SheetDissector.getClasses().length)
						: Math.ceil((double) SheetDissector.getTotalFemaleStudents() / (double) emptyClasses.length);
		mostNotFemaleStudentsAllowedPerClass = (double) (SheetDissector.getStudents().length
				- SheetDissector.getTotalFemaleStudents()) / (double) emptyClasses.length
				+ 1.0 / (double) emptyClasses.length;
		return attemptToPlaceStudent(new LinkedList<Student>(Arrays.asList(students)), emptyClasses);

	}

	private static Classroom[] attemptToPlaceStudent(LinkedList<Student> students,
			Classroom[] initialClasses) throws SearchingException {
		Classroom[] actualInitialClasses = initialClasses.clone();
		LinkedList<Student> actualStudents = (LinkedList<Student>) students.clone();
		if (actualStudents.isEmpty())
			return actualInitialClasses;
		Student student = actualStudents.pop();
		// get students who were requested by this student
		String teacherWithFriend = null;
		for (String friendName : student.getRequiredStudents()) {
			for (Classroom classToCheck : initialClasses) {
				if (classToCheck.getStudentNames().contains(friendName)) {
					if (teacherWithFriend == null)
						teacherWithFriend = classToCheck.getTeacherName();
					else if (!teacherWithFriend.equals(classToCheck.getTeacherName())) {
						// multiple requested students with different teachers won't work
						return null;
					}
				}
			}
		}

		// see if any placed students requested this one
		for (Classroom classroomToCheck : initialClasses) {
			for (String friendName : classroomToCheck.getStudentNames()) {
				if (SheetDissector.getStudentByName(friendName).getRequiredStudents().contains(student.getName())) {
					if (teacherWithFriend == null) {
						teacherWithFriend = classroomToCheck.getTeacherName();
					} else if (!teacherWithFriend.equals(classroomToCheck.getTeacherName())) {
						// student requests don't line up
						return null;
					}
				}
			}
		}

		LinkedList<String> allowedTeachers = student.getAllowedTeachers();
		if (teacherWithFriend != null) {
			if (!allowedTeachers.contains(teacherWithFriend))
				return null;
			else {
				allowedTeachers.clear();
				allowedTeachers.add(teacherWithFriend);
			}
		}
		int classesAtMaximum = classesAtMaximum(actualInitialClasses);
		for (String teacherName : allowedTeachers) {
			if (studentIsAllowedInClass(student.getName(), teacherName, actualInitialClasses)) {
				for (Classroom classroom : actualInitialClasses)
					if (classroom.getTeacherName().equals(teacherName)) {
						if (classesAtMaximum == extraStudents && classroom.getStudentNames().size() == mostAllowedInClass - 1) {
							break;
						}
						classroom.addStudent(student.getName(), student.IsFemale());
						Classroom[] solution = attemptToPlaceStudent(actualStudents, actualInitialClasses);
						if (solution != null)
							return solution;
						classroom.removeStudent(student.getName(), student.IsFemale());
						break;
					}
			}
		}
		return null;
	}

	private static boolean studentIsAllowedInClass(String studentName, String teacherName,
			Classroom[] currentClasses) throws SearchingException {
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
		if (!student.IsFemale() && (currentClassroom.getStudentNames().size()
				- currentClassroom.getTotalFemaleStudents()) > mostNotFemaleStudentsAllowedPerClass - 1)
			return false;

		for (String studentInClassName : currentClassroom.getStudentNames()) {
			Student studentInClass = SheetDissector.getStudentByName(studentInClassName);
			if (studentInClass.getForbiddenStudents().contains(studentName)
					|| student.getForbiddenStudents().contains(studentInClassName))
				return false;
		}
		return true;
	}

	private static int classesAtMaximum(Classroom[] currentClasses) {
		int atMaxCapacity = 0;
		for (Classroom classroom : currentClasses) {
			if (classroom.getStudentNames().size() == mostAllowedInClass)
				atMaxCapacity++;
		}
		return atMaxCapacity;
	}

}
