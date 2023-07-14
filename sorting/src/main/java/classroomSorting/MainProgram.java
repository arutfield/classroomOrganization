package classroomSorting;

import java.io.IOException;
import java.util.LinkedList;

import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class MainProgram {
	public static void main(String[] args) throws IOException, ClassSetupException, SearchingException {
		System.out.println("Hello, World: " + new java.io.File(".").getCanonicalPath());
		SheetDissector.ParseSheet("src/test/resources/sampleClassToArrange.xlsx");
		Classroom[] classrooms = ClassroomSorter.solveClassrooms();
		if (classrooms == null)
			System.out.println("no solution found");
		else {
			for (Classroom classroom : classrooms) {
				System.out.println(classroom.toString());
			}
		}
	}

}
