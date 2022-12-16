package tech.vedantchimote.springboot.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vedantchimote.springboot.exception.ResourceNotFoundException;
import tech.vedantchimote.springboot.model.Teacher;
import tech.vedantchimote.springboot.repository.TeacherRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1")
public class TeacherController {

  @Autowired
  private TeacherRepository teacherRepository;

  @GetMapping("/teachers")
  public List<Teacher> getTeachers() {
    return (List<Teacher>) teacherRepository.findAll();
  }

  @GetMapping("/teachers/{id}")
  public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
    Teacher teacher = teacherRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Teacher not exist with id : " + id));
    return ResponseEntity.ok(teacher);
  }

  @PutMapping("/teachers/{id}")
  public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id,
      @RequestBody Teacher teacherDetails) {
    Teacher teacher = teacherRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Teacher not exist with id : " + id));

    teacher.setFirstName(teacherDetails.getFirstName());
    teacher.setLastName(teacher.getLastName());
    teacher.setDepartment(teacher.getDepartment());
    teacher.setAge(teacher.getAge());

    return ResponseEntity.ok(teacher);
  }

  public ResponseEntity<Map<String, Boolean>> deleteTeacher(@PathVariable Long id) {
    Teacher teacher = teacherRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException(
            "The respective item associated id " + id + " is not found"));

    teacherRepository.delete(teacher);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);

  }

}
