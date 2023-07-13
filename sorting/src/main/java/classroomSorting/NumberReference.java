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
		//throw new SearchingException("Unable to find " + name + " in list of students");
	}

	public static Integer addStudent(String studentName) {
		if (students.containsValue(studentName))
			return findStudentNumberByName(studentName);
		int id = students.size();
		students.put(id, studentName);
		return id;
	}

	
}
