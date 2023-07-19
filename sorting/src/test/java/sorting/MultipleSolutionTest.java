package sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import classroomSorting.Classroom;
import classroomSorting.ClassroomSorter;
import classroomSorting.NumberReference;
import classroomSorting.SheetDissector;
import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class MultipleSolutionTest {

	@Test
	public void TestSampleOfManyStudents() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\BigClass.xlsx");
		ArrayList<Classroom[]> results = ClassroomSorter.solveAllClassrooms();
		for (Classroom[] result : results) {
			assertEquals(3, result.length);
			int totalFemaleStudents = 0;
			for (Classroom classroom : result) {
				ArrayList<Integer> studentIds = classroom.getStudentIds();
				assertTrue(studentIds.size() == 17);
				totalFemaleStudents += classroom.getTotalFemaleStudents();
				assertTrue(classroom.getTotalFemaleStudents() == 6 || classroom.getTotalFemaleStudents() == 7);
				if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Ms N")) {
					CommonTestFunctions.checkIfStudentsInClass(
							new ArrayList<String>(Arrays.asList("One", "Three", "Nine", "Twenty-one", "Twenty-six",
									"Thirty-six", "Thirty-seven", "Forty-three", "Forty-four", "Forty-six", "Fifty-one")),
							studentIds);
					CommonTestFunctions.checkIfStudentsNotInClass(new ArrayList<String>(Arrays.asList("Four", "Forty-two")), studentIds);
					// based on requests
					CommonTestFunctions.checkIfStudentsInClass(new ArrayList<String>(Arrays.asList("Forty", "Fifty")), studentIds);
				} else if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Ms S")) {
					CommonTestFunctions.checkIfStudentsInClass(new ArrayList<String>(Arrays.asList("Two", "Six", "Eight", "Fourteen", "Twenty",
							"Twenty-four", "Twenty-nine", "Thirty-four", "Thirty-eight", "Thirty-nine", "Forty-one")),
							studentIds);
					CommonTestFunctions.checkIfStudentsNotInClass(new ArrayList<String>(Arrays.asList("Twelve", "Thirteen")), studentIds);
					CommonTestFunctions.checkIfStudentsInClass(new ArrayList<String>(Arrays.asList("Thirty")), studentIds);
				} else if (classroom.getTeacherId() == NumberReference.findTeacherNumberByName("Ms L")) {
					CommonTestFunctions.checkIfStudentsInClass(new ArrayList<String>(Arrays.asList("Five", "Seventeen")), studentIds);
					CommonTestFunctions.checkIfStudentsNotInClass(new ArrayList<String>(Arrays.asList("Twenty-nine", "Fifty-one")),
							studentIds);
				}
				CommonTestFunctions.hasFriend(studentIds, "Fifteen", "Ten");
				CommonTestFunctions.hasFriend(studentIds, "Thirty-three", "One");
				CommonTestFunctions.hasFriend(studentIds, "Forty-eight", "Forty");
				CommonTestFunctions.hasNoEnemy(studentIds, "Two", "Eighteen");
				CommonTestFunctions.hasNoEnemy(studentIds, "Twelve", "Fourteen");
				CommonTestFunctions.hasNoEnemy(studentIds, "Seventeen", "Fifty");
				CommonTestFunctions.hasNoEnemy(studentIds, "Twenty-two", "Nineteen");
				CommonTestFunctions.hasNoEnemy(studentIds, "Forty-three", "Forty-two");
				CommonTestFunctions.hasNoEnemy(studentIds, "Fifty", "Eight");
				CommonTestFunctions.hasNoEnemy(studentIds, "Fifty", "Five");
			}
			assertEquals(19, totalFemaleStudents);			
		}
	}

	@Test
	public void TestAllNoRequirements() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\smallClassNoReq.xlsx");
		ArrayList<Classroom[]> results = ClassroomSorter.solveAllClassrooms();
		assertEquals(20, results.size());
		checkIfResultsUnique(results);
	}
	
	@Test
	public void TestAllUnevenClasses() throws IOException, ClassSetupException, SearchingException {
		SheetDissector.ParseSheet("src\\test\\resources\\unevenClasses.xlsx");
		ArrayList<Classroom[]> results = ClassroomSorter.solveAllClassrooms();
		checkIfResultsUnique(results);
	}

	private void checkIfResultsUnique(ArrayList<Classroom[]> results) {
		// check all combinations are unique
		for (int i=0; i<results.size(); i++) {
			Classroom[] resultFromI = results.get(i);
			for (int j=0; j<results.size(); j++) {
				if (i==j)
					continue;
				Classroom[] resultFromJ = results.get(j);
				
				//iterate all classrooms, make sure there's a difference somewhere
				boolean differenceFound = false;
				for (Classroom classFromResultI : resultFromI) {
					for (Classroom classFromResultJ : resultFromJ) {
						if (classFromResultI.getTeacherId() == classFromResultJ.getTeacherId()) {
							// check student lists are not the exact same
							for (int studentIdFromI : classFromResultI.getStudentIds()) {
								if (!classFromResultJ.getStudentIds().contains(studentIdFromI)) {
									differenceFound = true;
									break;
								}
							}
							if (!differenceFound)
								differenceFound = classFromResultI.getStudentIds().size() != classFromResultJ.getStudentIds().size();
						}
					}
				}
				assertTrue(differenceFound);
			}
		}
		
	}
}
