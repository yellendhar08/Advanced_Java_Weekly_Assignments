package org.example.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseName;

    public Course(){}

    public Course(String courseName) {
        this.courseName = courseName;
    }

    @ManyToMany(mappedBy = "courseList")
    private List<Student> studentList;

    public List<Student> getStudentList(){
        return studentList;
    }
    public void setStudentList(List<Student> studentList){
        this.studentList=studentList;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}