package com.jordanj.cmpt276.assignment2.controllers;

import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jordanj.cmpt276.assignment2.models.Student;
import com.jordanj.cmpt276.assignment2.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")
    public String getAllStudents(Model model) {
        System.out.println("Getting all students...");
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", students);
        return "students/showAll";
    }

    @PostMapping("/students/add")
    public String addStudent(
            @RequestParam("name") String name,
            @RequestParam("weight") Integer weight,
            @RequestParam("height") Integer height,
            @RequestParam("haircolor") String hairColor,
            @RequestParam("gpa") Double gpa,
            HttpServletResponse response
        ){   
        System.out.println("ADD student");
        
        Student student = new Student(name, weight, height, hairColor, gpa);
        studentRepo.save(student);

        response.setStatus(201);
        return "students/added";
    }

    @GetMapping("/students/{id}")
    public String getStudent(@PathVariable("id") Integer id, Model model) {
        Student student = studentRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "students/view";
    }

    @GetMapping("/students/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Student student = studentRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "students/update";
    }

    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable("id") Integer id, Student student, 
    BindingResult result, Model model) {
        if (result.hasErrors()) {
            student.setUid(id);
            return "students/update";
        }

        studentRepo.save(student);
        return "redirect:/students/view";
    }


    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, Model model) {
        Student student = studentRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        studentRepo.delete(student);
        model.addAttribute("students", studentRepo.findAll());
        return "students/viewAll";
    }
}
