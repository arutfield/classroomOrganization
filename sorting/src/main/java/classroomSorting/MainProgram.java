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
		FileInputStream fis=new FileInputStream(new File("../sampleClassToArrange.xlsx"));  
		XSSFWorkbook workbook=new XSSFWorkbook(fis);   
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		boolean isInTeacherSection = false;
		for (Row row : sheet) {
			Classroom newClassroom = null;
			boolean isStartOfRow = true;
		    for (Cell cell : row) {
		        switch (cell.getCellType()) {
		            case STRING:
		            	if (cell.getStringCellValue().isBlank())
		            		continue;
		            	if (cell.getStringCellValue().toLowerCase().equals("teachers")) {
		            		if (isInTeacherSection) {
		            			throw new ClassSetupException("Cannot have multiple teacher sections");
		            		} else {
		            			isInTeacherSection = true;
		            			continue;
		            		}
		            	}
		            	if (isInTeacherSection) {
		            		if (cell.getStringCellValue().toLowerCase().equals("students")) {
		            			isInTeacherSection = false;
		            			continue;
		            		}
		            		if (isStartOfRow) {
		            			newClassroom = new Classroom(cell.getStringCellValue());
		            		} else {
		            			if (cell.getStringCellValue().toLowerCase().equals("ell")) {
		            				newClassroom.enableEll();
		            			} else if (cell.getStringCellValue().toLowerCase().equals("504 plan")) {
		            				newClassroom.enable504();
		            			}
		            		}
		            		
		            	}
		            	
		            	System.out.println("string: " + cell.getStringCellValue());
		            	break;
		            case NUMERIC:
		            	System.out.println("numeric: " + cell.getNumericCellValue());
		            	break;
		            case BOOLEAN:
		            	System.out.println("boolean: " + cell.getBooleanCellValue());
		            	break;
		            case FORMULA:
		            	System.out.println("formula: " + cell.getCellFormula());
		            	break;
		            default:
		            	break;
		        }
		        isStartOfRow = false;
		        
		    }
		    if (newClassroom != null)
	        	classes.add(newClassroom);
		}
		workbook.close();
	}
}
