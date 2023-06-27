package classroomSorting;

import java.io.IOException;

import exceptions.ClassSetupException;

public class MainProgram {
	public static void main(String[] args) throws IOException, ClassSetupException {
		System.out.println("Hello, World: " + new java.io.File(".").getCanonicalPath());
		SheetDissector.ParseSheet("../sampleClassToArrange.xlsx");
		System.out.println("finished");
	}

}
