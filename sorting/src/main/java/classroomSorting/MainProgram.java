package classroomSorting;

import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

public class MainProgram {
	public static void main(String[] args) throws IOException {
		System.out.println("Hello, World: " + new java.io.File(".").getCanonicalPath()); 
		
		FileInputStream fis=new FileInputStream(new File("../sampleClassToArrange.xlsx"));  
		//creating workbook instance that refers to .xls file  
		XSSFWorkbook workbook=new XSSFWorkbook(fis);   
		
		XSSFSheet sheet = workbook.getSheetAt(0);

		HashMap<Integer, List<String>> data = new HashMap<Integer, List<String>>();
		int i = 0;
		for (Row row : sheet) {
		    data.put(i, new ArrayList<String>());
		    for (Cell cell : row) {
		        switch (cell.getCellType()) {
		            case STRING:
		            	break;
		            case NUMERIC:
		            	break;
		            case BOOLEAN:
		            	break;
		            case FORMULA:
		            	break;
		            default: data.get(new Integer(i)).add(" ");
		        }
		    }
		    i++;
		}
	}
}
