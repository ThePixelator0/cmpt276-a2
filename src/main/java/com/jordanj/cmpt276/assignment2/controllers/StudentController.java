package com.jordanj.cmpt276.assignment2.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jordanj.cmpt276.assignment2.models.Student;
import com.jordanj.cmpt276.assignment2.models.StudentRepository;

@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")
    public String getAllStudents(Model model) {
        System.out.println("Getting all students...");
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", model);
        return "students/showAll";
    }

    @GetMapping(value="/students/addStudent")
    public String addStudent() {
        Student student = new Student();
        studentRepo.save(student);
        return "students/added";
    }
}
