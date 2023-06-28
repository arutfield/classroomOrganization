package classroomSorting;

import java.io.IOException;
import java.util.LinkedList;

import exceptions.ClassSetupException;

public class MainProgram {
	public static void main(String[] args) throws IOException, ClassSetupException {
		System.out.println("Hello, World: " + new java.io.File(".").getCanonicalPath());
		SheetDissector.ParseSheet("src/test/resources/smallClass.xlsx");
		LinkedList<Classroom> classrooms = ClassroomSorter.solveClassrooms();
		System.out.println("finished: " + classrooms.size());
	}

}
