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

	@Test
	public void TestParserTeachers() throws IOException, ClassSetupException, SearchingException {
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
		checkStudentCharacteristics("Four", true, mrAMissingList, emptyStringList,
				new LinkedList<String>(Arrays.asList("Eleven")));
		checkStudentCharacteristics("Five", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Six", false, getAllTeacherNames(), new LinkedList<String>(Arrays.asList("Eight")),
				emptyStringList);
		checkStudentCharacteristics("Seven", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Eight", false, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Nine", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Ten", true, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Eleven", true, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twelve", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirteen", false, getAllTeacherNames(),
				new LinkedList<String>(Arrays.asList("Eleven")), emptyStringList);
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
		checkStudentCharacteristics("B", true, new LinkedList<String>(Arrays.asList("Miss One")), emptyStringList,
				emptyStringList);
		checkStudentCharacteristics("C", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("D", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("E", false, new LinkedList<String>(Arrays.asList("Miss One")), emptyStringList,
				emptyStringList);
		checkStudentCharacteristics("F", false, new LinkedList<String>(Arrays.asList("Miss One")), emptyStringList,
				emptyStringList);

	}

	@Test
	public void TestParserLargeList() throws IOException, ClassSetupException, SearchingException {
		LinkedList<String> nList = new LinkedList<String>(Arrays.asList("Ms N"));
		LinkedList<String> sList = new LinkedList<String>(Arrays.asList("Ms S"));
		LinkedList<String> lList = new LinkedList<String>(Arrays.asList("Ms L"));

		LinkedList<String> nsList = new LinkedList<String>(Arrays.asList("Ms N", "Ms S"));
		LinkedList<String> lsList = new LinkedList<String>(Arrays.asList("Ms L", "Ms S"));
		LinkedList<String> nslList = new LinkedList<String>(Arrays.asList("Ms N", "Ms S", "Ms L"));
		LinkedList<String> nlList = new LinkedList<String>(Arrays.asList("Ms N", "Ms L"));

		SheetDissector.ParseSheet("src\\test\\resources\\BigClass.xlsx");

		assertEquals(3, SheetDissector.getClasses().length);
		assertEquals(51, SheetDissector.getStudents().length);

		assertTrue(SheetDissector.getClassroomByName("Ms N").IsIEP());
		assertFalse(SheetDissector.getClassroomByName("Ms S").IsIEP());
		assertFalse(SheetDissector.getClassroomByName("Ms N").IsEll());
		assertTrue(SheetDissector.getClassroomByName("Ms S").IsEll());
		assertFalse(SheetDissector.getClassroomByName("Ms L").IsIEP());
		assertFalse(SheetDissector.getClassroomByName("Ms L").IsEll());

		checkStudentCharacteristics("One", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Two", true, sList, emptyStringList,
				new LinkedList<String>(Arrays.asList("Eighteen")));
		checkStudentCharacteristics("Three", true, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Four", true, lsList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Five", false, lList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Six", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Seven", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Eight", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Nine", false, nList, new LinkedList<String>(Arrays.asList("Forty", "Fifty")),
				emptyStringList);
		checkStudentCharacteristics("Ten", true, nslList, emptyStringList, emptyStringList);

		checkStudentCharacteristics("Eleven", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twelve", true, nlList, emptyStringList,
				new LinkedList<String>(Arrays.asList("Fourteen")));
		checkStudentCharacteristics("Thirteen", false, nlList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Fourteen", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Fifteen", true, nslList, new LinkedList<String>(Arrays.asList("Ten")),
				emptyStringList);
		checkStudentCharacteristics("Sixteen", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Seventeen", false, lList, emptyStringList,
				new LinkedList<String>(Arrays.asList("Fifty")));
		checkStudentCharacteristics("Eighteen", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Nineteen", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty", true, sList, emptyStringList, emptyStringList);

		checkStudentCharacteristics("Twenty-one", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty-two", false, nslList, emptyStringList,
				new LinkedList<String>(Arrays.asList("Nineteen")));
		checkStudentCharacteristics("Twenty-three", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty-four", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty-five", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty-six", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty-seven", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty-eight", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty-nine", true, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty", false, nslList, emptyStringList, emptyStringList);

		checkStudentCharacteristics("Thirty-one", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-two", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-three", true, nslList, new LinkedList<String>(Arrays.asList("One")),
				emptyStringList);
		checkStudentCharacteristics("Thirty-four", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-five", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-six", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-seven", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-eight", true, sList, new LinkedList<String>(Arrays.asList("Thirty")),
				emptyStringList);
		checkStudentCharacteristics("Thirty-nine", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty", false, nslList, emptyStringList, emptyStringList);

		checkStudentCharacteristics("Forty-one", true, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-two", false, lsList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-three", false, nList, emptyStringList,
				new LinkedList<String>(Arrays.asList("Forty-two")));
		checkStudentCharacteristics("Forty-four", true, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-five", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-six", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-seven", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-eight", false, nslList, new LinkedList<String>(Arrays.asList("Forty")),
				emptyStringList);
		checkStudentCharacteristics("Forty-nine", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Fifty", true, nslList, emptyStringList,
				new LinkedList<String>(Arrays.asList("Eight", "Five")));

		checkStudentCharacteristics("Fifty-one", false, nList, emptyStringList, emptyStringList);

	}

	private void checkStudentCharacteristics(String name, boolean isFemale, LinkedList<String> allowedTeachers,
			LinkedList<String> requiredStudents, LinkedList<String> forbiddenStudents) throws SearchingException {
		Student student = SheetDissector.getStudentById(NumberReference.findStudentNumberByName(name));
		assertEquals(isFemale, student.IsFemale());
		CommonTestFunctions.compareStringLists(allowedTeachers, student.getAllowedTeachers());
		CommonTestFunctions.compareIntegerLists(CommonTestFunctions.convertStringStudentsToNumber(requiredStudents),
				student.getRequiredStudents());
		CommonTestFunctions.compareIntegerLists(CommonTestFunctions.convertStringStudentsToNumber(forbiddenStudents),
				student.getForbiddenStudents());

	}

	private LinkedList<String> getAllTeacherNames() {
		LinkedList<String> names = new LinkedList<String>();
		for (Classroom clr : SheetDissector.getClasses())
			names.add(clr.getTeacherName());
		return names;
	}
}
