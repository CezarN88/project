package ro.itschool.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.project.models.entities.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}
