package classroomSorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

import exceptions.SearchingException;

public class ClassroomSorter {

	private static int mostAllowedInClass;
	private static int extraStudents;
	private static double mostFemaleStudentsAllowedPerClass;
	private static double mostNotFemaleStudentsAllowedPerClass;
	private static int timeoutTime = 600000;
	private static int solutionsFound = 0;
	private static int maxSolutions = 1000000;
	private static long startTime;
	private static boolean exitEarlyFlag = false;
	
	public static Classroom[] solveClassrooms() throws SearchingException {
		startTime = System.currentTimeMillis();
		Classroom[] emptyClasses = SheetDissector.getClasses();
		Student[] students = SheetDissector.getStudents();
		mostAllowedInClass = (int) Math.ceil((double) students.length / emptyClasses.length);
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
		return attemptToPlaceStudent(new ArrayList<Student>(Arrays.asList(students)), emptyClasses);

	}

	public static ArrayList<Classroom[]> solveAllClassrooms() throws SearchingException {
		startTime = System.currentTimeMillis();
		Classroom[] emptyClasses = SheetDissector.getClasses();
		Student[] students = SheetDissector.getStudents();
		mostAllowedInClass = (int) Math.ceil((double) students.length / emptyClasses.length);
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
		return attemptToPlaceStudentAllSets(new ArrayList<Student>(Arrays.asList(students)), emptyClasses);

	}
	
	private static Classroom[] attemptToPlaceStudent(ArrayList<Student> students, Classroom[] initialClasses)
			throws SearchingException {
		if (System.currentTimeMillis() - startTime > timeoutTime) {
			if (!exitEarlyFlag) {
				exitEarlyFlag = true;
				System.out.println("Timeout reached before finding a solution");
			}
			return null;
		}
		Classroom[] actualInitialClasses = initialClasses.clone();
		ArrayList<Student> actualStudents = new ArrayList<Student>();
		for (int i=0; i<students.size(); i++) actualStudents.add(null);
		Collections.copy(actualStudents, students);
		if (actualStudents.isEmpty())
			return actualInitialClasses;
		Student student = actualStudents.remove(0);
		if(!handleFriends(student, initialClasses)) return null;
		
		int classesAtMaximum = classesAtMaximum(actualInitialClasses);
		for (Integer teacherId : student.getAllowedTeachers()) {
			if (studentIsAllowedInClass(student.getId(), teacherId, actualInitialClasses)) {
				for (Classroom classroom : actualInitialClasses)
					if (classroom.getTeacherId() == teacherId) {
						if (classesAtMaximum == extraStudents
								&& classroom.getStudentIds().size() == mostAllowedInClass - 1) {
							break;
						}
						classroom.addStudent(student.getId(), student.IsFemale());
						Classroom[] solution = attemptToPlaceStudent(actualStudents, actualInitialClasses);
						if (solution != null)
							return solution;
						classroom.removeStudent(student.getId(), student.IsFemale());
						break;
					}
			}
		}
		return null;
	}

