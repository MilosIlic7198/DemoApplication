package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service /*this class has to be a spring bean to be autowired and instantiated!*/
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmail(student.getEmail());
        if (optionalStudent.isPresent()) {
            throw new IllegalStateException("email taken!");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("student with id = " + id + " does not exist!");
        }
        studentRepository.deleteById(id);
    }

    @Transactional /*we are not using any queries below because the entity goes into manage state*/
    public void updateStudentInfo(Long id, Student student) {
        Optional<Student> s = studentRepository.findById(id);
        if (!s.isPresent()) {
            throw new IllegalStateException("student with id = " + id + " does not exist!");
        }
        s.get().setName(student.getName());
        s.get().setEmail(student.getEmail());

        //or
        /*
        Student s2 = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("student with id = " + id + " does not exist!"));

        if (name != null && name.length() > 0 && !Objects.equals(s2.getName(), name)) {
            s2.setName(name);
        }
        if (email != null && email.length() > 0 && !Objects.equals(s2.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken!");
            }
            s2.setEmail(email);
        }
         */
    }
}
