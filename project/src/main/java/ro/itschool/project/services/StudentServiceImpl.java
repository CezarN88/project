package ro.itschool.project.services;

import org.springframework.stereotype.Service;
import ro.itschool.project.models.dtos.StudentDTO;
import ro.itschool.project.models.entities.Student;
import ro.itschool.project.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) { this.studentRepository = studentRepository;}

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO){
        if (studentDTO.getFirstName().length() < 3) {
            throw new IllegalArgumentException("Invalid first name.");
        }

        Student studentEntity = new Student();
        studentEntity.setFirstName(studentDTO.getFirstName());
        studentEntity.setLastName(studentDTO.getLastName());
        studentEntity.setEmail(studentDTO.getEmail());

        Student savedStudent = studentRepository.save(studentEntity);

        StudentDTO studentResponseDTO = new StudentDTO();
        studentResponseDTO.setId(savedStudent.getId());
        studentResponseDTO.setFirstName(savedStudent.getFirstName());
        studentResponseDTO.setLastName(savedStudent.getLastName());
        studentResponseDTO.setEmail(savedStudent.getEmail());

        return studentResponseDTO;
    }
}