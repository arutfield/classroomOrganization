package classroomSorting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import exceptions.ClassSetupException;
import exceptions.SearchingException;

public class MainProgram {
	private static long startTimeMilliseconds;
	private static long timeoutTimeMilliseconds = 1200000;
	private static boolean timeoutFlag = false;
	
	public static void main(String[] args) throws IOException, ClassSetupException, SearchingException {
		LocalTime startTime = LocalTime.now();
		startTimeMilliseconds = System.currentTimeMillis();
		System.out.println("Start time: " + startTime);
		if (args.length < 1) {
			System.out.println("No file found");
			System.exit(1);
		}
		SheetDissector.ParseSheet(args[0]);
		ArrayList<Classroom[]> allSolutions = new ArrayList<Classroom[]>();
		if (args.length > 1 && args[1].equals("-a")) {
			allSolutions = ClassroomSorter.solveAllClassrooms();			
		} else {
			Classroom[] classrooms = ClassroomSorter.solveClassrooms();
			if (classrooms != null)
				allSolutions.add(classrooms);
		}
		if (allSolutions.isEmpty())
		{
			System.out.println("No solution found");
			return;
		}
		System.out.println("Outputting file with " + allSolutions.size() + " solution(s)");
		args[0] = args[0].replace("\\", "/");
		String[] fileNameComponents = args[0].split("/");
		String fileName = fileNameComponents[fileNameComponents.length-1];
		WriteSolution(allSolutions, fileName.substring(0, fileName.length()-5));
		LocalTime endTime = LocalTime.now();
		System.out.println("Done. End time: " + endTime);
	}

	
	private static void WriteSolution(ArrayList<Classroom[]> solutions, String fileName) throws IOException {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
		XSSFFont font = (XSSFFont) workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);

		XSSFCellStyle studentHeaderStyle = (XSSFCellStyle) workbook.createCellStyle();
		XSSFFont studentFont = (XSSFFont) workbook.createFont();
		studentFont.setFontName("Arial");
		studentFont.setFontHeightInPoints((short) 12);
		studentHeaderStyle.setFont(studentFont);
		
		int column = 16384;
		int sheetCounter = 0;
		SXSSFSheet sheet = null;
		for (int w=0; w<solutions.size(); w++) {
			Classroom[] classrooms = solutions.get(w);
			if (column > 16384 - classrooms.length - 1) {
				if (sheet != null)
					sheet.flushRows();
				sheetCounter++;
				sheet = workbook.createSheet("Solutions_" + sheetCounter);
				column = 0;
			}
			if (sheet.getLastRowNum() < 0)
				sheet.createRow(0);

			for (int i=0; i<classrooms.length; i++) {
				if (System.currentTimeMillis() - startTimeMilliseconds > timeoutTimeMilliseconds) {
					timeoutFlag = true;
					System.out.println("Timeout triggered when exporting files on solution #" + w + ". Truncating solution list");
					break;
				}
				sheet.setColumnWidth(column, 6000);
				SXSSFRow header = sheet.getRow(0);
				Cell headerCell = header.createCell(column);
				headerCell.setCellValue(NumberReference.findTeacherNameByNumber(classrooms[i].getTeacherId()));
				headerCell.setCellStyle(headerStyle);
				for (int j=0; j<classrooms[i].getStudentIds().size(); j++) {
					int r = j+1;
					SXSSFRow studentHeader = null;
					if (sheet.getLastRowNum() < r)
						studentHeader = sheet.createRow(r);
					else
						studentHeader = sheet.getRow(r);
					Cell studentHeaderCell = studentHeader.createCell(column);
					studentHeaderCell.setCellValue(NumberReference.findStudentNameByNumber(classrooms[i].getStudentIds().get(j)));
					studentHeaderCell.setCellStyle(studentHeaderStyle);
				}
				column++;
			}
			column++;
			if (timeoutFlag)
				break;
		}
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + fileName + "_solution.xlsx";
		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		workbook.write(outputStream);
		workbook.close();
	}
}
