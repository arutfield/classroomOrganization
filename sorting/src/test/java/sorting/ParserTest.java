package sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import classroomSorting.Classroom;
import classroomSorting.SheetDissector;
import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class ParserTest {

	@Test
	public void TestParser() throws IOException, ClassSetupException, SearchingException {
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
	
}
