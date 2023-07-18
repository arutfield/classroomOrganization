package sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

import org.junit.Test;

import classroomSorting.Classroom;
import classroomSorting.NumberReference;
import classroomSorting.SheetDissector;
import classroomSorting.Student;
import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class ParserTest {
	private static final ArrayList<String> emptyStringList = new ArrayList<String>();

	@Test
	public void TestParserTeachers() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\sampleClassToArrange.xlsx");

		assertEquals(3, SheetDissector.getClasses().length);
		assertEquals(15, SheetDissector.getStudents().length);
		assertTrue(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Mr A.")).IsEll());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Mr A.")).IsIEP());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Ms B.")).IsEll());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Ms B.")).IsIEP());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Mrs C.")).IsEll());
		assertTrue(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Mrs C.")).IsIEP());

	}

	@Test
	public void TestParserStudents() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\sampleClassToArrange.xlsx");

		assertEquals(3, SheetDissector.getClasses().length);
		assertEquals(15, SheetDissector.getStudents().length);

		checkStudentCharacteristics("One", false, getAllTeacherNames(), emptyStringList, emptyStringList);

		ArrayList<String> twoForbiddenList = new ArrayList<String>(Arrays.asList("One"));
		ArrayList<String> mrAList = new ArrayList<String>(Arrays.asList("Mr A."));
		ArrayList<String> mrsCList = new ArrayList<String>(Arrays.asList("Mrs C."));
		ArrayList<String> mrAMissingList = new ArrayList<String>(Arrays.asList("Ms B.", "Mrs C."));
		checkStudentCharacteristics("Two", true, mrAList, emptyStringList, twoForbiddenList);
		checkStudentCharacteristics("Three", true, mrAList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Four", true, mrAMissingList, emptyStringList,
				new ArrayList<String>(Arrays.asList("Eleven")));
		checkStudentCharacteristics("Five", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Six", false, getAllTeacherNames(), new ArrayList<String>(Arrays.asList("Eight")),
				emptyStringList);
		checkStudentCharacteristics("Seven", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Eight", false, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Nine", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Ten", true, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Eleven", true, mrsCList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twelve", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirteen", false, getAllTeacherNames(),
				new ArrayList<String>(Arrays.asList("Eleven")), emptyStringList);
		checkStudentCharacteristics("Fourteen", false, mrAList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Fifteen", false, getAllTeacherNames(), emptyStringList, emptyStringList);
	}

	@Test
	public void TestParserSmallList() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\smallClass.xlsx");

		assertEquals(2, SheetDissector.getClasses().length);
		assertEquals(6, SheetDissector.getStudents().length);

		assertTrue(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Miss One")).IsEll());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Miss One")).IsIEP());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Mr Two")).IsEll());
		assertTrue(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Mr Two")).IsIEP());

		checkStudentCharacteristics("A", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("B", true, new ArrayList<String>(Arrays.asList("Miss One")), emptyStringList,
				emptyStringList);
		checkStudentCharacteristics("C", false, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("D", true, getAllTeacherNames(), emptyStringList, emptyStringList);
		checkStudentCharacteristics("E", false, new ArrayList<String>(Arrays.asList("Miss One")), emptyStringList,
				emptyStringList);
		checkStudentCharacteristics("F", false, new ArrayList<String>(Arrays.asList("Miss One")), emptyStringList,
				emptyStringList);

	}

	@Test
	public void TestParserLargeList() throws IOException, ClassSetupException, SearchingException {
		ArrayList<String> nList = new ArrayList<String>(Arrays.asList("Ms N"));
		ArrayList<String> sList = new ArrayList<String>(Arrays.asList("Ms S"));
		ArrayList<String> lList = new ArrayList<String>(Arrays.asList("Ms L"));

		ArrayList<String> nsList = new ArrayList<String>(Arrays.asList("Ms N", "Ms S"));
		ArrayList<String> lsList = new ArrayList<String>(Arrays.asList("Ms L", "Ms S"));
		ArrayList<String> nslList = new ArrayList<String>(Arrays.asList("Ms N", "Ms S", "Ms L"));
		ArrayList<String> nlList = new ArrayList<String>(Arrays.asList("Ms N", "Ms L"));

		SheetDissector.ParseSheet("src\\test\\resources\\BigClass.xlsx");

		assertEquals(3, SheetDissector.getClasses().length);
		assertEquals(51, SheetDissector.getStudents().length);

		assertTrue(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Ms N")).IsIEP());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Ms S")).IsIEP());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Ms N")).IsEll());
		assertTrue(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Ms S")).IsEll());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Ms L")).IsIEP());
		assertFalse(SheetDissector.getClassroomById(NumberReference.findTeacherNumberByName("Ms L")).IsEll());

		checkStudentCharacteristics("One", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Two", true, sList, emptyStringList,
				new ArrayList<String>(Arrays.asList("Eighteen")));
		checkStudentCharacteristics("Three", true, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Four", true, lsList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Five", false, lList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Six", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Seven", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Eight", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Nine", false, nList, new ArrayList<String>(Arrays.asList("Forty", "Fifty")),
				emptyStringList);
		checkStudentCharacteristics("Ten", true, nslList, emptyStringList, emptyStringList);

		checkStudentCharacteristics("Eleven", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twelve", true, nlList, emptyStringList,
				new ArrayList<String>(Arrays.asList("Fourteen")));
		checkStudentCharacteristics("Thirteen", false, nlList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Fourteen", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Fifteen", true, nslList, new ArrayList<String>(Arrays.asList("Ten")),
				emptyStringList);
		checkStudentCharacteristics("Sixteen", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Seventeen", false, lList, emptyStringList,
				new ArrayList<String>(Arrays.asList("Fifty")));
		checkStudentCharacteristics("Eighteen", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Nineteen", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty", true, sList, emptyStringList, emptyStringList);

		checkStudentCharacteristics("Twenty-one", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Twenty-two", false, nslList, emptyStringList,
				new ArrayList<String>(Arrays.asList("Nineteen")));
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
		checkStudentCharacteristics("Thirty-three", true, nslList, new ArrayList<String>(Arrays.asList("One")),
				emptyStringList);
		checkStudentCharacteristics("Thirty-four", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-five", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-six", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-seven", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Thirty-eight", true, sList, new ArrayList<String>(Arrays.asList("Thirty")),
				emptyStringList);
		checkStudentCharacteristics("Thirty-nine", false, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty", false, nslList, emptyStringList, emptyStringList);

		checkStudentCharacteristics("Forty-one", true, sList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-two", false, lsList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-three", false, nList, emptyStringList,
				new ArrayList<String>(Arrays.asList("Forty-two")));
		checkStudentCharacteristics("Forty-four", true, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-five", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-six", false, nList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-seven", true, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Forty-eight", false, nslList, new ArrayList<String>(Arrays.asList("Forty")),
				emptyStringList);
		checkStudentCharacteristics("Forty-nine", false, nslList, emptyStringList, emptyStringList);
		checkStudentCharacteristics("Fifty", true, nslList, emptyStringList,
				new ArrayList<String>(Arrays.asList("Eight", "Five")));

		checkStudentCharacteristics("Fifty-one", false, nList, emptyStringList, emptyStringList);

	}

	private void checkStudentCharacteristics(String name, boolean isFemale, ArrayList<String> allowedTeachers,
			ArrayList<String> requiredStudents, ArrayList<String> forbiddenStudents) throws SearchingException {
		Student student = SheetDissector.getStudentById(NumberReference.findStudentNumberByName(name));
		assertEquals(isFemale, student.IsFemale());
		CommonTestFunctions.compareTeacherLists(allowedTeachers, student.getAllowedTeachers());
		CommonTestFunctions.compareIntegerLists(CommonTestFunctions.convertStringStudentsToNumber(requiredStudents),
				student.getRequiredStudents());
		CommonTestFunctions.compareIntegerLists(CommonTestFunctions.convertStringStudentsToNumber(forbiddenStudents),
				student.getForbiddenStudents());

	}

	private ArrayList<String> getAllTeacherNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Classroom clr : SheetDissector.getClasses())
			names.add(NumberReference.findTeacherNameByNumber(clr.getTeacherId()));
		return names;
	}
}
