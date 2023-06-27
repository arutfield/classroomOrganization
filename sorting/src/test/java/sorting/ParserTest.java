package sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

import classroomSorting.Classroom;
import classroomSorting.SheetDissector;
import classroomSorting.Student;
import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class ParserTest {
	private static final LinkedList<String> emptyStringList = new LinkedList<String>();
	

	@Test
	public void TestParserTeachers() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("../sampleClassToArrange.xlsx");
		
		assertEquals(3, SheetDissector.getClasses().size());
		assertEquals(15, SheetDissector.getStudents().size());
		assertTrue(SheetDissector.getClassroomByName("Mr A.").IsEll());
		assertFalse(SheetDissector.getClassroomByName("Mr A.").Is504());
		assertFalse(SheetDissector.getClassroomByName("Ms B.").IsEll());
		assertFalse(SheetDissector.getClassroomByName("Ms B.").Is504());
		assertFalse(SheetDissector.getClassroomByName("Mrs C.").IsEll());
		assertTrue(SheetDissector.getClassroomByName("Mrs C.").Is504());
		
	}

	
	@Test
	public void TestParserStudents() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("../sampleClassToArrange.xlsx");
		
		assertEquals(3, SheetDissector.getClasses().size());
		assertEquals(15, SheetDissector.getStudents().size());
		
		checkStudentCharacteristics("One", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		
		LinkedList<String> twoForbiddenList = new LinkedList<String>(Arrays.asList("One"));
		LinkedList<String> mrAList = new LinkedList<String>(Arrays.asList("Mr A."));
		checkStudentCharacteristics("Two", true, mrAList, emptyStringList, twoForbiddenList);
		
	}

	private void checkStudentCharacteristics(String name, boolean isFemale, LinkedList<String> allowedTeachers,
			LinkedList<String> requiredStudents, LinkedList<String> forbiddenStudents)
					throws SearchingException {
		Student student = SheetDissector.getStudentByName(name);
		assertEquals(isFemale, student.IsFemale());
		compareStringLists(allowedTeachers, student.getAllowedTeachers());
		compareStringLists(requiredStudents, student.getRequiredStudents());
		compareStringLists(forbiddenStudents, student.getForbiddenStudents());

	}
	
	private void compareStringLists(LinkedList<String> actual, LinkedList<String> calculated) {
		assertEquals(actual.size(), calculated.size());
		for (String string : actual)
			assertTrue(calculated.contains(string));
	}
	
	private LinkedList<String> getAllTeacherNames(){
		LinkedList<String> names = new LinkedList<String>();
		for (Classroom clr: SheetDissector.getClasses())
			names.add(clr.getTeacherName());
		return names;
	}
}