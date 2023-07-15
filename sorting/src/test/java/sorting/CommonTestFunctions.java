package sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import classroomSorting.NumberReference;
import exceptions.SearchingException;

public class CommonTestFunctions {
	public static LinkedList<Integer> convertStringStudentsToNumber(LinkedList<String> studentList) throws SearchingException{
		LinkedList<Integer> studentsIntegerList = new LinkedList<Integer>();
		for (String studentName : studentList)
			studentsIntegerList.add(NumberReference.findStudentNumberByName(studentName));
		return studentsIntegerList;
	}
	public static void compareStringLists(LinkedList<String> actual, LinkedList<String> calculated) {
		assertEquals(actual.size(), calculated.size());
		for (String string : actual)
			assertTrue(calculated.contains(string));
	}

	public static void compareIntegerLists(LinkedList<Integer> actual, LinkedList<Integer> calculated) {
		assertEquals(actual.size(), calculated.size());
		for (Integer actualInt : actual)
			assertTrue(calculated.contains(actualInt));
	}

}
