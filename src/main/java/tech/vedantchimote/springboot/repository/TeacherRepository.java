package tech.vedantchimote.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vedantchimote.springboot.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
