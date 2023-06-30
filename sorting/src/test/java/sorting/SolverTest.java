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
import classroomSorting.SheetDissector;
import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class SolverTest {

	@Test
	public void TestSolverSmallList() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\smallClass.xlsx");
		LinkedList<Classroom> result = ClassroomSorter.solveClassrooms();
		
		assertEquals(2, result.size());
		compareStringLists(result.get(0).getStudentNames(), new LinkedList<String>(Arrays.asList("E", "F", "B")));
		compareStringLists(result.get(1).getStudentNames(), new LinkedList<String>(Arrays.asList("A", "C", "D")));
		assertEquals("Miss One", result.get(0).getTeacherName());
		assertEquals("Mr Two", result.get(1).getTeacherName());
	}
	
	@Test
	public void TestSampleOf15Students() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\sampleClassToArrange.xlsx");
		LinkedList<Classroom> result = ClassroomSorter.solveClassrooms();
		assertEquals(3, result.size());
		int totalFemaleStudents = 0;
		for (Classroom classroom : result) {
			assertEquals(5, classroom.getStudentNames().size());
			totalFemaleStudents += classroom.getTotalFemaleStudents();
			assertTrue(classroom.getTotalFemaleStudents() == 3 || classroom.getTotalFemaleStudents() == 2);
			if (classroom.getTeacherName().equals("Mr A.")) {
				assertTrue(classroom.getStudentNames().contains("Three"));
				assertFalse(classroom.getStudentNames().contains("Four"));
				assertTrue(classroom.getStudentNames().contains("Two"));
				assertTrue(classroom.getStudentNames().contains("Fourteen"));		
				assertFalse(classroom.getStudentNames().contains("One"));
			} else if (classroom.getTeacherName().equals("Ms B.")) {
				
			} else if (classroom.getTeacherName().equals("Mrs C.")) {
				assertTrue(classroom.getStudentNames().contains("Ten"));
				assertTrue(classroom.getStudentNames().contains("Eleven"));
				assertTrue(classroom.getStudentNames().contains("Eight"));
			}
			if (classroom.getStudentNames().contains("Two"))
				assertFalse(classroom.getStudentNames().contains("One"));
			if (classroom.getStudentNames().contains("Four"))
				assertFalse(classroom.getStudentNames().contains("Eleven"));
			if (classroom.getStudentNames().contains("Six"))
				assertTrue(classroom.getStudentNames().contains("Eight"));
			if (classroom.getStudentNames().contains("Thirteen"))
				assertTrue(classroom.getStudentNames().contains("Eleven"));			
		}
		assertEquals(7, totalFemaleStudents);

	}
	
	private void compareStringLists(LinkedList<String> actual, LinkedList<String> calculated) {
		assertEquals(actual.size(), calculated.size());
		for (String string : actual)
			assertTrue(calculated.contains(string));
	}

}
