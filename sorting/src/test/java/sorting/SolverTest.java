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
	}
	
}
