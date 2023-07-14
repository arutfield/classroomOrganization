package classroomSorting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
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
import exceptions.SearchingException;

public class SheetDissector {
	private static Classroom[] classes;
	private static Student[] students;

	public static Classroom[] getClasses() {
		return classes;
	}

	public static Student[] getStudents() {
		return students;
	}

	
	public static void ParseSheet(String file) throws IOException, ClassSetupException, SearchingException {
		LinkedList<Classroom> classesList = new LinkedList<Classroom>();
		LinkedList<Student> studentsList = new LinkedList<Student>();
		LinkedList<String> teacherNames = new LinkedList<String>();
		FileInputStream fis = new FileInputStream(new File(file));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

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
				String cellValue = cell.getStringCellValue();
				if (cellValue.toLowerCase().equals("students")) {
					isInTeacherSection = false;
					isInStudentSection = true;
					for (int i = 1; i < row.getLastCellNum(); i++) {
						if (row.getCell(i).getCellType() != CellType.STRING)
							continue;
						studentCharacteristicsList.add(row.getCell(i).getStringCellValue());
					}
					continue;
				}
				if (cellValue.toLowerCase().equals("teachers")) {
					if (!classesList.isEmpty())
						throw new ClassSetupException("Cannot have multiple teacher sections");
					else {
						isInTeacherSection = true;
						continue;
					}
				}
				if (isInTeacherSection) {
					Classroom newClassroom = new Classroom(cellValue);
					teacherNames.add(cellValue);
					for (int i = 1; i < row.getLastCellNum(); i++) {
						Cell subCell = row.getCell(i);
						if (subCell.getStringCellValue().toLowerCase().equals("ell"))
							newClassroom.enableEll();
						else if (subCell.getStringCellValue().toLowerCase().equals("iep"))
							newClassroom.enableIEP();
					}
					classesList.add(newClassroom);
				}
				if (isInStudentSection) {
					studentsList.add(createStudent(row, teacherNames, classesList, studentCharacteristicsList));
				}
				break;
			default:
				break;
			}

		}
		
		students = new Student[studentsList.size()];
		for (int i=0; i<studentsList.size(); i++)
			students[i] = studentsList.get(i);
		classes = new Classroom[classesList.size()];
		for (int i=0; i<classesList.size(); i++)
			classes[i] = classesList.get(i);
		workbook.close();

	}
	
	private static Student createStudent(Row row, LinkedList<String> teacherNames, LinkedList<Classroom> classes,
			LinkedList<String> studentCharacteristicsList) throws ClassSetupException, SearchingException {
		Student newStudent = null;
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (i == 0) {
				Integer newStudentId = NumberReference.addStudent(row.getCell(i).getStringCellValue());
				newStudent = new Student(newStudentId, teacherNames);
				continue;
			}
			Cell subCell = row.getCell(i);
			if (subCell == null)
				continue;
			if (subCell.getCellType() == CellType.STRING) {
				String category = studentCharacteristicsList.get(i - 1).toLowerCase();
				if (category.equals("is female"))
					newStudent.setIsFemale(!subCell.getStringCellValue().equals("n"));
				else if (category.equals("must have teacher")) {
					checkTeacherIsInList(subCell.getStringCellValue(), classes);
					newStudent.setOnlyAllowedTeacher(subCell.getStringCellValue());
				} else if (category.equals("can't have teacher")) {
					String[] teachersNotAllowed = subCell.getStringCellValue().split(",");
					for (String teacherInList : teachersNotAllowed) {
						checkTeacherIsInList(teacherInList, classes);
						newStudent.addForbiddenTeacher(teacherInList);
					}
				} else if (category.equals("must have student")) {
					String[] studentsRequired = subCell.getStringCellValue().split(",");
					for (String studentInList : studentsRequired)
						newStudent.addRequiredClassmate(studentInList);
				} else if (category.equals("can't have student")) {
					String[] studentsForbidden = subCell.getStringCellValue().split(",");
					for (String studentInList : studentsForbidden)
						newStudent.addForbiddenClassmate(studentInList);
				} else if (category.equals("special circumstances")) {
					if (subCell.getCellType() == CellType.STRING) {
						String[] categories = subCell.getStringCellValue().split(",");
						for (String specialCategory : categories) {
							if (specialCategory.equals("ELL")) {
								for (Classroom currentClass : classes) {
									if (!currentClass.IsEll())
										newStudent.addForbiddenTeacher(currentClass.getTeacherName());
								}
							}
							else if (specialCategory.equals("IEP")) {
								for (Classroom currentClass : classes) {
									if (!currentClass.IsIEP())
										newStudent.addForbiddenTeacher(currentClass.getTeacherName());
								}
							}
						}
					}
				}
			} else if (subCell.getCellType() == CellType.NUMERIC) {
				throw new ClassSetupException("unknown number found " + subCell.getNumericCellValue());
			} else
				throw new ClassSetupException("Unknown type: " + subCell.getCellType().name());
		}
		return newStudent;
	}

	private static boolean checkTeacherIsInList(String name, LinkedList<Classroom> classes) throws ClassSetupException {
		for (Classroom classroom : classes) {
			if (classroom.getTeacherName().equals(name)) {
				return true;
			}
		}
		throw new ClassSetupException("Unknown teacher: " + name);

	}

	public static Classroom getClassroomByName(String teacher) throws SearchingException {
		for (Classroom cl : SheetDissector.getClasses()) {
			if (cl.getTeacherName().equals(teacher))
				return cl;
		}
		throw new SearchingException("No class with name " + teacher);
	}

	public static Student getStudentById(Integer id) throws SearchingException {
		for (Student stu : SheetDissector.getStudents()) {
			if (stu.getId() == id)
				return stu;
		}
		throw new SearchingException("No student with id " + id);
	}

	public static int getTotalFemaleStudents() {
		int total = 0;
		for (Student student : students) {
			if (student.IsFemale())
				total++;
		}
		return total;
	}
	
}
