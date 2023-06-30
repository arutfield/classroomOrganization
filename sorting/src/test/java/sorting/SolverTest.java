package sorting;

import static org.junit.Assert.assertEquals;
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
		for (Classroom classroom : result) {
			assertEquals(5, classroom.getStudentNames().size());
		}
	}
	
	private void compareStringLists(LinkedList<String> actual, LinkedList<String> calculated) {
		assertEquals(actual.size(), calculated.size());
		for (String string : actual)
			assertTrue(calculated.contains(string));
	}

}
