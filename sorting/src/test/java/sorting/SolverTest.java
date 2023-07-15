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
		CommonTestFunctions.compareIntegerLists(result[0].getStudentIds(), CommonTestFunctions.convertStringStudentsToNumber(new LinkedList<String>(Arrays.asList("E", "F", "B"))));
		CommonTestFunctions.compareIntegerLists(result[1].getStudentIds(), CommonTestFunctions.convertStringStudentsToNumber(new LinkedList<String>(Arrays.asList("A", "C", "D"))));
		assertEquals("Miss One", result[0].getTeacherName());
		assertEquals("Mr Two", result[1].getTeacherName());
	}
	
	@Test
	public void TestSampleOf15Students() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\sampleClassToArrange.xlsx");
		Classroom[] result = ClassroomSorter.solveClassrooms();
		assertEquals(3, result.length);
		int totalFemaleStudents = 0;
		for (Classroom classroom : result) {
			assertEquals(5, classroom.getStudentIds().size());
			totalFemaleStudents += classroom.getTotalFemaleStudents();
			assertTrue(classroom.getTotalFemaleStudents() == 3 || classroom.getTotalFemaleStudents() == 2);
			if (classroom.getTeacherName().equals("Mr A.")) {
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Three")));
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Four")));
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Two")));
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Fourteen")));		
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("One")));
			} else if (classroom.getTeacherName().equals("Ms B.")) {
				
			} else if (classroom.getTeacherName().equals("Mrs C.")) {
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Ten")));
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eleven")));
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eight")));
			}
			if (classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Two")))
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("One")));
			if (classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Four")))
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eleven")));
			if (classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Six")))
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eight")));
			if (classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Thirteen")))
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eleven")));			
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
			assertTrue(classroom.getStudentIds().size() == 5 || classroom.getStudentIds().size() == 6);
			totalFemaleStudents += classroom.getTotalFemaleStudents();
			assertTrue(classroom.getTotalFemaleStudents() == 3 || classroom.getTotalFemaleStudents() == 2);
			if (classroom.getTeacherName().equals("Mr A.")) {
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Three")));
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Four")));
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Two")));
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Fourteen")));		
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("One")));
			} else if (classroom.getTeacherName().equals("Ms B.")) {
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Sixteen")));
			} else if (classroom.getTeacherName().equals("Mrs C.")) {
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Ten")));
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eleven")));
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eight")));
			}
			if (classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Two")))
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("One")));
			if (classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Four")))
				assertFalse(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eleven")));
			if (classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Six")))
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eight")));
			if (classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Thirteen")))
				assertTrue(classroom.getStudentIds().contains(NumberReference.findStudentNumberByName("Eleven")));			
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
			if (classroom.getTeacherName().equals("Ms N")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("One", "Three", "Nine", "Twenty-one", "Twenty-six", "Thirty-six", "Thirty-seven", "Forty-three", "Forty-four", "Forty-six", "Fifty-one")), classroom);
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Four", "Forty-two")), classroom);
				//based on requests
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Forty", "Fifty")), classroom);

			} else if (classroom.getTeacherName().equals("Ms S")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Two", "Six", "Eight", "Fourteen", "Twenty", "Twenty-four", "Twenty-nine", "Thirty-four", "Thirty-eight", "Thirty-nine", "Forty-one")), classroom);
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Twelve", "Thirteen")), classroom);
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Thirty")), classroom);
			} else if (classroom.getTeacherName().equals("Ms L")) {
				checkIfStudentsInClass(new LinkedList<String>(Arrays.asList("Five", "Seventeen")), classroom);
				checkIfStudentsNotInClass(new LinkedList<String>(Arrays.asList("Twenty-nine", "Fifty-one")), classroom);
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

	
	private void checkIfStudentsInClass(LinkedList<String> students, Classroom classroom) {
		LinkedList<Integer> studentIds = classroom.getStudentIds();
		for (String studentName : students)
			assertTrue(studentIds.contains(NumberReference.findStudentNumberByName(studentName)));
	}

	
	private void checkIfStudentsNotInClass(LinkedList<String> students, Classroom classroom) {
		LinkedList<Integer> studentIds = classroom.getStudentIds();
		for (String studentName : students)
			assertFalse(studentIds.contains(NumberReference.findStudentNumberByName(studentName)));
	}
}
