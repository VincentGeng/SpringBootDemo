package com.example.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.StudentDTO;
import com.example.service.StudentService;

@RestController
@RequestMapping(value="/student")
public class StudentController extends BaseController{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private StudentService studentService;
	
	@Autowired
    public void setStudentServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }
	
	@Override
	protected String getModuleFolder() {
		return "student/";
	}
	
	@RequestMapping(value="/",method = RequestMethod.GET)
	public ArrayList<StudentDTO> getAllStudents(){
		return this.studentService.getAllStudents();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public StudentDTO getStudent(@PathVariable long id){
	   return this.studentService.getStudentById(id);
	}
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public StudentDTO addStudent(@RequestParam(value="name") String name
	      ,@RequestParam(value="subject", defaultValue = "unknown") String subject){

		StudentDTO student = new StudentDTO(name, subject);
		this.studentService.saveStudent(student);
	    return student;

	}
	
	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public StudentDTO updateStudent(@RequestBody StudentDTO student) throws Exception {
	 
	   if(this.studentService.studentExistById(student.getId())){
		   this.studentService.saveStudent(student);
	   }else{
	      throw new Exception("Student "+student.getId()+" does not exists");
	   }
	 
	   return student;
	}
	
	@RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
	public StudentDTO deleteStudent(@PathVariable long id) throws Exception {
	 
		StudentDTO student;
	 
		if(this.studentService.studentExistById(id)){
			student = this.studentService.getStudentById(id);
			this.studentService.deleteStudentById(id);
		}else{
			throw new Exception("Student "+id+" does not exists");
		}
		
		return student;
	}
	
	@RequestMapping(value="/test",method = RequestMethod.GET)
	public ModelAndView test(){
		return goToCurrentFolderPage("blank");
	}

}