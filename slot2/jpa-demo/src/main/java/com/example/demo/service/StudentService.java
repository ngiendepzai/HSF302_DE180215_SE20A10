package com.example.demo.service;

import com.example.demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Student addStudent(String name, String email, int age) {
        Student student = new Student(name, email, age);
        em.persist(student);
        return student;
    }

    @Transactional(readOnly = true)
    public Student findStudentById(Long id) {
        Student student = em.find(Student.class, id);
        if (student == null) {
            throw new EntityNotFoundException("Khong tim thay student co id = " + id);
        }
        return student;
    }

    @Transactional
    public void updateStudent(Long id, String fullName, String email, Integer age) {
        Student student = findStudentById(id);

        student.setFullName(fullName);
        student.setEmail(email);
        student.setAge(age);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = findStudentById(id);
        em.remove(student);
    }

    @Transactional(readOnly = true)
    public void printAll() {
        em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList()
                .forEach(System.out::println);
    }
}
