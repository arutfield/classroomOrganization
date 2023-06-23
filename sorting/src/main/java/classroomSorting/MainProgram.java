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
		LinkedList<String> teacherNames = new LinkedList<String>();
		FileInputStream fis=new FileInputStream(new File("../sampleClassToArrange.xlsx"));  
		XSSFWorkbook workbook=new XSSFWorkbook(fis);   
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		boolean isInTeacherSection = false;
		boolean isInStudentSection = false;
		LinkedList<String> studentCharacteristicsList = new LinkedList<String>();
		for (Row row : sheet) {
			if (row.getLastCellNum() == 0)
				continue;
			Cell cell = row.getCell(0);
		    switch (cell.getCellType()) {
		        case STRING:
		        	if (cell.getStringCellValue().toLowerCase().equals("students")) {
		        		isInTeacherSection = false;
		        		isInStudentSection = true;
		        		for (int i=1; i<row.getLastCellNum(); i++) {
		        			if (row.getCell(i).getCellType() != CellType.STRING)
		        				continue;
		        			studentCharacteristicsList.add(row.getCell(i).getStringCellValue());
		        		}
		        		continue;
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
	           			teacherNames.add(cell.getStringCellValue());
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
		          		System.out.println("teacher names size: " + teacherNames.size());
		          		Student newStudent = new Student(cell.getStringCellValue(), teacherNames);
	           			for (int i=1; i<row.getLastCellNum(); i++) {
	           				Cell subCell = row.getCell(i);
	           				if (subCell == null)
	           					continue;
	           				if (subCell.getCellType() == CellType.STRING) {
		           				System.out.println("got cell " + i + ": " + (subCell == null ? "nothing" : subCell.getStringCellValue()));
		           				String category = studentCharacteristicsList.get(i-1).toLowerCase();
		           				if (category.equals("is female"))
		           					newStudent.setIsFemale(!subCell.getStringCellValue().equals("n"));
		           				else if (category.equals("must have teacher")) {
		           					checkTeacherIsInList(subCell.getStringCellValue(), classes);
		           					newStudent.setOnlyAllowedTeacher(subCell.getStringCellValue());
		           				}
		           				else if (category.equals("can't have teacher")) {
		           					String[] teachersNotAllowed = subCell.getStringCellValue().split(",");
		           					for (String teacherInList : teachersNotAllowed) {
			           					checkTeacherIsInList(teacherInList, classes);
			           					newStudent.addForbiddenTeacher(teacherInList);
		           					}
		           				}
		           				else if (category.equals("must have student")) {
		           					String[] studentsRequired = subCell.getStringCellValue().split(",");
		           					for (String studentInList : studentsRequired)
			           					newStudent.addRequiredClassmate(studentInList);
		           				}
		           				else if (category.equals("can't have student")) {
		           					String[] studentsForbidden = subCell.getStringCellValue().split(",");
		           					for (String studentInList : studentsForbidden)
			           					newStudent.addForbiddenClassmate(studentInList);
		           				}
		           				else if (category.equals("special circumstances")) {
		           					if (subCell.getCellType() == CellType.STRING) {
			           					String[] categories = subCell.getStringCellValue().split(",");
			           					for (String specialCategory : categories) {
			           						if (specialCategory.equals("ELL")) {
			           							for (Classroom currentClass : classes) {
			           								if (!currentClass.IsEll())
			           									newStudent.addForbiddenTeacher(currentClass.getTeacherName());
			           							}
			           						} else if (specialCategory.equals("504")) {
			           							for (Classroom currentClass : classes) {
			           								if (!currentClass.Is504())
			           									newStudent.addForbiddenTeacher(currentClass.getTeacherName());
			           							}
			           						}
			           					}		           						
		           					}
		           				}
	           				}
	           				else if (subCell.getCellType() == CellType.NUMERIC) {
	           					if (subCell.getNumericCellValue() == 504) {
	           						for (Classroom currentClass : classes) {
	           							if (!currentClass.Is504())
	           								newStudent.addForbiddenTeacher(currentClass.getTeacherName());
	           						}
	           					}
	           				} else
	           					throw new ClassSetupException("Unknown type: " + subCell.getCellType().name());
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
	
	private static boolean checkTeacherIsInList(String name, LinkedList<Classroom> classes) throws ClassSetupException {
		for (Classroom classroom : classes) {
			if (classroom.getTeacherName().equals(name)) {
				return true;
			}
		}
		throw new ClassSetupException("Unknown teacher: " + name);
		
	}
}
