package sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import classroomSorting.NumberReference;
import exceptions.SearchingException;

public class CommonTestFunctions {
	public static ArrayList<Integer> convertStringStudentsToNumber(ArrayList<String> studentList) throws SearchingException{
		ArrayList<Integer> studentsIntegerList = new ArrayList<Integer>();
		for (String studentName : studentList)
			studentsIntegerList.add(NumberReference.findStudentNumberByName(studentName));
		return studentsIntegerList;
	}

	public static void compareTeacherLists(ArrayList<String> actual, ArrayList<Integer> calculated) throws SearchingException {
		assertEquals(actual.size(), calculated.size());
		for (String string : actual)
			assertTrue(calculated.contains(NumberReference.findTeacherNumberByName(string)));
	}

	public static void compareIntegerLists(ArrayList<Integer> actual, ArrayList<Integer> calculated) {
		assertEquals(actual.size(), calculated.size());
		for (Integer actualInt : actual)
			assertTrue(calculated.contains(actualInt));
	}

}
