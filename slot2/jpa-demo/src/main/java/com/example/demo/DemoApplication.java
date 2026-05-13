package com.example.demo;

import com.example.demo.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentService service) {
        return args -> {
            var student1 = service.addStudent("Nguyen Van A", "a@fpt.edu.vn", 20);
            var student2 = service.addStudent("Tran Thi B", "b@fpt.edu.vn", 21);
            service.updateStudent(student1.getId(), "Nguyen Van A Updated", "a.updated@fpt.edu.vn", 22);
            System.out.println(service.findStudentById(student1.getId()));
            service.deleteStudent(student2.getId());
            service.printAll();
        };
    }
}
