package com.example.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.model.StudentDTO;

public interface StudentDAO extends CrudRepository<StudentDTO, Long> {

	Page<StudentDTO> findAll(Pageable pageable);
	
}
