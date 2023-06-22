package classroomSorting;

import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import exceptions.ClassSetupException; 

public class MainProgram {
	public static void main(String[] args) throws IOException, ClassSetupException {
		System.out.println("Hello, World: " + new java.io.File(".").getCanonicalPath()); 
		LinkedList<Classroom> classes = new LinkedList<Classroom>();
		LinkedList<Student> students = new LinkedList<Student>();
		FileInputStream fis=new FileInputStream(new File("../sampleClassToArrange.xlsx"));  
		XSSFWorkbook workbook=new XSSFWorkbook(fis);   
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		boolean isInTeacherSection = false;
		boolean isInStudentSection = false;
		for (Row row : sheet) {
			if (row.getLastCellNum() == 0)
				continue;
			Cell cell = row.getCell(0);
		    switch (cell.getCellType()) {
		        case STRING:
		        	if (cell.getStringCellValue().toLowerCase().equals("students")) {
		        		isInTeacherSection = false;
		        		isInStudentSection = true;
		        	}
		          	if (cell.getStringCellValue().toLowerCase().equals("teachers")) {
		           		if (!classes.isEmpty())
		           			throw new ClassSetupException("Cannot have multiple teacher sections");
		           		else {
		           			isInTeacherSection = true;
		           			continue;
		           		}
		           	}
		          	if (isInTeacherSection) {
	           			Classroom newClassroom = new Classroom(cell.getStringCellValue());
	           			for (int i=1; i<row.getLastCellNum(); i++) {
	           				Cell subCell = row.getCell(i);
		           			if (subCell.getStringCellValue().toLowerCase().equals("ell"))
		           				newClassroom.enableEll();
		           			else if (subCell.getStringCellValue().toLowerCase().equals("504 plan"))
		           				newClassroom.enable504();
	           			}
			        	classes.add(newClassroom);
		          	}
		          	if (isInStudentSection) {
		          		Student newStudent = new Student(cell.getStringCellValue());
	           			for (int i=1; i<row.getLastCellNum(); i++) {
	           				Cell subCell = row.getCell(i);
	           				if (subCell == null)
	           					continue;
	           				if (subCell.getCellType() == CellType.STRING)
	           					System.out.println("got cell: " + (subCell == null ? "nothing" : subCell.getStringCellValue()));
	           				else if (subCell.getCellType() == CellType.NUMERIC)
	           					System.out.println("got numeric cell: " + (subCell == null ? "nothing" : subCell.getNumericCellValue()));
	           			}
	           			students.add(newStudent);
		          	}
		            break;
		        default:
		            break;
		    }
		        
		}
		workbook.close();
	}
}
