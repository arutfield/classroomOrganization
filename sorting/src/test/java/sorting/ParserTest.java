package sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

import classroomSorting.Classroom;
import classroomSorting.NumberReference;
import classroomSorting.SheetDissector;
import classroomSorting.Student;
import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class ParserTest {
	private static final LinkedList<String> emptyStringList = new LinkedList<String>();
	private static final LinkedList<Integer> emptyIntegerList = new LinkedList<Integer>();

	@Test
	public void TestParserTeachers() throws IOException, ClassSetupException, SearchingException {
		System.out.println("dir: " + System.getProperty("user.dir"));
		SheetDissector.ParseSheet("src\\test\\resources\\sampleClassToArrange.xlsx");
		
		assertEquals(3, SheetDissector.getClasses().length);
		assertEquals(15, SheetDissector.getStudents().length);
		assertTrue(SheetDissector.getClassroomByName("Mr A.").IsEll());
		assertFalse(SheetDissector.getClassroomByName("Mr A.").IsIEP());
		assertFalse(SheetDissector.getClassroomByName("Ms B.").IsEll());
		assertFalse(SheetDissector.getClassroomByName("Ms B.").IsIEP());
		assertFalse(SheetDissector.getClassroomByName("Mrs C.").IsEll());
		assertTrue(SheetDissector.getClassroomByName("Mrs C.").IsIEP());
		
	}

	
	@Test
	public void TestParserStudents() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\sampleClassToArrange.xlsx");
		
		assertEquals(3, SheetDissector.getClasses().length);
		assertEquals(15, SheetDissector.getStudents().length);
		
		checkStudentCharacteristics("One", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		
		LinkedList<String> twoForbiddenList = new LinkedList<String>(Arrays.asList("One"));
		LinkedList<String> mrAList = new LinkedList<String>(Arrays.asList("Mr A."));
		LinkedList<String> mrsCList = new LinkedList<String>(Arrays.asList("Mrs C."));
		LinkedList<String> mrAMissingList = new LinkedList<String>(Arrays.asList("Ms B.", "Mrs C."));
		checkStudentCharacteristics("Two", true, mrAList, emptyStringList, twoForbiddenList);
		checkStudentCharacteristics("Three", true, mrAList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Four", true, mrAMissingList, emptyStringList, new LinkedList<String>(Arrays.asList("Eleven")));
		checkStudentCharacteristics("Five", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Six", false, getAllTeacherNames(), new LinkedList<String>(Arrays.asList("Eight")), emptyStringList);
		checkStudentCharacteristics("Seven", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Eight", false, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Nine", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Ten", true, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Eleven", true, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twelve", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirteen", false, getAllTeacherNames(), new LinkedList<String>(Arrays.asList("Eleven")), emptyStringList);
		checkStudentCharacteristics("Fourteen", false, mrAList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Fifteen", false, getAllTeacherNames(), emptyStringList, emptyStringList);
	}

	@Test
	public void TestParserSmallList() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\smallClass.xlsx");
		
		assertEquals(2, SheetDissector.getClasses().length);
		assertEquals(6, SheetDissector.getStudents().length);
		
		assertTrue(SheetDissector.getClassroomByName("Miss One").IsEll());
		assertFalse(SheetDissector.getClassroomByName("Miss One").IsIEP());
		assertFalse(SheetDissector.getClassroomByName("Mr Two").IsEll());
		assertTrue(SheetDissector.getClassroomByName("Mr Two").IsIEP());

		checkStudentCharacteristics("A", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("B", true, new LinkedList<String>(Arrays.asList("Miss One")), emptyStringList, emptyStringList);
		checkStudentCharacteristics("C", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("D", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("E", false, new LinkedList<String>(Arrays.asList("Miss One")), emptyStringList, emptyStringList);
		checkStudentCharacteristics("F", false, new LinkedList<String>(Arrays.asList("Miss One")), emptyStringList, emptyStringList);
		
	}
	
	private void checkStudentCharacteristics(String name, boolean isFemale, LinkedList<String> allowedTeachers,
			LinkedList<String> requiredStudents, LinkedList<String> forbiddenStudents)
					throws SearchingException {
		
		
		
		Student student = SheetDissector.getStudentById(NumberReference.findStudentNumberByName(name));
		assertEquals(isFemale, student.IsFemale());
		compareStringLists(allowedTeachers, student.getAllowedTeachers());
		compareIntegerLists(convertStringStudentsToNumber(requiredStudents), student.getRequiredStudents());
		compareIntegerLists(convertStringStudentsToNumber(forbiddenStudents), student.getForbiddenStudents());

	}
	
	private LinkedList<Integer> convertStringStudentsToNumber(LinkedList<String> studentList) throws SearchingException{
		LinkedList<Integer> studentsIntegerList = new LinkedList<Integer>();
		for (String studentName : studentList)
			studentsIntegerList.add(NumberReference.findStudentNumberByName(studentName));
		return studentsIntegerList;
	}
	
	private void compareStringLists(LinkedList<String> actual, LinkedList<String> calculated) {
		assertEquals(actual.size(), calculated.size());
		for (String string : actual)
			assertTrue(calculated.contains(string));
	}

	private void compareIntegerLists(LinkedList<Integer> actual, LinkedList<Integer> calculated) {
		assertEquals(actual.size(), calculated.size());
		for (Integer actualInt : actual)
			assertTrue(calculated.contains(actualInt));
	}

	
	private LinkedList<String> getAllTeacherNames(){
		LinkedList<String> names = new LinkedList<String>();
		for (Classroom clr: SheetDissector.getClasses())
			names.add(clr.getTeacherName());
		return names;
	}
}
