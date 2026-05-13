package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class studentTest {

    @Autowired
    private StudentService studentService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testAddStudent() {
        Student saved = studentService.addStudent("Le Van C", "c-test@fpt.edu.vn", 19);

        entityManager.flush();
        entityManager.clear();

        Student student = entityManager.find(Student.class, saved.getId());

        assertNotNull(student);
        assertEquals("Le Van C", student.getFullName());
        assertEquals("c-test@fpt.edu.vn", student.getEmail());
        assertEquals(19, student.getAge());
    }

    @Test
    void testUpdateStudent() {
        Student saved = studentService.addStudent("Pham Thi D", "d-test@fpt.edu.vn", 20);

        entityManager.flush();
        entityManager.clear();

        studentService.updateStudent(
                saved.getId(),
                "Pham Thi D Updated",
                "d-updated@fpt.edu.vn",
                21
        );

        entityManager.flush();
        entityManager.clear();

        Student student = entityManager.find(Student.class, saved.getId());

        assertNotNull(student);
        assertEquals("Pham Thi D Updated", student.getFullName());
        assertEquals("d-updated@fpt.edu.vn", student.getEmail());
        assertEquals(21, student.getAge());
    }

    @Test
    void testFindStudentById() {
        Student saved = studentService.addStudent("Vo Thi F", "f-test@fpt.edu.vn", 24);

        entityManager.flush();
        entityManager.clear();

        Student student = studentService.findStudentById(saved.getId());

        assertNotNull(student);
        assertEquals(saved.getId(), student.getId());
        assertEquals("Vo Thi F", student.getFullName());
        assertEquals("f-test@fpt.edu.vn", student.getEmail());
        assertEquals(24, student.getAge());
    }

    @Test
    void testFindStudentById_NotFound() {
        assertThrows(
                EntityNotFoundException.class,
                () -> studentService.findStudentById(999L)
        );
    }

    @Test
    void testDeleteStudent() {
        Student saved = studentService.addStudent("Hoang Van E", "e-test@fpt.edu.vn", 23);

        entityManager.flush();
        entityManager.clear();

        studentService.deleteStudent(saved.getId());

        entityManager.flush();
        entityManager.clear();

        Student student = entityManager.find(Student.class, saved.getId());

        assertNull(student);
    }

    @Test
    void testDeleteStudent_NotFound() {
        assertThrows(
                EntityNotFoundException.class,
                () -> studentService.deleteStudent(999L)
        );
    }
}
