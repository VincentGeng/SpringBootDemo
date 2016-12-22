package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class StudentDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
    private String name;
	
	@Column(nullable = false)
	private String subject;
 
    public StudentDTO() {
    }
 
    public StudentDTO(String name, String subject) {
        this.id = (new Date()).getTime();
        this.name = name;
        this.subject = subject;
    }
 
    public long getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getSubject() {
        return subject;
    }
 
    public void setSubject(String subject) {
        this.subject = subject;
    }
 
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
    
}

