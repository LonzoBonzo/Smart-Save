package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.model.Student;

@Service
public class StudentService {

    private List<Student> students = new ArrayList<>();

    public Student createStudent(Student student) {
        students.add(student);
        return student;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student getStudentById(Long id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public void deleteStudent(Long id) {
        students.removeIf(s -> s.getId().equals(id));
    }
}