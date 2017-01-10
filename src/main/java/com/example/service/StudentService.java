package com.example.service;

import java.util.ArrayList;

import com.example.domain.StudentDTO;

public interface StudentService {
	
	ArrayList<StudentDTO> getAllStudents();

	StudentDTO getStudentById(long id);

	void saveStudent(StudentDTO studentDTO);

	boolean studentExistById(long id);

	void deleteStudentById(long id);

}
