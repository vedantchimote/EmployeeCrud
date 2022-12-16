package tech.vedantchimote.springboot.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vedantchimote.springboot.exception.ResourceNotFoundException;
import tech.vedantchimote.springboot.model.Student;
import tech.vedantchimote.springboot.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;

  //get all the students
  @GetMapping("/students")
  public List<Student> getAllStudents() {
    return (List<Student>) studentRepository.findAll();
  }

  //just for fun
  @GetMapping("/randomStudent")
  public ResponseEntity<Student> getRamdomStudent() {
    int max = 100;
    int min = 1;
    int range = max - min + 1;
    int rand = (int) (Math.random() * range) + min;

    long randomStudent = rand;

    Student student = studentRepository.findById(randomStudent).orElseThrow(
        () -> new ResourceNotFoundException("Tumhara maal humare paas nahi hai, Aage Badho"));

    return ResponseEntity.ok(student);
  }


  // get employee by id rest api
  @GetMapping("/students/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    Student student = studentRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Student not exist with id :" + id));
    return ResponseEntity.ok(student);
  }

  //update employee rest api
  public ResponseEntity<Student> updateStudent(@PathVariable Long id,
      @RequestBody Student studentDetails) {
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id : " + id));
    student.setFirstName(studentDetails.getFirstName());
    student.setLastName(studentDetails.getLastName());
    student.setAge(studentDetails.getAge());
    student.setDepartment(studentDetails.getDepartment());

    return ResponseEntity.ok(student);
  }

  //delete employee rest api

  @DeleteMapping("/students/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
    Student student = studentRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException(
            "Tumhara resource nahi mila woh bhi yeh wale id wala : " + id));

    studentRepository.delete(student);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);

  }

  // Various get request as for experiment

  //get all even numbered id students
  @GetMapping("/evenStudents")
  public List<Student> getAllEvenStudents() {
    List<Student> listOfEvenStudents = studentRepository.findAll();

    ArrayList<Student> final_listOfEvenStudents = new ArrayList<Student>();

    for (Student student : listOfEvenStudents) {
      if (student.getId() % 2 == 0) {
        final_listOfEvenStudents.add(student);
      }
    }
//        while(listOfEvenStudents.iterator().hasNext())
//        {
//          int i=1;
//          if(listOfEvenStudents.get(i).getId()%2==0)
//          {
//            final_listOfEvenStudents.add(listOfEvenStudents.get(i));
//          }
//          ++i;
//          if(i==20)
//          {
//            break;
//          }
//        }

    return final_listOfEvenStudents;
  }

}