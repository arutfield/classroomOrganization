package classroomSorting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class MainProgram {
	public static void main(String[] args) throws IOException, ClassSetupException, SearchingException {
		if (args.length < 1) {
			System.out.println("No file found");
			System.exit(1);
		}
		SheetDissector.ParseSheet(args[0]);
		Classroom[] classrooms = ClassroomSorter.solveClassrooms();
		if (classrooms == null)
			System.out.println("no solution found");
		else {
			String[] fileNameComponents = args[0].split("/");
			String fileName = fileNameComponents[fileNameComponents.length-1];
			
			WriteSolution(classrooms, fileName.substring(0, fileName.length()-5));
			for (Classroom classroom : classrooms) {
				System.out.println(classroom.toString());
			}
		}
	}

	
	private static void WriteSolution(Classroom[] classrooms, String fileName) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Solution");
		sheet.createRow(0);
		for (int i=0; i<classrooms.length; i++) {
			sheet.setColumnWidth(i, 6000);
			XSSFRow header = sheet.getRow(0);
			XSSFCellStyle headerStyle = workbook.createCellStyle();

			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 16);
			font.setBold(true);
			headerStyle.setFont(font);
			
			Cell headerCell = header.createCell(i);
			headerCell.setCellValue(NumberReference.findTeacherNameByNumber(classrooms[i].getTeacherId()));
			headerCell.setCellStyle(headerStyle);
			for (int j=0; j<classrooms[i].getStudentIds().size(); j++) {
				int r = j+1;
				XSSFRow studentHeader = null;
				if (sheet.getLastRowNum() < r)
					studentHeader = sheet.createRow(r);
				else
					studentHeader = sheet.getRow(r);
				XSSFCellStyle studentHeaderStyle = workbook.createCellStyle();

				XSSFFont studentFont = ((XSSFWorkbook) workbook).createFont();
				studentFont.setFontName("Arial");
				studentFont.setFontHeightInPoints((short) 12);
				studentHeaderStyle.setFont(studentFont);
				
				Cell studentHeaderCell = studentHeader.createCell(i);
				studentHeaderCell.setCellValue(NumberReference.findStudentNameByNumber(classrooms[i].getStudentIds().get(j)));
				studentHeaderCell.setCellStyle(studentHeaderStyle);

			}
			
		}
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + fileName + "_solution.xlsx";

		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		workbook.write(outputStream);
		workbook.close();
	}
}
