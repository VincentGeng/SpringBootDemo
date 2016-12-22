package com.example.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.StudentDAO;
import com.example.model.StudentDTO;
import com.example.service.StudentService;

@Transactional
@Service
public class StudentServiceImpl implements StudentService{
	
	private StudentDAO studentDAO;
	
	@Autowired
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

	public ArrayList<StudentDTO> getAllStudents() {
		ArrayList<StudentDTO> students = (ArrayList<StudentDTO>) studentDAO.findAll();
		return students;
	}

	@Override
	public StudentDTO getStudentById(long id) {
		StudentDTO studentDTO = studentDAO.findOne(id);
		return studentDTO;
	}

	@Override
	public void saveStudent(StudentDTO studentDTO) {
		studentDAO.save(studentDTO);
	}

	@Override
	public boolean studentExistById(long id) {
		if(studentDAO.findOne(id)!=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void deleteStudentById(long id) {
		studentDAO.delete(id);
	}

}
