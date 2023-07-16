package classroomSorting;

import java.util.HashMap;

import exceptions.SearchingException;

public class NumberReference {
	private static HashMap<Integer, String> teachers = new HashMap<Integer, String>();
	private static HashMap<Integer, String> students = new HashMap<Integer, String>();
	
	public static String findStudentNameByNumber(int number) {
		return students.get(number);
	}
	
	public static Integer findStudentNumberByName(String name){
		for (Integer studentNumber : students.keySet()) {
			if (students.get(studentNumber).equals(name))
				return studentNumber;
		}
		return addStudent(name);
	}

	public static Integer addStudent(String studentName) {
		if (students.containsValue(studentName))
			return findStudentNumberByName(studentName);
		int id = students.size();
		students.put(id, studentName);
		return id;
	}

	public static String findTeacherNameByNumber(int number) {
		return teachers.get(number);
	}
	
	public static Integer findTeacherNumberByName(String name) throws SearchingException{
		for (Integer teacherNumber : teachers.keySet()) {
			if (teachers.get(teacherNumber).equals(name))
				return teacherNumber;
		}
		throw new SearchingException("Unable to find teacher " + name);
	}

	public static Integer addTeacher(String teacherName) throws SearchingException {
		if (teachers.containsValue(teacherName))
			return findTeacherNumberByName(teacherName);
		int id = teachers.size();
		teachers.put(id, teacherName);
		return id;
	}
	
}
