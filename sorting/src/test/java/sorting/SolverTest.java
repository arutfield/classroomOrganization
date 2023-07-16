package sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

import classroomSorting.Classroom;
import classroomSorting.ClassroomSorter;
import classroomSorting.NumberReference;
import classroomSorting.SheetDissector;
import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class SolverTest {

	@Test
	public void TestSolverSmallList() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\smallClass.xlsx");
		Classroom[] result = ClassroomSorter.solveClassrooms();

		assertEquals(2, result.length);
		CommonTestFunctions.compareIntegerLists(result[0].getStudentIds(), CommonTestFunctions
				.convertStringStudentsToNumber(new LinkedList<String>(Arrays.asList("E", "F", "B"))));
		CommonTestFunctions.compareIntegerLists(result[1].getStudentIds(), CommonTestFunctions
				.convertStringStudentsToNumber(new LinkedList<String>(Arrays.asList("A", "C", "D"))));
		assertEquals("Miss One", NumberReference.findTeacherNameByNumber(result[0].getTeacherId()));
		assertEquals("Mr Two", NumberReference.findTeacherNameByNumber(result[1].getTeacherId()));
	}

	@Test
	public void TestSampleOf15Students() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\sampleClassToArrange.xlsx");
		Classroom[] result = ClassroomSorter.solveClassrooms();
		assertEquals(3, result.length);
		int totalFemaleStudents = 0;
		for (Classroom classroom : result) {
			LinkedList<Integer> studentIds = classroom.getStudentIds();
			assertEquals(5, studentIds.size());
			totalFemaleStudents += classroom.getTotalFemaleStudents();
			assertTrue(classroom.getTotalFemaleStudents() == 3 || classroom.getTotalFemaleStudents() == 2);
			if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Mr A.")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Three", "Two", "Fourteen")), studentIds);
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Four", "One")), studentIds);
			} else if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Ms B.")) {

			} else if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Mrs C.")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Ten", "Eleven", "Eight")), studentIds);
			}
			hasNoEnemy(studentIds, "Two", "One");
			hasNoEnemy(studentIds, "Four", "Eleven");
			hasFriend(studentIds, "Six", "Eight");
			hasFriend(studentIds, "Thirteen", "Eleven");
		}
		assertEquals(7, totalFemaleStudents);

	}

	@Test
	public void TestUnevenClasses() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\unevenClasses.xlsx");
		Classroom[] result = ClassroomSorter.solveClassrooms();
		assertEquals(3, result.length);
		int totalFemaleStudents = 0;
		for (Classroom classroom : result) {
			LinkedList<Integer> studentIds = classroom.getStudentIds();
			assertTrue(studentIds.size() == 5 || studentIds.size() == 6);
			totalFemaleStudents += classroom.getTotalFemaleStudents();
			assertTrue(classroom.getTotalFemaleStudents() == 3 || classroom.getTotalFemaleStudents() == 2);
			if (classroom.getTeacherId().equals("Mr A.")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Three", "Two", "Fourteen")), studentIds);
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Four", "One")), studentIds);
			} else if (classroom.getTeacherId().equals("Ms B.")) {
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Sixteen")), studentIds);
			} else if (classroom.getTeacherId().equals("Mrs C.")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Ten", "Eleven", "Eight")), studentIds);
			}
			hasNoEnemy(studentIds, "Two", "One");
			hasNoEnemy(studentIds, "Four", "Eleven");
			hasFriend(studentIds, "Six", "Eight");
			hasFriend(studentIds, "Thirteen", "Eleven");
		}
		assertEquals(8, totalFemaleStudents);

	}

	@Test
	public void TestSampleOfManyStudents() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\BigClass.xlsx");
		Classroom[] result = ClassroomSorter.solveClassrooms();
		assertEquals(3, result.length);
		int totalFemaleStudents = 0;
		for (Classroom classroom : result) {
			LinkedList<Integer> studentIds = classroom.getStudentIds();
			assertTrue(studentIds.size() == 17);
			totalFemaleStudents += classroom.getTotalFemaleStudents();
			assertTrue(classroom.getTotalFemaleStudents() == 6 || classroom.getTotalFemaleStudents() == 7);
			if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Ms N")) {
				checkIfStudentsInClass(
						new LinkedList<String>(Arrays.asList("One", "Three", "Nine", "Twenty-one", "Twenty-six",
								"Thirty-six", "Thirty-seven", "Forty-three", "Forty-four", "Forty-six", "Fifty-one")),
						studentIds);
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Four", "Forty-two")), studentIds);
				// based on requests
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Forty", "Fifty")), studentIds);

			} else if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Ms S")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Two", "Six", "Eight", "Fourteen", "Twenty",
						"Twenty-four", "Twenty-nine", "Thirty-four", "Thirty-eight", "Thirty-nine", "Forty-one")),
						studentIds);
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Twelve", "Thirteen")), studentIds);
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Thirty")), studentIds);
			} else if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Ms L")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Five", "Seventeen")), studentIds);
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Twenty-nine", "Fifty-one")),
						studentIds);
			}
			hasFriend(studentIds, "Fifteen", "Ten");
			hasFriend(studentIds, "Thirty-three", "One");
			hasFriend(studentIds, "Forty-eight", "Forty");
			hasNoEnemy(studentIds, "Two", "Eighteen");
			hasNoEnemy(studentIds, "Twelve", "Fourteen");
			hasNoEnemy(studentIds, "Seventeen", "Fifty");
			hasNoEnemy(studentIds, "Twenty-two", "Nineteen");
			hasNoEnemy(studentIds, "Forty-three", "Forty-two");
			hasNoEnemy(studentIds, "Fifty", "Eight");
			hasNoEnemy(studentIds, "Fifty", "Five");

		}
		assertEquals(19, totalFemaleStudents);
	}

	private void hasFriend(LinkedList<Integer> studentIds, String currentStudentName, String friendName) {
		if (studentIds.contains(NumberReference.findStudentNumberByName(currentStudentName)))
			assertTrue(studentIds.contains(NumberReference.findStudentNumberByName(friendName)));
	}

	private void hasNoEnemy(LinkedList<Integer> studentIds, String currentStudentName, String friendName) {
		if (studentIds.contains(NumberReference.findStudentNumberByName(currentStudentName)))
			assertFalse(studentIds.contains(NumberReference.findStudentNumberByName(friendName)));
	}

	private void checkIfStudentsInClass(LinkedList<String> students, LinkedList<Integer> studentIds) {
		for (String studentName : students)
			assertTrue(studentIds.contains(NumberReference.findStudentNumberByName(studentName)));
	}

	private void checkIfStudentsNotInClass(LinkedList<String> students, LinkedList<Integer> studentIds) {
		for (String studentName : students)
			assertFalse(studentIds.contains(NumberReference.findStudentNumberByName(studentName)));
	}
}