	private static ArrayList<Classroom[]> attemptToPlaceStudentAllSets(ArrayList<Student> students, Classroom[] initialClasses)
			throws SearchingException {
		ArrayList<Classroom[]> allSolutions = new ArrayList<Classroom[]>();
		if (System.currentTimeMillis() - startTime > timeoutTime) {
			if (!exitEarlyFlag) {
				exitEarlyFlag = true;
				System.out.println("Timeout reached before finishing. Returning all solutions found so far.");
			}
			return allSolutions;
		}
		if (solutionsFound == maxSolutions) {
			if (!exitEarlyFlag) {
				exitEarlyFlag = true;
				System.out.println("Maximum solutions of " + maxSolutions + " found. Exiting");
			}
			return allSolutions;
		}
		Classroom[] actualInitialClasses = initialClasses.clone();
		ArrayList<Student> actualStudents = new ArrayList<Student>();
		for (int i=0; i<students.size(); i++) actualStudents.add(null);
		Collections.copy(actualStudents, students);
		if (actualStudents.isEmpty()) {
			Classroom[] solution = new Classroom[actualInitialClasses.length];
			for (int i=0; i<actualInitialClasses.length; i++) solution[i] = new Classroom(actualInitialClasses[i]);
			allSolutions.add(solution);
			solutionsFound++;
			return allSolutions;
		}
		Student student = actualStudents.remove(0);
		if(!handleFriends(student, initialClasses)) return allSolutions;
		
		int classesAtMaximum = classesAtMaximum(actualInitialClasses);
		for (Integer teacherId : student.getAllowedTeachers()) {
			if (studentIsAllowedInClass(student.getId(), teacherId, actualInitialClasses)) {
				for (Classroom classroom : actualInitialClasses)
					if (classroom.getTeacherId() == teacherId) {
						if (classesAtMaximum == extraStudents
								&& classroom.getStudentIds().size() == mostAllowedInClass - 1) {
							break;
						}
						classroom.addStudent(student.getId(), student.IsFemale());
						ArrayList<Classroom[]> subSolution = attemptToPlaceStudentAllSets(actualStudents, actualInitialClasses);
						allSolutions.addAll(new ArrayList<Classroom[]>(subSolution));
						if (exitEarlyFlag)
							return allSolutions;
						classroom.removeStudent(student.getId(), student.IsFemale());
						break;
					}
			}
		}
		return allSolutions;
	}

	
	private static boolean studentIsAllowedInClass(Integer studentId, Integer teacherId, Classroom[] currentClasses)
			throws SearchingException {
		Classroom currentClassroom = null;
		for (Classroom classroom : currentClasses)
			if (classroom.getTeacherId() == teacherId) {
				currentClassroom = classroom;
				break;
			}
		if (currentClassroom.getStudentIds().size() == mostAllowedInClass) {
			return false;
		}
		Student student = SheetDissector.getStudentById(studentId);
		if (student.IsFemale() && currentClassroom.getTotalFemaleStudents() > mostFemaleStudentsAllowedPerClass - 1)
			return false;
		if (!student.IsFemale() && (currentClassroom.getStudentIds().size()
				- currentClassroom.getTotalFemaleStudents()) > mostNotFemaleStudentsAllowedPerClass - 1)
			return false;

		//check forbidden classmates
		for (Integer studentInClassId : currentClassroom.getStudentIds()) {
			Student studentInClass = SheetDissector.getStudentById(studentInClassId);
			if (studentInClass.getForbiddenStudents().contains(studentId)
					|| student.getForbiddenStudents().contains(studentInClassId))
				return false;
		}
		return true;
	}

	private static boolean handleFriends(Student student, Classroom[] initialClasses) throws SearchingException {
		// get students who were requested by this student
		Integer teacherWithFriend = null;
		for (Integer friendId : student.getRequiredStudents()) {
			for (Classroom classToCheck : initialClasses) {
				if (classToCheck.getStudentIds().contains(friendId)) {
					if (teacherWithFriend == null)
						teacherWithFriend = classToCheck.getTeacherId();
					else if (teacherWithFriend != classToCheck.getTeacherId()) {
						// multiple requested students with different teachers won't work
						return false;
					}
				}
			}
		}
		
		// see if any placed students requested this one
		for (Classroom classroomToCheck : initialClasses) {
			for (Integer friendId : classroomToCheck.getStudentIds()) {
				if (SheetDissector.getStudentById(friendId).getRequiredStudents().contains(student.getId())) {
					if (teacherWithFriend == null) {
						teacherWithFriend = classroomToCheck.getTeacherId();
					} else if (teacherWithFriend != classroomToCheck.getTeacherId()) {
						// student requests don't line up
						return false;
					}
				}
			}
		}

		ArrayList<Integer> allowedTeacherIds = student.getAllowedTeachers();
		if (teacherWithFriend != null) {
			if (!allowedTeacherIds.contains(teacherWithFriend))
				return false;
			else {
				allowedTeacherIds.clear();
				allowedTeacherIds.add(teacherWithFriend);
			}
		}
		return true;
	}

	
	private static int classesAtMaximum(Classroom[] currentClasses) {
		int atMaxCapacity = 0;
		for (Classroom classroom : currentClasses) {
			if (classroom.getStudentIds().size() == mostAllowedInClass)
				atMaxCapacity++;
		}
		return atMaxCapacity;
	}

}
