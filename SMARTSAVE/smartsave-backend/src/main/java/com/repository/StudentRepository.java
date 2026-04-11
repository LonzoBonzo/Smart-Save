package com.repository;

import java.util.List;
import com.model.Student;

public interface StudentRepository {

    Student save(Student student);

    Student findById(Long id);

    List<Student> findAll();

    void delete(Long id);
}